package com.colin.realestatemanager.controllers.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.colin.realestatemanager.BuildConfig;
import com.colin.realestatemanager.R;
import com.colin.realestatemanager.models.EstateWithPhotos;
import com.colin.realestatemanager.utils.Utils;
import com.colin.realestatemanager.views.PhotoListAdapter;
import java.util.Collections;
import butterknife.BindView;

import static butterknife.ButterKnife.bind;

public class EstateDetailsFragment extends Fragment {
    @BindView(R.id.media_recycler_view_details)
    RecyclerView recyclerView;
    @BindView(R.id.surface_value)
    TextView surface;
    @BindView(R.id.number_rooms_value)
    TextView numberRooms;
    @BindView(R.id.number_bathrooms_value)
    TextView numberBathrooms;
    @BindView(R.id.number_bedrooms_value)
    TextView numberBedrooms;
    @BindView(R.id.location_value)
    TextView location;
    @BindView(R.id.description_text)
    TextView description;
    @BindView(R.id.static_map_image)
    ImageView staticMapImage;

    private EstateWithPhotos estate;


    private EstateDetailsFragment () {
    }

    public static EstateDetailsFragment newInstance(EstateWithPhotos estate) {
        EstateDetailsFragment estateDetailsFragment = new EstateDetailsFragment();
        estateDetailsFragment.estate = estate;
        return estateDetailsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estate_details, container, false);
        bind(this, view);
        Collections.sort(estate.getPhotos());
        PhotoListAdapter adapter = new PhotoListAdapter(estate.getPhotos());

        recyclerView.setAdapter(adapter);

        surface.setText(Utils.castIntToString(estate.getEstate().getSurface()));
        numberRooms.setText(Utils.castIntToString(estate.getEstate().getNbRooms()));
        numberBathrooms.setText(Utils.castIntToString(estate.getEstate().getNbBathrooms()));
        numberBedrooms.setText(Utils.castIntToString(estate.getEstate().getNbBedrooms()));

        String address =estate.getEstate().getAddress() + "\n";
        if (estate.getEstate().getComplement() != null && !estate.getEstate().getComplement().equals("")) {
            address += estate.getEstate().getComplement() + "\n";
        }
        address += estate.getEstate().getCity() + "\n";
        address += estate.getEstate().getPostalCode() + "\n";
        address += estate.getEstate().getState();
        location.setText(address);

        description.setText(estate.getEstate().getDescription());

        Glide.with(requireContext())
                .load(getString(R.string.static_map_query) + getUrlAddress() + "&key=" + BuildConfig.google_maps_api)
                .centerCrop()
                .into(staticMapImage);

        return view;
    }

    private String getUrlAddress() {
        String address = estate.getEstate().getAddress() + "+";
        address += estate.getEstate().getCity() + "+";
        address += estate.getEstate().getPostalCode() + "+";
        address += estate.getEstate().getState();
        return address.replace(' ', '+');
        }

}
