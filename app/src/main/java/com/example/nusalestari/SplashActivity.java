package com.example.nusalestari;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.nusalestari.api.ApiClient;
import com.example.nusalestari.api.ApiService;
import com.example.nusalestari.database.AppDatabase;
import com.example.nusalestari.model.Endemik;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private TextView tvStatus;
    private ProgressBar progressLoading;
    private Button btnRetry;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvStatus = findViewById(R.id.tv_status);
        progressLoading = findViewById(R.id.progress_loading);
        btnRetry = findViewById(R.id.btn_retry);

        database = AppDatabase.getInstance(this);

        btnRetry.setOnClickListener(v -> loadEndemikData());

        loadEndemikData();
    }

    private void loadEndemikData() {
        btnRetry.setVisibility(View.GONE);
        progressLoading.setVisibility(View.VISIBLE);

        if (database.endemikDao().countEndemik() > 0) {
            tvStatus.setText("Menyiapkan NusaLestari...");
            openHome();
            return;
        }

        tvStatus.setText("Memuat data keanekaragaman hayati...");

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        apiService.getEndemik().enqueue(new Callback<List<Endemik>>() {
            @Override
            public void onResponse(Call<List<Endemik>> call, Response<List<Endemik>> response) {
                if (response.isSuccessful()
                        && response.body() != null
                        && !response.body().isEmpty()) {

                    database.endemikDao().insertAll(response.body());

                    tvStatus.setText("Data siap digunakan");
                    openHome();

                } else {
                    showError("Data dari server tidak tersedia.");
                }
            }

            @Override
            public void onFailure(Call<List<Endemik>> call, Throwable t) {
                showError("Gagal terhubung ke internet.");
            }
        });
    }

    private void showError(String message) {
        tvStatus.setText(message);
        progressLoading.setVisibility(View.GONE);
        btnRetry.setVisibility(View.VISIBLE);
    }

    private void openHome() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }, 500);
    }
}