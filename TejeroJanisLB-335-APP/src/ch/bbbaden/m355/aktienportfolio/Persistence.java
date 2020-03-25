/*
 *  Janis Tejero
 *  janis.tejero@stud.bbbaden.ch
 */
package ch.bbbaden.m355.aktienportfolio;

import com.codename1.io.FileSystemStorage;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Janis Tejero
 */
public class Persistence {

    private String portfolio_data;
    private String watchlist_data;
    private String path;
    private final static String PORTFOLIO_FILE = "portfolio.txt";
    private final static String WATCHLIST_FILE = "watchlist.txt";

    private String getPortfolioFilePath() {
        return FileSystemStorage.getInstance().getAppHomePath() + PORTFOLIO_FILE;
    }

    private String getWatchlistFilePath() {
        return FileSystemStorage.getInstance().getAppHomePath() + WATCHLIST_FILE;
    }

    public void savePortfolio() {
        System.out.println("Saving portfolio:" + this.portfolio_data);
        try {
            FileSystemStorage.getInstance().openOutputStream(getPortfolioFilePath()).write(this.portfolio_data.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void saveWatchlist() {
        System.out.println("Saving watchlist:" + this.watchlist_data);
        try {
            FileSystemStorage.getInstance().openOutputStream(getWatchlistFilePath()).write(this.watchlist_data.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void loadPortfolio() {
        String d = "";
        try {
            d = readLines("portfolio", path);
        } catch (IOException ex) {
            // cant log.. codename one..
        }
        System.out.println("Loading portfolio: " + d);
        this.portfolio_data = d;
    }

    public void loadWatchlist() {
        String d = "";
        try {
            d = readLines("watchlist", path);
        } catch (IOException ex) {
            // cant log.. codename one..
        }
        System.out.println("Loading watchlists: " + d);
        this.watchlist_data = d;
    }

    private String readLines(String fileType, String fileName) throws IOException {
        InputStream inputStream;
        if (fileType.equals("portfolio")) {
            inputStream = FileSystemStorage.getInstance().openInputStream(getPortfolioFilePath());
        } else {
            inputStream = FileSystemStorage.getInstance().openInputStream(getWatchlistFilePath());
        }
        int charRead;
        StringBuilder stringRead = new StringBuilder();
        while ((charRead = inputStream.read()) >= 0) {
            final char c = (char) charRead;
            stringRead.append(c);
        }

        return stringRead.toString();
    }

    public String getPortfolio_data() {
        return portfolio_data;
    }

    public void setPortfolio_data(String portfolio_data) {
        this.portfolio_data = portfolio_data;
    }

    public String getWatchlist_data() {
        return watchlist_data;
    }

    public void setWatchlist_data(String watchlist_data) {
        this.watchlist_data = watchlist_data;
    }

}
