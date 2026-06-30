package com.example.nusalestari.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nusalestari.DetailActivity;
import com.example.nusalestari.R;
import com.example.nusalestari.adapter.EndemikAdapter;
import com.example.nusalestari.database.AppDatabase;
import com.example.nusalestari.model.Endemik;

import java.util.List;

public class HewanFragment extends Fragment {

    private EndemikAdapter adapter;
    private TextView tvEmpty;

    public HewanFragment() {
        super(R.layout.fragment_hewan);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rv_hewan);
        tvEmpty = view.findViewById(R.id.tv_empty_hewan);

        adapter = new EndemikAdapter(endemik -> {
            Intent intent = new Intent(requireContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_ENDEMIK_ID, endemik.getId());
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recyclerView.setAdapter(adapter);

        loadData();
    }

    private void loadData() {
        AppDatabase database = AppDatabase.getInstance(requireContext());

        List<Endemik> data = database.endemikDao()
                .getEndemikByTipe("Hewan");

        adapter.setData(data);
        tvEmpty.setVisibility(data.isEmpty() ? View.VISIBLE : View.GONE);
    }
}