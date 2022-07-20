package root.iv.digitalsignalprocessing.func;

import com.jjoe64.graphview.series.DataPoint;

import java.util.LinkedList;
import java.util.List;

import static root.iv.digitalsignalprocessing.func.Worker.kotelnikov;

public class GaussCommand implements SignalCommand {
    private double sigma;
    private double[] x;
    private int k;

    public GaussCommand(int n, double delta, double sigma, int k) {
        this.sigma = sigma;
        double step = delta/n;
        x = new double[n+1];
        for (int i = 0; i <= n; i++)
            x[i] = -delta/2 + i*step;
        this.k = k;
    }

    @Override
    public DataPoint[] rec() {
        int nRec = (x.length-1)/k;
        double delta = getB()-getA();
        double dt = (delta) /(nRec);
        List<Double> xRec = new LinkedList<>();
        for (double t = -delta/2; t < delta/2; t += dt)
            xRec.add(t);
        double[] yRec = gauss(xRec);


        return kotelnikov(x, yRec, dt);
    }

    @Override
    public double getA() {
        return x[0];
    }

    @Override
    public double getB() {
        return x[x.length-1];
    }

    public void setK(int k) {
        this.k = k;
    }

    @Override
    public DataPoint[] run() {
        DataPoint[] result = new DataPoint[x.length];
        for (int i = 0; i < x.length; i++)
            result[i] = new DataPoint(x[i], gauss(x[i]));
        return result;
    }

    private double gauss(double x) {
        final double a = 1.0;
        final double b = 0;
        final double c = sigma/Math.sqrt(2);
        final double p = -(x-b)*(x-b)/(2*c*c);
        return a * Math.exp(p);
    }

    private double[] gauss(List<Double> x) {
        double[] result = new double[x.size()];
        for (int i = 0; i < x.size(); i++)
            result[i] = gauss(x.get(i));
        return result;
    }
}
