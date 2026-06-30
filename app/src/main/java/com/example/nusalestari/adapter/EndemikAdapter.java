package com.example.nusalestari.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nusalestari.R;
import com.example.nusalestari.model.Endemik;

import java.util.ArrayList;
import java.util.List;

public class EndemikAdapter extends RecyclerView.Adapter<EndemikAdapter.EndemikViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Endemik endemik);
    }

    private final List<Endemik> endemikList = new ArrayList<>();
    private final OnItemClickListener listener;

    public EndemikAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<Endemik> data) {
        endemikList.clear();

        if (data != null) {
            endemikList.addAll(data);
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EndemikViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_endemik, parent, false);

        return new EndemikViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EndemikViewHolder holder, int position) {
        Endemik item = endemikList.get(position);

        holder.tvNama.setText(
                TextUtils.isEmpty(item.getNama()) ? "Tanpa nama" : item.getNama()
        );

        if (TextUtils.isEmpty(item.getNamaLatin())) {
            holder.tvNamaLatin.setVisibility(View.GONE);
        } else {
            holder.tvNamaLatin.setVisibility(View.VISIBLE);
            holder.tvNamaLatin.setText(item.getNamaLatin());
        }

        if (TextUtils.isEmpty(item.getFoto())) {
            holder.imgEndemik.setImageResource(R.drawable.bg_image_placeholder);
        } else {
            Glide.with(holder.itemView)
                    .load(item.getFoto())
                    .placeholder(R.drawable.bg_image_placeholder)
                    .error(R.drawable.bg_image_placeholder)
                    .centerCrop()
                    .into(holder.imgEndemik);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return endemikList.size();
    }

    static class EndemikViewHolder extends RecyclerView.ViewHolder {

        ImageView imgEndemik;
        TextView tvNama;
        TextView tvNamaLatin;

        public EndemikViewHolder(@NonNull View itemView) {
            super(itemView);

            imgEndemik = itemView.findViewById(R.id.img_endemik);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvNamaLatin = itemView.findViewById(R.id.tv_nama_latin);
        }
    }
}