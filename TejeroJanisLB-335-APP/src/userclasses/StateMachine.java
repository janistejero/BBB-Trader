/**
 * Your application code goes here<br>
 * This file was generated by <a href="https://www.codenameone.com/">Codename
 * One</a> for the purpose of building native mobile applications using Java.
 */
package userclasses;

import ch.bbbaden.m355.aktienportfolio.Chart.PricesLineChart;
import ch.bbbaden.m355.aktienportfolio.utils.DataEntry;
import ch.bbbaden.m355.aktienportfolio.FinanceAPI;
import ch.bbbaden.m355.aktienportfolio.Persistence;
import ch.bbbaden.m355.aktienportfolio.utils.IndexedLinkedHashMap;
import ch.bbbaden.m355.aktienportfolio.utils.NumericConstraint;
import ch.bbbaden.m355.aktienportfolio.utils.Order;
import ch.bbbaden.m355.aktienportfolio.utils.Portfolio;
import ch.bbbaden.m355.aktienportfolio.utils.Watchlist;
import com.codename1.charts.ChartComponent;
import com.codename1.charts.util.ColorUtil;
import com.codename1.components.Accordion;
import com.codename1.components.ScaleImageButton;
import generated.StateMachineBase;
import com.codename1.ui.*;
import com.codename1.ui.events.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Janis Tejero
 */
public class StateMachine extends StateMachineBase {

    private FinanceAPI api;
    private String selectedTicker = "";
    private String selectedQuantity = "";
    private HashMap<String, Double> prices;
    private Portfolio portfolio;
    private ArrayList<Watchlist> watchlists;

    // list with tickers and company names
    IndexedLinkedHashMap<String, String> titles;
    private boolean loadedPortfolio;
    private boolean loadedWatchlist;
    private Persistence persistence;

    public StateMachine(String resFile) {
        super(resFile);
        // do not modify, write code in initVars and initialize class members there,125
        // the constructor might be invoked too late due to race conditions that might occur
    }

    /**
     * this method should be used to initialize variables instead of the
     * constructor/class scope to avoid race conditions
     */
    protected void initVars(Resources res) {
        this.api = new FinanceAPI();
        this.prices = new HashMap();
        this.watchlists = new ArrayList<>();
        this.loadedPortfolio = false;
        this.loadedWatchlist = false;
        this.titles = new IndexedLinkedHashMap<String, String>();
        this.persistence = new Persistence();
    }

    public void showWatchlistForm() {
        showForm("Watchlist", null);
    }

    public void showPortfolioForm() {
        showForm("Portfolio", null);
    }

    public void showFindForm() {
        showForm("Find", null);
    }

    public void showAccountForm() {
        showForm("Account", null);
    }

    public void showChartForm() {
        showForm("Graph", null);
    }

    private double getPriceFromAPI(String ticker) {
        return api.getClosePrice(ticker);
    }

    private void openChartForTicker(String ticker) {
        this.selectedTicker = ticker;
        showChartForm();
    }

