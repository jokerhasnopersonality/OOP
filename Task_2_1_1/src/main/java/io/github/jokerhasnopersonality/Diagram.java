package io.github.jokerhasnopersonality;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.xy.XYDataset;

/**
 * Diagram class. Creates a frame to draw line charts for functions.
 */
public class Diagram extends ApplicationFrame {
    private static final long serialVersionUID = 1L;
    private final XYDataset dataset;
    private final XYSplineRenderer renderer;
    private final JFreeChart chart;

    /**
     * Diagram constructor creates a chart with a given dataset and render parameters.
     */
    public Diagram(XYDataset dataset, XYSplineRenderer renderer) {
        super("Line Chart");
        this.dataset = dataset;
        this.renderer = renderer;

        chart      = createChart();
        ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    public JFreeChart getChart() {
        return chart;
    }

    /**
     * Creates a line chart using JFreeChart library.
     */
    private JFreeChart createChart() {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "NonPrimeNumberCheck function",
                "Array size",
                "Milliseconds",
                null,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        chart.setBackgroundPaint(ChartColor.white);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(ChartColor.lightGray);

        plot.setDomainGridlinePaint(ChartColor.gray);
        plot.setRangeGridlinePaint(ChartColor.gray);

        plot.setAxisOffset(new RectangleInsets(1.0, 1.0, 1.0, 1.0));

        ValueAxis axis = plot.getDomainAxis();
        axis.setAxisLineVisible(false);

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAxisLineVisible(false);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        plot.setDataset(dataset);
        plot.setRenderer(renderer);

        return chart;
    }
}
