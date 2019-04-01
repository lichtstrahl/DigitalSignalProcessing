package root.iv.digitalsignalprocessing.ui.fragments;

import android.os.Bundle;
import android.os.WorkSource;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import root.iv.digitalsignalprocessing.R;
import root.iv.digitalsignalprocessing.func.Worker;

public class KotelnikovFragment extends Fragment {
    @BindView(R.id.plot1)
    GraphView plot1;
    @BindView(R.id.plot2)
    GraphView plot2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kotelnikov, container, false);
        ButterKnife.bind(this, view);
        getActivity().setTitle(R.string.lab1);
        Worker.runG(plot1, 5, 100, 2.0);
        return view;
    }

    public static KotelnikovFragment getInstance() {
        KotelnikovFragment fragment = new KotelnikovFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
}
