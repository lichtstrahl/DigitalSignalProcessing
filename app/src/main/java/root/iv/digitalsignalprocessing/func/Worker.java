package root.iv.digitalsignalprocessing.func;

import android.graphics.Color;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Worker {
    // Точность для сравнений
    private static final double EPS = 1e-5;

    private static final double T = 5.0;

    /**
     *
     * @param x
     * @param d - ширина разброса
     * @return
     */
    public static double digital(double x, double d) {
        return (x>-d/2 || x<d/2) ? 1.0 : 0.0;
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

    public static DataPoint[] kotelnikov(double[] arg, double[] S, double dt) {
        DataPoint[] result = new DataPoint[arg.length];
        for (int j = 0; j < arg.length; j++) {
            double sum = 0.0;
            for (int i = 0; i < S.length; i++) {
                sum += S[i] *sinc(Math.PI/dt * (arg[j] - dt*(-Math.round(S.length/2) + i)));
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

    public static void runG(GraphView plot, double delta, int n, double sigma) {
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



    }
}
