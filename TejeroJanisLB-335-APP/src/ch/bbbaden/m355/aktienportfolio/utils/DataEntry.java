/*
 *  Janis Tejero
 *  janis.tejero@stud.bbbaden.ch
 */
package ch.bbbaden.m355.aktienportfolio.utils;

/**
 *
 * @author Janis Tejero
 */
public class DataEntry {

    private String name;
    private String date;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;

    public DataEntry(String name, String date, String open, String high, String low, String close, String volume) {
        this.name = name;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String toString() {
        return name + " " + date + ": " + open + " 2. " + high + " 3. "
                + low + " 4. " + close + " 5. " + volume;
    }
}
