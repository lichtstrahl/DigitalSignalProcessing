package root.iv.digitalsignalprocessing.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import root.iv.digitalsignalprocessing.R;
import root.iv.digitalsignalprocessing.app.App;
import root.iv.digitalsignalprocessing.func.DigitalCommand;
import root.iv.digitalsignalprocessing.func.GaussCommand;
import root.iv.digitalsignalprocessing.func.Worker;
import root.iv.digitalsignalprocessing.ui.DrawerClosed;

public class KotelnikovFragment extends Fragment {
    private static final int INIT_K = 1;
    @BindView(R.id.plot1)
    GraphView plot1;
    @BindView(R.id.plot2)
    GraphView plot2;
    @BindView(R.id.seek1)
    SeekBar seek1;
    @BindView(R.id.viewSeek)
    TextView viewSeek;
    GaussCommand gaussCommand = new GaussCommand(100, 20, 2.0, INIT_K);
    DigitalCommand digitalCommand = new DigitalCommand(100, 20, 10, INIT_K);
    DrawerClosed activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kotelnikov, container, false);
        ButterKnife.bind(this, view);
        getActivity().setTitle(R.string.lab1);
        final int color1 = getContext().getResources().getColor(R.color.colorGraph1);
        final int color2 = getContext().getResources().getColor(R.color.colorGraph2);
        Worker.drawKotelnikov(plot1, gaussCommand, color1, color2);
        Worker.drawKotelnikov(plot2, digitalCommand, color1, color2);
        seek1.setMax(20);
        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                final int min = INIT_K;
                int value = min + progress;
                gaussCommand.setK(value);

                Worker.drawKotelnikov(plot1, gaussCommand, color1, color2);
                digitalCommand.setK(value);
                Worker.drawKotelnikov(plot2, digitalCommand, color1, color2);
                viewSeek.setText(String.valueOf(value));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        activity.closeDrawer();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (DrawerClosed)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    public static KotelnikovFragment getInstance() {
        KotelnikovFragment fragment = new KotelnikovFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
}