    private void openTradeDialogForTicker(String ticker) {
        this.selectedTicker = ticker;

        Dialog dialog = new Dialog();
        dialog.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        TextField quantityTxt = new TextField();
        Container buttonContainer = new Container(new GridLayout(1, 2));

        dialog.add(new Label(ticker));
        dialog.add(quantityTxt);

        Label costLbl = new Label("  ");

        // buttons for trading
        Button buyBtn = new Button("Buy");
        buyBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
            if (isValidInput(quantityTxt.getText())) {
                buy(this.selectedTicker, Double.valueOf(this.selectedQuantity), prices.get(ticker));
            }

        });

        Button sellBtn = new Button("Sell");
        sellBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
            if (isValidInput(quantityTxt.getText())) {
                if (hasEnoughToSell(ticker, Double.valueOf(quantityTxt.getText()))) {
                    sell(this.selectedTicker, Double.valueOf(this.selectedQuantity));
                } else {
                    costLbl.setText("Insufficient stocks owned.");
                }
            }
        });
        buttonContainer.add(buyBtn);
        buttonContainer.add(sellBtn);

        quantityTxt.addDataChangedListener((i, ii) -> {
            if (isValidInput(quantityTxt.getText())) {
                quantityTxt.putClientProperty("LastValid", quantityTxt.getText());
                updateCostLabel(quantityTxt, prices.get(ticker));
                this.selectedQuantity = quantityTxt.getText();
            } else {
                quantityTxt.stopEditing();
                quantityTxt.setText((String) quantityTxt.getClientProperty("LastValid"));
                quantityTxt.startEditingAsync();
            }
        });

        dialog.add(costLbl);
        dialog.add(buttonContainer);
        dialog.setDisposeWhenPointerOutOfBounds(true);
        dialog.setSize(new Dimension(Display.getInstance().getDisplayWidth() - 10, 200));
        dialog.show();
    }

    private boolean isValidInput(String text) {
        NumericConstraint quantityConstraint = new NumericConstraint(true, 1, 10000, "Please enter a valid amount");
        return quantityConstraint.isValid(text);
    }

    private boolean hasEnoughToSell(String ticker, double quantity) {
        for (Order o : getUniqueOrders()) {
            if (o.getTitle().equals(ticker)) {
                if (quantity <= o.getQuantity()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updateCostLabel(TextField quantityTxtField, double price) {
        double cost = Double.valueOf(quantityTxtField.getText()) * price;
        cost = (cost * 100) / 100;
        Container container = quantityTxtField.getParent();
        Label output = (Label) container.getComponentAt(2);
        output.setText(String.valueOf(cost));
    }

    @Override
    protected void beforeWatchlist(Form f) {
        // list with tickers & companies
        initializeTitles();

        // load the saved data
        loadPortfolio();
        loadWatchlist();

        // sample data;
        //initializeWatchlists();
        // tab for each watchlist
        Tabs t = new Tabs();
        t.getStyle().setOverline(true);
        Accordion singleWatchList;

        Style s = UIManager.getInstance().getComponentStyle("Button");
        Image icon = FontImage.createMaterial(FontImage.MATERIAL_TRENDING_UP, s);

        ScaleImageButton chartBtn;

        // loads each watchlist
        for (Watchlist w : watchlists) {
            // creates watchlist as accordion with its components
            singleWatchList = new Accordion();
            for (String tickerStr : w.getTickers()) {

                // check if prices have been loaded yet, reduces loading time
                if (!prices.containsKey(tickerStr)) {
                    if (!tickerStr.equals("defaultSample")) {
                        prices.put(tickerStr, getPriceFromAPI(tickerStr));
                    }

                }

                chartBtn = new ScaleImageButton(icon);
                chartBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
                    Label pressed = (Label) evt.getComponent().getParent().getComponentAt(1);
                    openChartForTicker(pressed.getText());
                });

                Button tradeBtn = new Button("Trade");
                tradeBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
                    Label pressed = (Label) evt.getComponent().getParent().getComponentAt(1);
                    openTradeDialogForTicker(pressed.getText());
                });

                Button removeBtn = new Button("Remove");
                removeBtn.getStyle().setFgColor(ColorUtil.rgb(194, 139, 130));
                removeBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
                    w.removeTitle(tickerStr);
                    saveWatchlist();
                    showWatchlistForm();
                });

                if (tickerStr.equalsIgnoreCase("defaultSample")) {
                    singleWatchList.addContent("Empty", new Label("Navigate to 'Find' to add some titles!"));
                } else {
                    // accordion element 
                    Container c = new Container(new GridLayout(3, 3));
                    c.add(new Label("Ticker:  "));
                    c.add(new Label(tickerStr));
                    c.add(new Label(""));
                    c.add(new Label("Price:   "));
                    c.add(new Label(String.valueOf(prices.get(tickerStr))));
                    c.add(new Label(""));
                    c.add(chartBtn);
                    c.add(tradeBtn);
                    c.add(removeBtn);
                    singleWatchList.addContent(titles.get(tickerStr), c);
                }

            }

            // element for deleting watchlist
            Button removeWatchListBtn = new Button("Delete Watchlist");
            removeWatchListBtn.getStyle().setFgColor(ColorUtil.rgb(194, 139, 130));
            removeWatchListBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
                removeWatchlist(w);
                showWatchlistForm();
            });
            singleWatchList.addContent("About this watchlist", BoxLayout.encloseY(
                    removeWatchListBtn));
            t.addTab(w.getName(), singleWatchList);
        }

        // tab for creating a new watchlist
        Container addContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        addContainer.setHeight(300);
        TextField newWatchlistTxtField = new TextField();
        newWatchlistTxtField.setHint("Enter a name for watchlist");

        Label outputLbl = new Label(" ");

        Button addBtn = new Button("Create new watchlist");
        addBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
            if (newWatchlistTxtField.getText().isEmpty() || newWatchlistTxtField.getText().length() < 2) {
                outputLbl.setText("Name should be atleast 2 characters long.");
            } else if (newWatchlistTxtField.getText().length() > 10) {
                outputLbl.setText("Name should be no more than 10 characters long.");
            } else {
                createNewWatchList(newWatchlistTxtField.getText());
            }
        });

        addContainer.add(newWatchlistTxtField);
        addContainer.add(addBtn);
        addContainer.add(outputLbl);
        addContainer.setSize(new Dimension(Display.getInstance().getDisplayWidth() - 10, 200));

        t.addTab("+", addContainer);
        f.add(BorderLayout.CENTER, t);
        f.add(BorderLayout.SOUTH, createBottomMenu());

        // automatically update (save) all watchlist objects
        //saveWatchlist();
    }

    @Override
    protected void beforePortfolio(Form f) {
        Accordion accr = new Accordion();

        // summarised orders (1 ticker x orders)
        ArrayList<Order> uniqueOrders = getUniqueOrders();

        for (int i = 0; i < uniqueOrders.size(); i++) {
            Order o = uniqueOrders.get(i);
            Button tradeBtn = new Button("Trade");
            tradeBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
                openTradeDialogForTicker(o.getTitle());
            });

            accr.addContent(o.getTitle(), BoxLayout.encloseY(
                    new Label("Total : " + String.valueOf(o.getPrice())),
                    new Label("Amount: " + String.valueOf(o.getQuantity())),
                    tradeBtn));
        }

        f.add(BorderLayout.CENTER, accr);
        f.add(BorderLayout.SOUTH, createBottomMenu());

        // automatically update (save) the portfolio
        savePortfolio();
    }

    @Override
    protected void beforeFind(Form f) {

        Container searchContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        TextField searchBar = new TextField();
        searchBar.setHint("Search for a title");

        Button addToWatchListBtn = new Button("Add to Watchlist");
        addToWatchListBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
            openWatchListAddDialog(searchBar.getText());
        });

        searchContainer.add(searchBar);
        searchContainer.add(addToWatchListBtn);

        Accordion searchResults = new Accordion();

        f.add(BorderLayout.NORTH, searchContainer);
        f.add(BorderLayout.CENTER, searchResults);
        f.add(BorderLayout.SOUTH, createBottomMenu());
    }

    @Override
    protected void beforeGraph(Form f) {
        PricesLineChart plc = new PricesLineChart();

        java.util.List<DataEntry> entries = api.getData(this.selectedTicker);
        plc.setDataEntries(entries);
        ChartComponent chart = plc.execute();
        f.add(BorderLayout.SOUTH, createBottomMenu());
        f.add(BorderLayout.CENTER, chart);
    }

    @Override
    protected void beforeAccount(Form f) {

        // profil icon
        Style s = UIManager.getInstance().getComponentStyle("Label");
        Image icon = FontImage.createMaterial(FontImage.MATERIAL_ACCOUNT_CIRCLE, s);
        icon = icon.scaledHeight(200);
        icon = icon.scaledWidth(200);

        Container accountContainer = (Container) f.getComponentAt(0);
        Container cardContainer = (Container) accountContainer.getComponentAt(0);
        cardContainer.getStyle().setBorder(Border.createBevelRaised());

        // profile image
        Container profileContainer = (Container) cardContainer.getComponentAt(0);
        Label profileLbl = (Label) profileContainer.getComponentAt(0);
        profileLbl.setIcon(icon);
        profileLbl.setAlignment(0);
        profileLbl.setText("");

        // data labels
        Container dataContainer = (Container) cardContainer.getComponentAt(1);
        Label accountValueLbl = (Label) dataContainer.getComponentAt(1);
        Label portfolioValueLbl = (Label) dataContainer.getComponentAt(3);
        Label freeFundsLbl = (Label) dataContainer.getComponentAt(5);
        Label resultLbl = (Label) dataContainer.getComponentAt(7);

        // calculate 
        double accountVal = portfolio.getPortfolioValue() + portfolio.getFreeFunds();
        accountVal = (accountVal * 100) / 100;
        accountValueLbl.setText(String.valueOf(accountVal));
        portfolioValueLbl.setText(String.valueOf(portfolio.getPortfolioValue()));
        freeFundsLbl.setText(String.valueOf(portfolio.getFreeFunds()));
        resultLbl.setText(calculateResult());

        f.add(BorderLayout.SOUTH, createBottomMenu());
    }

    private Container createBottomMenu() {
        // bottom menu
        Container bottomMenu = new Container();
        bottomMenu.setLayout(new GridLayout(1, 4));

        Button watchlistBtn = new Button("Watchlist");
        watchlistBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
            showWatchlistForm();
        });

        Button portfolioBtn = new Button("Portfolio");
        portfolioBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
            showPortfolioForm();
        });

        Button findBtn = new Button("Find");
        findBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
            showFindForm();
        });

        Button accountBtn = new Button("Account");
        accountBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
            showAccountForm();
        });

        bottomMenu.add(watchlistBtn);
        bottomMenu.add(portfolioBtn);
        bottomMenu.add(findBtn);
        bottomMenu.add(accountBtn);

        return bottomMenu;
    }

    private void buy(String ticker, double quantity, double price) {
        double cost = ((quantity * price) * 100) / 100;
        portfolio.addTitle(ticker, quantity, price);
        portfolio.setFreeFunds((portfolio.getFreeFunds() - cost) * 100 / 100);

        updatePortfolioValue();
        portfolio.printPortfolio();
        showPortfolioForm();
    }

    private void sell(String ticker, double quantity) {
        portfolio.removeTitle(ticker, quantity);
        portfolio.setFreeFunds((portfolio.getFreeFunds() + (prices.get(ticker) * quantity)) * 100 / 100);

        updatePortfolioValue();
        portfolio.printPortfolio();
        showPortfolioForm();
    }

    private void updatePortfolioValue() {
        double sum = 0.0;

        Order o;
        for (int i = 0; i < portfolio.getTitles().size(); i++) {
            o = portfolio.getTitles().get(i);
            sum += (prices.get(o.getTitle()) * o.getQuantity());
        }
        sum = (sum * 100) / 100;
        portfolio.setPortfolioValue(sum);
        portfolio.printPortfolio();
    }

    private String calculateResult() {
        double paid = 0.0;
        double current = 0.0;
        for (Order o : portfolio.getTitles()) {
            paid += o.getPrice() * o.getQuantity();
            current += prices.get(o.getTitle()) * o.getQuantity();
        }

        double difference = current - paid;
        difference = (difference * 100) / 100;
        if (difference >= 0) {
            return "+ " + difference;
        } else {
            return String.valueOf(difference);
        }
    }

    private ArrayList<Order> getUniqueOrders() {
        ArrayList<Order> orders = new ArrayList();
        ArrayList<String> uniqueTickers = new ArrayList<>();
        for (int i = 0; i < portfolio.getTitles().size(); i++) {
            if (!uniqueTickers.contains(portfolio.getTitles().get(i).getTitle())) {
                uniqueTickers.add(portfolio.getTitles().get(i).getTitle());
            }
        }

        for (String ticker : uniqueTickers) {
            double sum = 0.0;
            double quan = 0.0;
            Order o;
            for (int i = 0; i < portfolio.getTitles().size(); i++) {
                o = portfolio.getTitles().get(i);
                if (o.getTitle().equals(ticker)) {
                    sum += (o.getPrice() * o.getQuantity());
                    quan += o.getQuantity();
                }
            }
            orders.add(new Order(ticker, quan, sum));
        }

        return orders;
    }

    private void addToWatchList(String watchlist, String ticker) {
        for (Watchlist w : watchlists) {
            if (w.getName().equals(watchlist)) {
                System.out.println("Adding: " + ticker + " to " + watchlist);
                w.getTickers().add(ticker);
            }
        }
        saveWatchlist();
    }

    private void createNewWatchList(String name) {
        ArrayList<String> defaultSample = new ArrayList<>();
        defaultSample.add("defaultSample");
        watchlists.add(new Watchlist(name, defaultSample));
        saveWatchlist();
        showWatchlistForm();
    }

    private void removeWatchlist(Watchlist watchlist) {
        System.out.println("Removing: " + watchlist + " from watchlists");
        watchlists.remove(watchlist);
        saveWatchlist();
    }

    private void openWatchListAddDialog(String ticker) {
        Dialog dialog = new Dialog();
        dialog.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        Label label = new Label("Add " + ticker + " to the following watchlist:");

        ComboBox<String> watchlistCmb = new ComboBox<>();
        for (Watchlist w : this.watchlists) {
            watchlistCmb.addItem(w.getName());
        }

        Button addBtn = new Button("Add");
        addBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
            addToWatchList((String) watchlistCmb.getSelectedItem(), ticker);
            showWatchlistForm();
        });

        container.add(watchlistCmb);
        container.add(addBtn);

        dialog.add(label);
        dialog.add(container);
        dialog.setDisposeWhenPointerOutOfBounds(true);
        dialog.setSize(new Dimension(Display.getInstance().getDisplayWidth() - 10, 200));
        dialog.show();
    }

    // only used for testing to have some samples
    private void initializeWatchlists() {
        if (!loadedWatchlist) {
            ArrayList<String> examples1 = new ArrayList<>();
            examples1.add("KO");
            examples1.add("TSLA");
            ArrayList<String> examples2 = new ArrayList<>();
            examples2.add("AMZN");
            examples2.add("T");
            watchlists.add(new Watchlist("Long", examples1));
            watchlists.add(new Watchlist("Short", examples2));
            this.loadedWatchlist = true;
        }
    }

    private void initializeTitles() {
        titles.add("MMM", "3M");
        titles.add("AXP", "American Express");
        titles.add("AAPL", "Apple");
        titles.add("BA", "Boeing");
        titles.add("CAT", "Catterpillar");
        titles.add("CVX", "Chevron");
        titles.add("CSCO", "Cisco");
        titles.add("KO", "Coca-Cola");
        titles.add("DIS", "Disney");
        titles.add("DOW", "Dow Chemical");
        titles.add("XOM", "Exxon Mobile");
        titles.add("GS", "Goldman Sachs");
        titles.add("HD", "Home Depot");
        titles.add("IBM", "IBM");
        titles.add("INTC", "Intel");
        titles.add("JNJ", "Johnson & Johnson");
        titles.add("JPM", "JPMorgan Chase");
        titles.add("MCD", "McDonald's");
        titles.add("MRK", "Merck");
        titles.add("MSFT", "Microsoft");
        titles.add("NKE", "Nike");
        titles.add("PFE", "Pfizer");
        titles.add("PG", "Procter & Gamble");
        titles.add("TRV", "Travelers Companies Inc");
        titles.add("UTX", "United Technologies");
        titles.add("UNH", "UnitedHealth");
        titles.add("VZ", "Verizon");
        titles.add("V", "Visa");
        titles.add("WMT", "Wal-Mart");
        titles.add("WBA", "Walgreen");
        titles.add("TSLA", "Tesla");
        titles.add("SPCE", "Virgin Galactic");
        titles.add("SNE", "Sony");
        titles.add("AMZN", "Amazon");
        titles.add("BRK-B", "Berkshire Hathaways Class B");
        titles.add("T", "AT&T");
    }

    private void savePortfolio() {
        persistence.setPortfolio_data(new Gson().toJson(portfolio));
        persistence.savePortfolio();
    }

    private void saveWatchlist() {
        persistence.setWatchlist_data(new Gson().toJson(watchlists));
        persistence.saveWatchlist();
    }

    private void loadPortfolio() {
        persistence.loadPortfolio();
        String portfolioStr = persistence.getPortfolio_data();
        JsonParser parser = new JsonParser();
        JsonObject portfolioJsonObj = parser.parse(portfolioStr).getAsJsonObject();

        ArrayList<Order> orders = new ArrayList<>();

        JsonArray ordersJsonArray = portfolioJsonObj.get("titles").getAsJsonArray();
        String startCapital = portfolioJsonObj.get("startCapital").getAsString();
        String portfolioValue = portfolioJsonObj.get("portfolioValue").getAsString();
        String freeFunds = portfolioJsonObj.get("freeFunds").getAsString();

        for (JsonElement orderElement : ordersJsonArray) {
            String title = orderElement.getAsJsonObject().get("title").getAsString();
            String quantity = orderElement.getAsJsonObject().get("quantity").getAsString();
            String price = orderElement.getAsJsonObject().get("price").getAsString();
            orders.add(new Order(title, Double.valueOf(quantity), Double.valueOf(price)));
        }

        Portfolio port = new Portfolio();
        port.setStartCapital(Double.valueOf(startCapital));
        port.setFreeFunds(Double.valueOf(freeFunds));
        port.setPortfolioValue(Double.valueOf(portfolioValue));
        port.setTitles(orders);
        port.printPortfolio();
        this.portfolio = port;
    }

    private void loadWatchlist() {
        persistence.loadWatchlist();

        String watchlistStr = persistence.getWatchlist_data();

        JsonParser parser = new JsonParser();
        JsonArray watchListJsonArr = parser.parse(watchlistStr).getAsJsonArray();

        ArrayList<Watchlist> toAdd = new ArrayList<>();
        for (JsonElement watchlistElement : watchListJsonArr) {
            JsonObject watchlistJsonObj = watchlistElement.getAsJsonObject();

            ArrayList<String> tickers = new ArrayList<>();
            String watchlistName = watchlistJsonObj.get("name").getAsString();
            JsonArray tickersJsonArray = watchlistJsonObj.get("tickers").getAsJsonArray();
            for (JsonElement tickerElement : tickersJsonArray) {
                tickers.add(tickerElement.getAsString());
            }

            toAdd.add(new Watchlist(watchlistName, tickers));
        }

        this.watchlists.clear();
        this.watchlists = toAdd;
    }
}
