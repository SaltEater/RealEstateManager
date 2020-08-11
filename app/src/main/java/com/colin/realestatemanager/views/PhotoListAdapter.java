package com.colin.realestatemanager.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.colin.realestatemanager.R;
import com.colin.realestatemanager.models.Photo;
import java.util.List;
import butterknife.BindView;
import static butterknife.ButterKnife.bind;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder> {
    private List<Photo> photos;
    private boolean clickable = false;

    public PhotoListAdapter(List<Photo> photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_photo, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.photoText.setText(photo.getName());
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(photo.getPath());
            bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
            holder.photoImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
        notifyDataSetChanged();
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photo_img)
        ImageView photoImage;
        @BindView(R.id.photo_txt)
        TextView photoText;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION && isClickable()) {
                    photos.remove(photos.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            });
        }
    }

}
