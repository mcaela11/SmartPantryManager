package com.example.smartpantrymanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.ViewHolder> {

    // Lets the Activity decide what happens when an item is tapped
    public interface OnItemClickListener {
        void onItemClick(PantryItem item);
    }

    private static final int EXPIRY_WARNING_DAYS = 3;
    private static final int STATUS_OK = 0;
    private static final int STATUS_SOON = 1;
    private static final int STATUS_EXPIRED = 2;

    private final List<PantryItem> items;
    private final OnItemClickListener listener;

    public PantryAdapter(List<PantryItem> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PantryItem item = items.get(position);
        holder.name.setText(item.getName());
        holder.quantity.setText(item.getQuantity() + " " + item.getUnit());

        // Read the Settings switch (on by default)
        SharedPreferences prefs = holder.itemView.getContext()
                .getSharedPreferences(SettingsActivity.PREFS_NAME, Context.MODE_PRIVATE);
        boolean alertsOn = prefs.getBoolean(SettingsActivity.KEY_EXPIRY_ALERTS, true);

        String expiryDate = item.getExpiryDate();
        int status = (alertsOn && expiryDate != null && !expiryDate.isEmpty())
                ? getExpiryStatus(expiryDate) : STATUS_OK;

        if (expiryDate == null || expiryDate.isEmpty()) {
            holder.expiry.setText("No expiry date");
        } else if (status == STATUS_EXPIRED) {
            holder.expiry.setText("EXPIRED: " + expiryDate);
        } else if (status == STATUS_SOON) {
            holder.expiry.setText("Expiring soon: " + expiryDate);
        } else {
            holder.expiry.setText("Expires: " + expiryDate);
        }

        // Always set a colour, because RecyclerView reuses rows
        if (status == STATUS_EXPIRED) {
            holder.expiry.setTextColor(Color.parseColor("#C62828")); // red
        } else if (status == STATUS_SOON) {
            holder.expiry.setTextColor(Color.parseColor("#E65100")); // orange
        } else {
            holder.expiry.setTextColor(holder.defaultExpiryColor);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    // Compares the expiry date (yyyy-MM-dd) with today
    private int getExpiryStatus(String expiryDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            format.setLenient(false);
            Date expiry = format.parse(expiryDate);

            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);

            long days = Math.round((expiry.getTime() - today.getTimeInMillis()) / 86400000.0);
            if (days < 0) return STATUS_EXPIRED;
            if (days <= EXPIRY_WARNING_DAYS) return STATUS_SOON;
            return STATUS_OK;
        } catch (ParseException e) {
            return STATUS_OK; // an invalid date is simply not highlighted
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity, expiry;
        int defaultExpiryColor;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textItemName);
            quantity = itemView.findViewById(R.id.textItemQuantity);
            expiry = itemView.findViewById(R.id.textItemExpiry);
            defaultExpiryColor = expiry.getCurrentTextColor(); // remembered so it can be restored
        }
    }
}