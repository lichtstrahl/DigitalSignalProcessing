package root.iv.digitalsignalprocessing.func;

import com.jjoe64.graphview.series.DataPoint;

public interface SignalCommand extends Command, Bound {
    DataPoint[] rec();
}
