/*
 *  Janis Tejero
 *  janis.tejero@stud.bbbaden.ch
 */
package ch.bbbaden.m355.aktienportfolio.utils;

import java.util.ArrayList;

/**
 *
 * @author Janis Tejero
 */
// class which represents a watchlist, a list of stocks
public class Watchlist {

    private String name = "";
    private ArrayList<String> tickers;

    public Watchlist(String name, ArrayList<String> tickers) {
        this.name = name;
        this.tickers = tickers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTitle(String ticker) {
        tickers.add(ticker);
    }

    public void removeTitle(String ticker) {
        tickers.remove(ticker);
    }

    public ArrayList<String> getTickers() {
        return tickers;
    }

    public void setTickers(ArrayList<String> tickers) {
        this.tickers = tickers;
    }

}
