package root.iv.digitalsignalprocessing.func;

import android.graphics.Color;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.LinkedList;
import java.util.List;

public class Worker {
    // Точность для сравнений
    private static final double EPS = 1e-5;

    public static double sinc(double x) {
        return Math.abs(x) < EPS ? 1.0 : Math.sin(x)/x;
    }

    public static DataPoint[] kotelnikov(double[] arg, double[] y, double dt) {
        DataPoint[] result = new DataPoint[arg.length];
        for (int j = 0; j < arg.length; j++) {
            double sum = 0.0;
            for (int i = 0; i < y.length; i++) {
                sum += y[i] *sinc(Math.PI/dt * (arg[j] - dt*(-Math.round(y.length/2.0) + i)));
            }
            result[j] = new DataPoint(arg[j], sum);
        }

        return result;
    }

    public static void drawKotelnikov(GraphView plot, SignalCommand cmd) {
        plot.removeAllSeries();
        DataPoint[] points = cmd.run();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
        series.setColor(Color.YELLOW);
        series.setThickness(8);
        plot.addSeries(series);

        DataPoint[] rec = cmd.rec();
        LineGraphSeries<DataPoint> recSerises = new LineGraphSeries<>(rec);
        recSerises.setColor(Color.RED);
        recSerises.setThickness(3);
        plot.addSeries(recSerises);

        Viewport port = plot.getViewport();
        port.setXAxisBoundsManual(true);
        port.setMinX(cmd.getA());
        port.setMaxX(cmd.getB());
    }
}
