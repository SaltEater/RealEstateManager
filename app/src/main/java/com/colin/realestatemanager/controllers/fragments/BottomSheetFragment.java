package com.colin.realestatemanager.controllers.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.lifecycle.ViewModelProvider;
import com.colin.realestatemanager.R;
import com.colin.realestatemanager.models.Filters;
import com.colin.realestatemanager.utils.Utils;
import com.colin.realestatemanager.viewmodels.EstateListViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomSheetFragment extends BottomSheetDialogFragment implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.real_estate_agent_name_ed_dial)
    TextInputEditText agentName;
    @BindView(R.id.radio_button_all)
    RadioButton radioAll;
    @BindView(R.id.radio_button_on_sale)
    RadioButton radioOnSale;
    @BindView(R.id.radio_button_sold)
    RadioButton radioSold;
    @BindView(R.id.entry_date_begin_ed_dial)
    TextInputEditText entryBeginDate;
    @BindView(R.id.entry_date_end_ed_dial)
    TextInputEditText entryEndDate;
    @BindView(R.id.sold_date_begin_ed_dial)
    TextInputEditText soldBeginDate;
    @BindView(R.id.sold_date_end_ed_dial)
    TextInputEditText soldEndDate;
    @BindView(R.id.estate_type_autocomplete_dial)
    AppCompatAutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.estate_ed_price_min_dial)
    TextInputEditText minPrice;
    @BindView(R.id.estate_ed_price_max_dial)
    TextInputEditText maxPrice;

    @BindView(R.id.address_postal_code_dial)
    TextInputEditText postalCode;
    @BindView(R.id.address_city)
    TextInputEditText city;
    @BindView(R.id.address_state)
    TextInputEditText state;

    @BindView(R.id.estate_ed_surface_min_dial)
    TextInputEditText minSurface;
    @BindView(R.id.estate_ed_surface_max_dial)
    TextInputEditText maxSurface;
    @BindView(R.id.estate_ed_numbers_rooms_min_dial)
    TextInputEditText minRooms;
    @BindView(R.id.estate_ed_numbers_rooms_max_dial)
    TextInputEditText maxRooms;
    @BindView(R.id.estate_ed_numbers_bathrooms_min_dial)
    TextInputEditText minBathrooms;
    @BindView(R.id.estate_ed_numbers_bathrooms_max_dial)
    TextInputEditText maxBathrooms;
    @BindView(R.id.estate_bed_numbers_bedrooms_min_dial)
    TextInputEditText minBedrooms;
    @BindView(R.id.estate_bed_numbers_bedrooms_max_dial)
    TextInputEditText maxBedrooms;

    @BindView(R.id.estate_chip_school_dial)
    Chip schoolChip;
    @BindView(R.id.estate_chip_swimming_pool_dial)
    Chip swimmingPoolChip;
    @BindView(R.id.estate_chip_shop_dial)
    Chip shopChip;
    @BindView(R.id.estate_chip_library_dial)
    Chip libraryChip;
    @BindView(R.id.estate_chip_park_dial)
    Chip parkChip;
    @BindView(R.id.estate_chip_restaurant_dial)
    Chip restaurantChip;

    @BindView(R.id.filter_list_done)
    TextView tvDone;
    @BindView(R.id.filter_list_clear)
    TextView tvClear;

    private EstateListViewModel estateListViewModel;
    private String[] estateTypes = new String[]{"Apartment", "Chalet", "Bungalow", "Mansion"};


    public static BottomSheetFragment newInstance() {
        return new BottomSheetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modal_bottom_sheet, container, false);
        ButterKnife.bind(this, view);
        estateListViewModel = new ViewModelProvider(requireActivity()).get(EstateListViewModel.class);
        configViews();
        return view;
    }


    @OnClick(R.id.filter_list_done)
    void onClickDone() {
        estateListViewModel.setFilters(new Filters(
                Utils.getText(agentName),
                autoCompleteTextView.getText().toString().trim(),
                Utils.getText(postalCode),
                Utils.getText(state),
                Utils.getText(city),
                Utils.getText(entryBeginDate),
                Utils.getText(entryEndDate),
                Utils.getText(soldBeginDate),
                Utils.getText(soldEndDate),
                Utils.getText(minPrice),
                Utils.getText(maxPrice),
                Utils.getText(minSurface),
                Utils.getText(maxSurface),
                Utils.getText(minRooms),
                Utils.getText(maxRooms),
                Utils.getText(minBedrooms),
                Utils.getText(maxBedrooms),
                Utils.getText(minBathrooms),
                Utils.getText(maxBathrooms),
                radioSold.isChecked() || radioAll.isChecked(),
                radioOnSale.isChecked() || radioAll.isChecked(),
                schoolChip.isChecked(),
                swimmingPoolChip.isChecked(),
                shopChip.isChecked(),
                libraryChip.isChecked(),
                parkChip.isChecked(),
                restaurantChip.isChecked()));
        dismiss();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.filter_list_clear)
    void onClickClear() {
        estateListViewModel.setFilters(new Filters());
        dismiss();
    }

    private void configViews() {
        configDropdownMenu();
        configDates();
        Filters filters = estateListViewModel.getFilters();

        agentName.setText(Filters.getStringText(filters.getRealEstateAgent()));
        autoCompleteTextView.setText(Filters.getStringText(filters.getType()));
        postalCode.setText(Filters.getStringText(filters.getPostalCode()));
        city.setText(Filters.getStringText(filters.getCity()));
        state.setText(Filters.getStringText(filters.getState()));

        entryBeginDate.setText(Filters.getDateText(filters.getEntryBeginDate()));
        entryEndDate.setText(Filters.getDateText(filters.getEntryEndDate()));
        soldBeginDate.setText(Filters.getDateText(filters.getSoldBeginDate()));
        soldEndDate.setText(Filters.getDateText(filters.getSoldEndDate()));

        minPrice.setText(Filters.getIntegerText(filters.getMinPrice()));
        maxPrice.setText(Filters.getIntegerText(filters.getMaxPrice()));
        minSurface.setText(Filters.getIntegerText(filters.getMinSurface()));
        maxSurface.setText(Filters.getIntegerText(filters.getMaxSurface()));
        minRooms.setText(Filters.getIntegerText(filters.getMinNbRooms()));
        maxRooms.setText(Filters.getIntegerText(filters.getMaxNbRooms()));
        minBathrooms.setText(Filters.getIntegerText(filters.getMinNbBathrooms()));
        maxBathrooms.setText(Filters.getIntegerText(filters.getMaxNbBathrooms()));
        minBedrooms.setText(Filters.getIntegerText(filters.getMinNbBedrooms()));
        maxBedrooms.setText(Filters.getIntegerText(filters.getMaxNbBedrooms()));

        schoolChip.setChecked(filters.getSchool());
        swimmingPoolChip.setChecked(filters.getSwimmingPool());
        shopChip.setChecked(filters.getShop());
        libraryChip.setChecked(filters.getLibrary());
        parkChip.setChecked(filters.getPark());
        restaurantChip.setChecked(filters.getRestaurant());

        radioOnSale.setChecked(filters.getOnSaleStateAccepted());
        radioSold.setChecked(filters.getSoldStateAccepted());
        radioAll.setChecked(filters.getSoldStateAccepted() && filters.getOnSaleStateAccepted());
    }

    private void configDropdownMenu() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.estate_type_dropdown_items, estateTypes);
        autoCompleteTextView.setAdapter(adapter);
    }

    @OnClick({R.id.entry_date_begin_ed_dial, R.id.entry_date_end_ed_dial, R.id.sold_date_begin_ed_dial, R.id.sold_date_end_ed_dial})
    public void onClickDate(View view) {
        triggeredDateId = view.getId();
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private int triggeredDateId;

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        checkDates(Utils.calendarToString(c));
    }

    private void checkDates(String date) {
        switch (triggeredDateId) {
            case R.id.entry_date_begin_ed_dial :
                if (Utils.getText(entryEndDate).equals("") || Utils.compareDates(Utils.getText(entryEndDate), date) >= 0) {
                    entryBeginDate.setText(date);
                }
                break;
            case R.id.entry_date_end_ed_dial :
                if (Utils.getText(entryBeginDate).equals("") || Utils.compareDates(Utils.getText(entryBeginDate), date) <= 0) {
                    entryEndDate.setText(date);
                }
                break;
            case R.id.sold_date_begin_ed_dial :
                if (Utils.getText(soldEndDate).equals("") || Utils.compareDates(Utils.getText(entryEndDate), date) >= 0) {
                    soldBeginDate.setText(date);
                }
                break;
            case R.id.sold_date_end_ed_dial :
                if (Utils.getText(soldBeginDate).equals("") || Utils.compareDates(Utils.getText(entryEndDate), date) <= 0) {
                    soldEndDate.setText(date);
                }
                break;
        }
    }

    private void configDates() {
        entryBeginDate.setFocusable(false);
        entryEndDate.setFocusable(false);
        soldBeginDate.setFocusable(false);
        soldEndDate.setFocusable(false);
    }

}
