package com.colin.realestatemanager.controllers.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.colin.realestatemanager.R;
import com.colin.realestatemanager.models.EstateWithPhotos;
import com.colin.realestatemanager.viewmodels.EstateListViewModel;
import com.colin.realestatemanager.views.EstateListAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

import static butterknife.ButterKnife.bind;

public class EstateListFragment extends Fragment {

    @BindView(R.id.estate_list_recycler_view)
    RecyclerView recyclerView;

    private EstateListAdapter adapter;
    private EstateListAdapter.OnItemClickListener listener;
    List<EstateWithPhotos> estates = new ArrayList<>();
    private EstateListViewModel estateListViewModel;


    public EstateListFragment() {

    }

    public static EstateListFragment newInstance(EstateListAdapter.OnItemClickListener listener) {
        EstateListFragment estateListFragment = new EstateListFragment();
        estateListFragment.listener = listener;
        return estateListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        estateListViewModel = new ViewModelProvider(requireActivity()).get(EstateListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estate_list, container, false);
        bind(this, view);
        configAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    public EstateListAdapter getAdapter() {
        return adapter;
    }

    public EstateWithPhotos getPosition(int position) {
        return estates.get(position);
    }

    private void configAdapter() {
        adapter = new EstateListAdapter(estates, listener, requireContext());
        estateListViewModel.getFilteredEstates().observe(requireActivity(), estateWithPhotos -> {
            estates.clear();
            estates.addAll(estateWithPhotos);
            adapter.notifyDataSetChanged();
        });
        try {
            adapter.setOnItemClickListener((EstateListAdapter.OnItemClickListener) requireContext());

        } catch (ClassCastException e) {
            throw new ClassCastException(requireContext().toString() + "must implement OnItemClickListener");
        }
    }
}
