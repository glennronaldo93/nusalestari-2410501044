package com.example.nusalestari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nusalestari.adapter.EndemikAdapter;
import com.example.nusalestari.database.AppDatabase;
import com.example.nusalestari.model.Endemik;

import java.util.List;

public class FavoritActivity extends AppCompatActivity {

    private EndemikAdapter adapter;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorit);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btn_back_favorit);
        RecyclerView recyclerView = findViewById(R.id.rv_favorit);
        tvEmpty = findViewById(R.id.tv_empty_favorit);

        btnBack.setOnClickListener(v -> finish());

        adapter = new EndemikAdapter(endemik -> {
            Intent intent = new Intent(FavoritActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_ENDEMIK_ID, endemik.getId());
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorit();
    }

    private void loadFavorit() {
        AppDatabase database = AppDatabase.getInstance(this);

        List<Endemik> data = database.favoritDao().getAllEndemikFavorit();

        adapter.setData(data);
        tvEmpty.setVisibility(data.isEmpty() ? View.VISIBLE : View.GONE);
    }
}