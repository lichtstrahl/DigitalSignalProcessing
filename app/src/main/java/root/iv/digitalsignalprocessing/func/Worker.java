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

    /**
     *
     * @param x -
     * @param d - ширина разброса
     * @return -
     */
    public static double digital(double x, double d) {
        return (x>-d/2 && x<d/2) ? 1.0 : 0.0;
    }

    public static double[] digital(List<Double> x, double d) {
        double[] result = new double[x.size()];
        for (int i = 0; i < x.size(); i++)
            result[i] = digital(x.get(i), d);

        return result;
    }

    public static double[] digital(double[] x, double d) {
        double[] result = new double[x.length];
        for (int i = 0; i < x.length; i++)
            result[i] = digital(x[i], d);
        return result;
    }

    public static double gauss(double x, double sigma) {
        final double a = 1.0;
        final double b = 0;
        final double c = sigma/Math.sqrt(2);
        final double p = -(x-b)*(x-b)/(2*c*c);
        return a * Math.exp(p);
    }

    public static double[] gauss(double[] x, double sigma) {
        double[] result = new double[x.length];
        for (int i = 0; i < x.length; i++)
            result[i] = gauss(x[i], sigma);
        return result;
    }

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

    public static DataPoint[] dataPointFromXY(double[] x, double[] y) {
        DataPoint[] result = new DataPoint[x.length];
        for (int i = 0; i < x.length; i++) {
            result[i] = new DataPoint(x[i], y[i]);
        }
        return result;
    }

    public static void runGauss(GraphView plot, double delta, int n, double sigma) {
        plot.removeAllSeries();
        final double step = delta/n;
        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = -delta/2 + i*step;
        }
        double[] y = gauss(x, sigma);

        DataPoint[] points = dataPointFromXY(x,y);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
        series.setColor(Color.YELLOW);
        series.setThickness(8);
        plot.addSeries(series);


        DataPoint[] rec = kotelnikov(x, y, step);
        LineGraphSeries<DataPoint> recSerises = new LineGraphSeries<>(rec);
        recSerises.setColor(Color.RED);
        recSerises.setThickness(3);
        plot.addSeries(recSerises);

        Viewport port = plot.getViewport();
        port.setXAxisBoundsManual(true);
        port.setMinX(-delta/2);
        port.setMaxX(delta/2);
    }

    public static void runDigital(GraphView plot, double delta, int n, double d) {
        plot.removeAllSeries();

        final double step = delta/n;
        double[] x = new double[n+1];
        for (int i = 0; i <= n; i++) {
            x[i] = -delta/2 + i*step;
        }
        double[] y = digital(x, d);

        DataPoint[] points = dataPointFromXY(x,y);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
        series.setColor(Color.YELLOW);
        series.setThickness(8);
        plot.addSeries(series);

        int nRec = n/10;
        double dt = (delta/2) /(nRec-1);
        List<Double> xRec = new LinkedList<>();
        for (double t = -delta/2; t < delta/2; t += dt)
            xRec.add(t);
        double[] yRec = digital(xRec, d);


        DataPoint[] rec = kotelnikov(x, yRec, dt);
        LineGraphSeries<DataPoint> recSerises = new LineGraphSeries<>(rec);
        recSerises.setColor(Color.RED);
        recSerises.setThickness(3);
        plot.addSeries(recSerises);

        Viewport port = plot.getViewport();
        port.setXAxisBoundsManual(true);
        port.setMinX(-delta/2);
        port.setMaxX(delta/2);
    }

    public static void runDigital(GraphView plot, SignalCommand cmd) {
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
