package com.colin.realestatemanager.views;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.colin.realestatemanager.R;
import com.colin.realestatemanager.models.EstateWithPhotos;
import com.colin.realestatemanager.models.Photo;
import java.util.List;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

public class EstateListAdapter extends RecyclerView.Adapter<EstateListAdapter.EstateViewHolder> {
    private List<EstateWithPhotos> estates;
    private OnItemClickListener listener;
    private int selectedPosition = -1;
    private Context context;

    public EstateListAdapter(List<EstateWithPhotos> estates, OnItemClickListener listener, Context context) {
        this.estates = estates;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public EstateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_estate, parent, false);
        return new EstateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EstateViewHolder holder, int position) {
        EstateWithPhotos estate = estates.get(position);

        if (selectedPosition == position) {
            holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.design_default_color_background));
        }
        holder.estateImage.setImageURI(Uri.parse(getFirstImage(estate).getPath()));
        holder.estateTitle.setText(estate.getEstate().getType());
        holder.estateCity.setText(estate.getEstate().getCity());
        String price = "$" + estate.getEstate().getPrice();
        holder.estatePrice.setText(price);
    }

    @Override
    public int getItemCount() {
        return estates.size();
    }

    private Photo getFirstImage(EstateWithPhotos estate) {
        for (Photo photo :estate.getPhotos()) {
            if (photo.getOrder() == 0) {
                return photo;
            }
        }
        throw new NullPointerException("First image not found");
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }


    public class EstateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.estate_image)
        ImageView estateImage;
        @BindView(R.id.estate_title)
        TextView estateTitle;
        @BindView(R.id.estate_city)
        TextView estateCity;
        @BindView(R.id.estate_price)
        TextView estatePrice;
        @BindView(R.id.estate_card_view)
        CardView cardView;

        public EstateViewHolder(@NonNull View itemView) {
            super(itemView);
            bind(this,itemView);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    if (selectedPosition == position) {
                        listener.onItemClick(estates.get(position));
                    }
                    else {
                        if (selectedPosition != -1) {
                            notifyItemChanged(selectedPosition);
                        }
                        selectedPosition = position;
                       notifyItemChanged(position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(EstateWithPhotos estate);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
