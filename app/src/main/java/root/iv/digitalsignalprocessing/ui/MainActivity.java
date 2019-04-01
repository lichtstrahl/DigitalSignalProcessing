package root.iv.digitalsignalprocessing.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import root.iv.digitalsignalprocessing.R;
import root.iv.digitalsignalprocessing.ui.fragments.KotelnikovFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, KotelnikovFragment.getInstance())
                .commit();
    }


}
