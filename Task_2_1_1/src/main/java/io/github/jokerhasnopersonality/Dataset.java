package io.github.jokerhasnopersonality;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Dataset class for representing database for a chart.
 */
public class Dataset {
    private final XYSeries[] series;

    /**
     * Initiates XYSeries array using given String array of keys.
     * A key is a function name.
     */
    public Dataset(String[] keys) {
        series = new XYSeries[keys.length];
        for (int i = 0; i < keys.length; i++) {
            series[i] = new XYSeries(keys[i]);
        }
    }

    /**
     * Adds a point with the given 2D-coordinates to a function of the given index.
     */
    public void add(int index, double x, double y) {
        series[index].add(x, y);
    }

    /**
     * Creates a dataset of all functions' coordinates.
     */
    public XYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (XYSeries s : series) {
            dataset.addSeries(s);
        }
        return dataset;
    }
}
