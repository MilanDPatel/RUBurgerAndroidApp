package com.example.recycleapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleapplication.model.Item;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * ItemsAdapter class to display items in RecyclerView.
 * @author Aditya Shah
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Item> items;

    /**
     * Constructor for ItemsAdapter.
     * @param context the context of where the adapter is being used.
     * @param items the items to display.
     */
    public ItemsAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    /**
     * Creates the ViewHolder and item layout.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return a ViewHolder instance.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds data to the ViewHolder.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemName.setText(item.getItemName());
        holder.itemPrice.setText(formatPrice(item.getPrice()));
        holder.itemImage.setImageResource(item.getImageResourceId());

        holder.itemView.setOnClickListener(v -> {
            // Instead of directly adding to cart, launch SizesActivity
            Intent intent = new Intent(context, SizesActivity.class);
            intent.putExtra("item_name", item.getItemName());
            intent.putExtra("image_res_id", item.getImageResourceId());
            intent.putExtra("base_price", item.getPrice());
            context.startActivity(intent);
        });
    }

    /**
     * Formats a price into a string.
     * @param price the price to format.
     * @return the price as a formatted string.
     */
    private String formatPrice(double price) {
        DecimalFormat df = new DecimalFormat("$#,##0.00");
        return df.format(price);
    }

    /**
     * Retrieves the number of items in the list.
     * @return the number of items.
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * ViewHolder class to hold views.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName;
        TextView itemPrice;

        /**
         * Constructor for ViewHolder.
         * @param itemView the view of the layout.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
        }
    }
}