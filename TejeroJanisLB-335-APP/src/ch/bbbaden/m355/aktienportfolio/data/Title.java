/*
 *  Janis Tejero
 *  janis.tejero@stud.bbbaden.ch
 */
package ch.bbbaden.m355.aktienportfolio.data;


/**
 *
 * @author Janis Tejero
 */
public class Title {

    private String ticker;

    private String company;

    public String toString() {
        return "Stock{"
                + ", ticker=" + ticker
                + ", company='" + company + '\''
                + '}';
    }

}
