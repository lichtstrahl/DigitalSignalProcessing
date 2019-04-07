package root.iv.digitalsignalprocessing.ui;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import root.iv.digitalsignalprocessing.R;
import root.iv.digitalsignalprocessing.ui.fragments.KotelnikovFragment;
import root.iv.digitalsignalprocessing.ui.fragments.Lab2Fragment;

public class MainActivity extends AppCompatActivity implements DrawerClosed {
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuNavigationLab1:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, KotelnikovFragment.getInstance())
                            .commit();
                    break;

                case R.id.menuNavigationLab2:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, Lab2Fragment.getInstance())
                            .commit();
                    break;
            }

//            drawerLayout.closeDrawer(GravityCompat.START, true);
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START, true);
                return true;
        }

        return false;
    }

    @Override
    public void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START, true);
    }
}
