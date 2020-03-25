/*
 *  Janis Tejero
 *  janis.tejero@stud.bbbaden.ch
 */
package ch.bbbaden.m355.aktienportfolio.Chart;

import ch.bbbaden.m355.aktienportfolio.utils.DataEntry;
import com.codename1.charts.ChartComponent;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PointStyle;
import com.codename1.charts.views.TimeChart;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Janis Tejero
 */
public class PricesLineChart extends AbstractChart {

    private static final long HOUR = 3600 * 1000;
    private static final long DAY = HOUR * 24;

    private List<DataEntry> data;

    public ChartComponent execute() {

        String[] titles = new String[]{data.get(1).getName()};

        // x values in dates from api
        List<Date[]> x = new ArrayList<Date[]>();
        long now = Math.round(new Date().getTime() / DAY) * DAY;
        Date[] dates = new Date[data.size()];
        for (int j = 0; j < data.size(); j++) {
            Date d = new Date(now - (data.size() - j) * DAY);
            dates[j] = d;
        }
        x.add(dates);

        // y values in prices from api
        List<double[]> values = new ArrayList<double[]>();
        double[] prices = new double[data.size()];
        for (int i = 0; i < data.size(); i++) {
            prices[i] = Double.valueOf(data.get(i).getClose());
        }
        values.add(prices);

        int[] colors = new int[]{ColorUtil.BLACK};
        PointStyle[] styles = new PointStyle[]{PointStyle.CIRCLE};
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
        }

        setChartSettings(renderer, " ", " ", "Price (USD)",
                x.get(0)[0].getTime(), x.get(0)[x.get(0).length - 1].getTime(), getMin(values.get(0)), getMax(values.get(0)), ColorUtil.BLACK, ColorUtil.LTGRAY);

        int numXLabels = Display.getInstance().getDisplayWidth() / (30 + 20);

        renderer.setXLabels(numXLabels);
        renderer.setYLabels(10);
        renderer.setXLabelsAngle(90);
        renderer.setXLabelsPadding(100);
        renderer.setGridColor(ColorUtil.GRAY);
        renderer.setBackgroundColor(ColorUtil.BLACK);
        renderer.setShowGrid(true);
        renderer.setXLabelsAlign(Component.CENTER);
        renderer.setYLabelsAlign(Component.RIGHT);

        TimeChart chart = new TimeChart(buildDateDataset(titles, x, values),
                renderer);
        return new ChartComponent(chart);

    }

    private double getMax(double[] inputArray) {
        double maxValue = inputArray[0];
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] > maxValue) {
                maxValue = inputArray[i];
            }
        }
        return maxValue;
    }

    private double getMin(double[] inputArray) {
        double minValue = inputArray[0];
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] < minValue) {
                minValue = inputArray[i];
            }
        }
        return minValue;
    }

    public void setDataEntries(List<DataEntry> entries) {
        this.data = entries;
    }

}
