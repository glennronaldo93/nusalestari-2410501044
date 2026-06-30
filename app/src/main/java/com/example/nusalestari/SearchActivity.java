package com.example.nusalestari;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EndemikAdapter adapter;
    private TextView tvEmpty;
    private EditText etSearch;
    private Spinner spinnerRegion;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = AppDatabase.getInstance(this);

        ImageButton btnBack = findViewById(R.id.btn_back_search);
        ImageButton btnClear = findViewById(R.id.btn_clear_search);
        etSearch = findViewById(R.id.et_search);
        spinnerRegion = findViewById(R.id.spinner_region);
        tvEmpty = findViewById(R.id.tv_empty_search);
        RecyclerView recyclerView = findViewById(R.id.rv_search);

        btnBack.setOnClickListener(v -> finish());

        btnClear.setOnClickListener(v -> etSearch.setText(""));

        adapter = new EndemikAdapter(endemik -> {
            Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_ENDEMIK_ID, endemik.getId());
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        loadRegion();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadSearchResult(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadSearchResult(etSearch.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        loadSearchResult("");
    }

    private void loadRegion() {
        List<String> dataRegion = database.endemikDao().getAllRegion();

        ArrayList<String> pilihanRegion = new ArrayList<>();
        pilihanRegion.add("Semua Region");
        pilihanRegion.addAll(dataRegion);

        ArrayAdapter<String> regionAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                pilihanRegion
        );

        regionAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerRegion.setAdapter(regionAdapter);
    }

    private void loadSearchResult(String keyword) {
        String region = "";

        if (spinnerRegion.getSelectedItem() != null) {
            String regionTerpilih = spinnerRegion.getSelectedItem().toString();

            if (!regionTerpilih.equals("Semua Region")) {
                region = regionTerpilih;
            }
        }

        List<Endemik> data = database.endemikDao()
                .searchEndemikByRegion(keyword.trim(), region);

        adapter.setData(data);
        tvEmpty.setVisibility(data.isEmpty() ? View.VISIBLE : View.GONE);
    }
}