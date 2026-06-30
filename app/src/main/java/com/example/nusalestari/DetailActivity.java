package com.example.nusalestari;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.nusalestari.database.AppDatabase;
import com.example.nusalestari.model.Endemik;
import com.example.nusalestari.model.Favorit;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_ENDEMIK_ID = "extra_endemik_id";

    private AppDatabase database;
    private Endemik endemik;
    private ImageButton btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = AppDatabase.getInstance(this);

        ImageButton btnBack = findViewById(R.id.btn_back);
        btnFavorite = findViewById(R.id.btn_favorite);

        btnBack.setOnClickListener(v -> finish());
        btnFavorite.setOnClickListener(v -> toggleFavorite());

        String idEndemik = getIntent().getStringExtra(EXTRA_ENDEMIK_ID);

        if (TextUtils.isEmpty(idEndemik)) {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        endemik = database.endemikDao().getEndemikById(idEndemik);

        if (endemik == null) {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tampilkanData();
        updateFavoriteIcon();
    }

    private void tampilkanData() {
        ImageView imgDetail = findViewById(R.id.img_detail);
        TextView tvNama = findViewById(R.id.tv_nama_detail);
        TextView tvNamaLatin = findViewById(R.id.tv_nama_latin_detail);
        TextView tvStatus = findViewById(R.id.tv_status_detail);
        TextView tvAsal = findViewById(R.id.tv_asal_detail);
        TextView tvSebaran = findViewById(R.id.tv_sebaran_detail);
        TextView tvFamili = findViewById(R.id.tv_famili_detail);
        TextView tvGenus = findViewById(R.id.tv_genus_detail);
        TextView tvDeskripsi = findViewById(R.id.tv_deskripsi_detail);

        tvNama.setText(valueOrStrip(endemik.getNama()));
        tvNamaLatin.setText(valueOrStrip(endemik.getNamaLatin()));
        tvStatus.setText("Status: " + valueOrStrip(endemik.getStatus()));
        tvAsal.setText(valueOrStrip(endemik.getAsal()));
        tvSebaran.setText(valueOrStrip(endemik.getSebaran()));
        tvFamili.setText(valueOrStrip(endemik.getFamili()));
        tvGenus.setText(valueOrStrip(endemik.getGenus()));
        tvDeskripsi.setText(valueOrStrip(endemik.getDeskripsi()));

        Glide.with(this)
                .load(endemik.getFoto())
                .placeholder(R.drawable.bg_image_placeholder)
                .error(R.drawable.bg_image_placeholder)
                .centerCrop()
                .into(imgDetail);
    }

    private String valueOrStrip(String value) {
        return TextUtils.isEmpty(value) ? "-" : value;
    }

    private void toggleFavorite() {
        int jumlahFavorit = database.favoritDao().checkFavorit(endemik.getId());

        if (jumlahFavorit > 0) {
            database.favoritDao().deleteFavoritById(endemik.getId());
            Toast.makeText(this, "Dihapus dari favorit", Toast.LENGTH_SHORT).show();
        } else {
            database.favoritDao().insertFavorit(new Favorit(endemik));
            Toast.makeText(this, "Ditambahkan ke favorit", Toast.LENGTH_SHORT).show();
        }

        updateFavoriteIcon();
    }

    private void updateFavoriteIcon() {
        int jumlahFavorit = database.favoritDao().checkFavorit(endemik.getId());

        if (jumlahFavorit > 0) {
            btnFavorite.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            btnFavorite.setImageResource(R.drawable.ic_favorite_border);
        }
    }
}