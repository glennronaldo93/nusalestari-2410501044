package com.example.nusalestari;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.nusalestari.ui.HewanFragment;
import com.example.nusalestari.ui.TumbuhanFragment;
import com.example.nusalestari.util.ThemeManager;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout navHewan;
    private LinearLayout navTumbuhan;
    private ImageView imgHewan;
    private ImageView imgTumbuhan;
    private TextView tvHewan;
    private TextView tvTumbuhan;

    private boolean hewanAktif = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnSearch = findViewById(R.id.btn_search);
        ImageButton btnFavorit = findViewById(R.id.btn_favorit);
        ImageButton btnProfile = findViewById(R.id.btn_profile);
        SwitchCompat switchTheme = findViewById(R.id.switch_theme);

        navHewan = findViewById(R.id.nav_hewan_container);
        navTumbuhan = findViewById(R.id.nav_tumbuhan_container);
        imgHewan = findViewById(R.id.img_nav_hewan);
        imgTumbuhan = findViewById(R.id.img_nav_tumbuhan);
        tvHewan = findViewById(R.id.tv_nav_hewan);
        tvTumbuhan = findViewById(R.id.tv_nav_tumbuhan);

        switchTheme.setChecked(ThemeManager.isDarkMode(this));

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) ->
                ThemeManager.setDarkMode(HomeActivity.this, isChecked)
        );

        btnSearch.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, SearchActivity.class))
        );

        btnFavorit.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, FavoritActivity.class))
        );

        btnProfile.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class))
        );

        navHewan.setOnClickListener(v -> pilihMenu(true));
        navTumbuhan.setOnClickListener(v -> pilihMenu(false));

        if (savedInstanceState != null) {
            hewanAktif = savedInstanceState.getBoolean("hewan_aktif", true);
        }

        pilihMenu(hewanAktif);
    }

    private void pilihMenu(boolean pilihHewan) {
        hewanAktif = pilihHewan;

        int aktif = ContextCompat.getColor(this, R.color.nav_active);
        int tidakAktif = ContextCompat.getColor(this, R.color.nav_inactive);

        imgHewan.setColorFilter(pilihHewan ? aktif : tidakAktif);
        tvHewan.setTextColor(pilihHewan ? aktif : tidakAktif);

        imgTumbuhan.setColorFilter(pilihHewan ? tidakAktif : aktif);
        tvTumbuhan.setTextColor(pilihHewan ? tidakAktif : aktif);

        if (pilihHewan) {
            showFragment(new HewanFragment());
        } else {
            showFragment(new TumbuhanFragment());
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("hewan_aktif", hewanAktif);
        super.onSaveInstanceState(outState);
    }
}