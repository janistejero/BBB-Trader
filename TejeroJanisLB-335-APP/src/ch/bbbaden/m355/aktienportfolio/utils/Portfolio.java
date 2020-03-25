/*
 *  Janis Tejero
 *  janis.tejero@stud.bbbaden.ch
 */
package ch.bbbaden.m355.aktienportfolio.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Janis Tejero
 */
public class Portfolio {

    private List<Order> titles = new ArrayList();
    private double startCapital = 100000;
    private double portfolioValue = 0;
    private double freeFunds = startCapital;

    public Portfolio() {
    }

    public void addTitle(String title, double quantity, double price) {
        double cost = ((quantity * price) * 100) / 100;
        if (freeFunds >= cost) {
            titles.add(new Order(title, quantity, price));
        }
    }

    public void removeTitle(String title, double quantity) {

        // iterator to avoid concurrentmodification exception
        Iterator<Order> iter = titles.iterator();

        while (iter.hasNext()) {
            Order o = iter.next();

            if (quantity > 0) {
                if (o.getTitle().equals(title)) {
                    if (o.getQuantity() > quantity) {
                        System.out.println("Quantity smaller, could delete quantity from order");
                        o.setQuantity(o.getQuantity() - quantity);
                        quantity = 0;
                    } else if (o.getQuantity() == quantity) {
                        System.out.println("Quantity equal, could delete entire order");
                        quantity = 0;
                        iter.remove();
                    } else {
                        System.out.println("Quantity greater, have to delete multiple orders");
                        quantity = quantity - o.getQuantity();
                        iter.remove();
                    }
                }
            }
        }
    }

    public double getResult() {
        double result = 0.0;
        return result;
    }

    public void printPortfolio() {
        System.out.println("Portfolio: " + this.getPortfolioValue() + " Free Funds: "
                + this.getFreeFunds() + " Result" + this.getResult());
    }

    public List<Order> getTitles() {
        return this.titles;
    }

    public double getStartCapital() {
        return startCapital;
    }

    public void setStartCapital(double startCapital) {
        this.startCapital = startCapital;
    }

    public double getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(double portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    public double getFreeFunds() {
        return freeFunds;
    }

    public void setFreeFunds(double freeFunds) {
        this.freeFunds = freeFunds;
    }

    public void setTitles(List<Order> titles) {
        this.titles = titles;
    }
    
}
