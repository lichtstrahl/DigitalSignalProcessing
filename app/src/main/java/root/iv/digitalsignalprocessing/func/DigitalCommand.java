package root.iv.digitalsignalprocessing.func;


import com.jjoe64.graphview.series.DataPoint;

import java.util.LinkedList;
import java.util.List;

import static root.iv.digitalsignalprocessing.func.Worker.kotelnikov;

public class DigitalCommand implements SignalCommand {
    private double d;
    private double[] x;
    private int k;

    public DigitalCommand(int n, double delta, double d, int k) {
        this.d = d;
        double step = delta/n;
        x = new double[n+1];
        for (int i = 0; i <= n; i++)
            x[i] = -delta/2 + i*step;
        this.k = k;
    }

    @Override
    public DataPoint[] run() {
        DataPoint[] result = new DataPoint[x.length];
        for (int i = 0; i < x.length; i++)
            result[i] = new DataPoint(x[i], digital(x[i]));
        return result;
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
    public DataPoint[] rec() {
        int nRec = (x.length-1)/k;
        double delta = getB()-getA();
        double dt = (delta/2) /(nRec-1);
        List<Double> xRec = new LinkedList<>();
        for (double t = -delta/2; t < delta/2; t += dt)
            xRec.add(t);
        double[] yRec = digital(xRec);


        return kotelnikov(x, yRec, dt);
    }

    private double digital(double x) {
        return (x>-d/2 && x< d/2) ? 1.0 : 0.0;
    }

    private double[] digital(List<Double> x) {
        double[] result = new double[x.size()];
        for (int i = 0; i < x.size(); i++)
            result[i] = digital(x.get(i));
        return result;
    }
}
