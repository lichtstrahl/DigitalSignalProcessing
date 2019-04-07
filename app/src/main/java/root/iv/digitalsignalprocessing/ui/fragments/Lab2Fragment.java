package root.iv.digitalsignalprocessing.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import root.iv.digitalsignalprocessing.R;
import root.iv.digitalsignalprocessing.ui.DrawerClosed;

public class Lab2Fragment extends Fragment {
    private DrawerClosed activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lab2, container, false);
        ButterKnife.bind(this, view);
        activity.closeDrawer();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (DrawerClosed) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    public static Lab2Fragment getInstance() {
        Lab2Fragment fragment = new Lab2Fragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        return fragment;
    }
}
