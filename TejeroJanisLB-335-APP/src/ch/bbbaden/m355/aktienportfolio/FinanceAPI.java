/*
 *  Janis Tejero
 *  janis.tejero@stud.bbbaden.ch
 */
package ch.bbbaden.m355.aktienportfolio;

import ch.bbbaden.m355.aktienportfolio.utils.DataEntry;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Janis Tejero
 */
public class FinanceAPI {

    private static final String QUOTES_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=";
    private static final String API_KEY = "&apikey=SKHUEYB9KY0D0T2N";
    private static final String[] API_KEYS = {
        "&apikey=SKHUEYB9KY0D0T2N", "&apikey=7JJ4KU81J5CDN1CU", "&apikey=6CXB51KD9FI74XVK",
        "&apikey=L5VR0TP42I8XCZI3", "&apikey=O4N4O4Y1SI31ZFVU", "&apikey=ZDFG61M1GHN5CZ5P",
        "&apikey=D0XYANN8HED81PHB", "&apikey=HSQSDYVPR9W51BEL", "&apikey=XTBWF9R65BYYAVT3"};

    private final static int HTTP_NO_NETWORK = 0;
    private final static int HTTP_OK = 200;

    private Date today = new Date();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public double getClosePrice(String ticker) {
        today = new Date();
        String price = "0";
        int apiKeyCounter = 0;
        int requestCounter = 0;
        boolean valid = false;
        do {
            String result = readFromService(QUOTES_URL, ticker, API_KEYS[apiKeyCounter]);
            final JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(result).getAsJsonObject();

            if (jsonObject.has("Error Message")) {
                System.out.println("Invalid API Call, Ticker has not been found");
            } else if (jsonObject.has("Note")) { // api key cycling does not work, alpha vantage recognizes it
                System.out.print("Used up 5 API calls of the key, unfortunately api key cycling is not possible, "
                        + "please wait atleast 1 minute or use less tickers at a time");
                if (requestCounter == 5) {
                    if (apiKeyCounter == (API_KEYS.length - 1)) {
                        apiKeyCounter = 0;
                    } else {
                        apiKeyCounter++;
                    }
                    requestCounter = 0;

                } else {
                    requestCounter++;
                }
            } else {
                valid = true;
                JsonObject todaysData = null;

                // look for the latest data possible
                do {
                    todaysData = jsonObject.getAsJsonObject("Time Series (Daily)").getAsJsonObject(simpleDateFormat.format(today));
                    System.out.println("trying to fetch current price for: " + ticker + " from the " + simpleDateFormat.format(today));
                    today = new Date(today.getTime() - (24 * 60 * 60 * 1000));
                } while (todaysData == null);

                price = todaysData.get("4. close").getAsString();

                if (price.equals("") || price.isEmpty()) {
                    return 0.0;
                }
            }
        } while (!valid);

        return Double.valueOf(price);
    }

    public List<DataEntry> getData(String ticker) {
        today = new Date();

        String result = readFromService(QUOTES_URL, ticker, API_KEY);
        List<DataEntry> entries = new ArrayList<>();

        if (result == null) {
            throw new NullPointerException("No response from API");
        }
        final JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(result).getAsJsonObject();
        if (jsonObject.has("Error Message")) {
            System.out.println("Invalid API Call, Ticker has not been found");
        } else if (jsonObject.has("Note")) {
            System.out.print("Used up 5 API calls of the key, unfortunately api key cycling is not possible, "
                    + "please wait atleast 1 minute or use less tickers at a time");
            // above implemented method of api key cycling does not work as AlphaVantage does not count
            // api calls with the key itself..
        } else {

            JsonObject timeseriesObject = jsonObject.getAsJsonObject("Time Series (Daily)");
            JsonObject todaysData = null;

            int counter = 0;
            DataEntry entry = null;

            // loops data until there is no more data
            do {
                System.out.println("trying to fetch data for: " + ticker + " from the " + simpleDateFormat.format(today));
                todaysData = timeseriesObject.getAsJsonObject(simpleDateFormat.format(today));
                if (todaysData == null) {
                    counter++;
                } else {
                    counter = 0;
                    String open = todaysData.get("1. open").getAsString();
                    String high = todaysData.get("2. high").getAsString();
                    String low = todaysData.get("3. low").getAsString();
                    String close = todaysData.get("4. close").getAsString();
                    String volume = todaysData.get("5. volume").getAsString();
                    entry = new DataEntry(ticker, today.toString(), open, high, low, close, volume);
                    entries.add(entry);
                }
                // get yesterday
                today = new Date(today.getTime() - (24 * 60 * 60 * 1000));
            } while (counter != 4);
        }
        return entries;
    }

    private String readFromService(final String baseurl, String arg1, String arg2) {
        byte[] data = readFromNetwork(baseurl + arg1 + arg2);
        return new String(data);
    }

    private byte[] readFromNetwork(final String url) {
        final ConnectionRequest req = new ConnectionRequest();
        req.setPost(false);
        req.setUrl(url);

        req.setFailSilently(true);
        NetworkManager.getInstance().addToQueueAndWait(req);
        if (req.getResponseCode() != HTTP_OK) {
            if (req.getResponseCode() == HTTP_NO_NETWORK) {
                throw new RuntimeException("No connection");
            }
            throw new RuntimeException("Error: " + req.getResponseCode() + ".");
        }
        final byte[] data = req.getResponseData();
        if (data == null) {
            throw new RuntimeException("Network problem: no response from server.");
        }
        return data;
    }
}
