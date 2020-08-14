package com.colin.realestatemanager.controllers.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import com.colin.realestatemanager.R;
import com.colin.realestatemanager.models.Estate;
import com.colin.realestatemanager.models.Photo;
import com.colin.realestatemanager.utils.ImagePickerUtils;
import com.colin.realestatemanager.utils.Utils;
import com.colin.realestatemanager.viewmodels.CreateEstateViewModel;
import com.colin.realestatemanager.views.PhotoAlertDialog;
import com.colin.realestatemanager.views.PhotoListAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Calendar;
import java.util.Date;
import butterknife.BindView;
import butterknife.OnClick;
import static butterknife.ButterKnife.bind;

public class CreateEstateActivity extends AppCompatActivity implements PhotoAlertDialog.PhotoAlertDialogListener, DatePickerDialog.OnDateSetListener {
    @BindView(R.id.estate_type_dropdown_layout)
    TextInputLayout estateTypeDropdownMenu;
    @BindView(R.id.estate_type_autocomplete)
    AppCompatAutoCompleteTextView estateTypeAutoComplete;
    @BindView(R.id.create_estate_toolbar)
    Toolbar toolbar;
    @BindView(R.id.estate_ed_price)
    TextInputEditText priceText;
    @BindView(R.id.estate_ed_description)
    TextInputEditText descriptionText;
    @BindView(R.id.address_way)
    TextInputEditText addressText;
    @BindView(R.id.address_complement)
    TextInputEditText complementText;
    @BindView(R.id.address_postal_code)
    TextInputEditText postalCodeText;
    @BindView(R.id.address_city)
    TextInputEditText cityText;
    @BindView(R.id.address_state)
    TextInputEditText stateText;
    @BindView(R.id.estate_ed_surface)
    TextInputEditText surfaceText;
    @BindView(R.id.estate_ed_numbers_rooms)
    TextInputEditText numberRoomsText;
    @BindView(R.id.estate_ed_numbers_bathrooms)
    TextInputEditText numberBathroomsText;
    @BindView(R.id.estate_bed_numbers_bedrooms)
    TextInputEditText numberBedroomsText;
    @BindView(R.id.estate_chip_school)
    Chip schoolChip;
    @BindView(R.id.estate_chip_swimming_pool)
    Chip swimmingPoolChip;
    @BindView(R.id.estate_chip_shop)
    Chip shopChip;
    @BindView(R.id.estate_chip_library)
    Chip libraryChip;
    @BindView(R.id.estate_chip_park)
    Chip parkChip;
    @BindView(R.id.estate_chip_restaurant)
    Chip restaurantChip;
    @BindView(R.id.add_photo_img)
    ImageView addPhotoImage;
    @BindView(R.id.photo_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.create_estate_button)
    Button createEstateButton;
    @BindView(R.id.real_estate_agent_name_ed)
    TextInputEditText agentName;

    @BindView(R.id.estate_entry_date_ed)
    TextInputEditText entryDate;
    @BindView(R.id.estate_sold_date_ed)
    TextInputEditText soldDate;
    @BindView(R.id.sold_estate_switch)
    Switch soldSwitch;

    private String[] estateTypes = new String[]{"Apartment", "Chalet", "Bungalow", "Mansion"};
    private PhotoListAdapter adapter;

    private CreateEstateViewModel createEstateViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_estate);
        bind(this);
        createEstateViewModel = new ViewModelProvider(this).get(CreateEstateViewModel.class);
        configDropdownMenu();
        configToolbar();
        configRecyclerView();
        configEntryDate();
        configSoldDate();
        configSwitch();
    }


    // ---------------------------
    // TOOLBAR
    // ---------------------------

    private void configToolbar() {
        toolbar.setTitle("Create an Estate");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_estate_activity_menu, menu);
        return true;
    }

    // --------------
    // VIEWS
    // --------------

    private void configDropdownMenu() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.estate_type_dropdown_items, estateTypes);
        estateTypeAutoComplete.setAdapter(adapter);
    }

    private void configRecyclerView() {
        adapter = new PhotoListAdapter(createEstateViewModel.getPhotosLiveData().getValue());
        adapter.setClickable(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void configSwitch() {
        soldSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            soldDate.setEnabled(isChecked);
            if (!isChecked) {
                soldDate.setText("");
            }
        });
    }

    private void configEntryDate() {
        Calendar c = Calendar.getInstance();
        entryDate.setText(Utils.calendarToString(c));
        entryDate.setClickable(false);
    }

    private void configSoldDate() {
        soldDate.setFocusable(false);
        soldDate.setClickable(false);
        soldDate.setEnabled(false);
    }

    @OnClick(R.id.add_photo_img)
    public void onClickAddPhoto() {
        PhotoAlertDialog dialog = new PhotoAlertDialog();
        dialog.show(getSupportFragmentManager(), "photoAlertDialog");
    }

    @OnClick(R.id.create_estate_button)
    public void onClickCreateEstate() {
        createEstate();
    }

    @OnClick(R.id.estate_sold_date_ed)
    public void onClickSoldDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        if (Utils.compareDates(Utils.getText(entryDate), Utils.calendarToString(c)) <= 0) {
            soldDate.setText(Utils.calendarToString(c));
        }
    }

    // ---------------
    // DIALOG
    // --------------

    private String photoName = "";

    @Override
    public void dialogResult(String name, int tag) {
        photoName = name;
        if (ImagePickerUtils.checkAndRequestPerms(this, tag)) {
            startActivityForResult(ImagePickerUtils.createIntent(tag), tag);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ImagePickerUtils.GALLERY_REQUEST || requestCode == ImagePickerUtils.CAMERA_REQUEST) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(ImagePickerUtils.createIntent(requestCode), requestCode);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == ImagePickerUtils.CAMERA_REQUEST) {
            Photo photo = new Photo(photoName, ImagePickerUtils.getCameraFilePath(), -1);
            adapter.addPhoto(photo);
        } else if (resultCode == RESULT_OK && requestCode == ImagePickerUtils.GALLERY_REQUEST && data != null) {
            Photo photo = new Photo(photoName, ImagePickerUtils.getGalleryFilePath(data, this), -1);
            adapter.addPhoto(photo);
        }
    }

    // --------------
    // ESTATE
    // --------------

    private boolean checkEstateCreation() {
        boolean b = (!Utils.getText(agentName).equals("")) && (!Utils.getText(entryDate).equals("")) && (!soldSwitch.isChecked() || !Utils.getText(soldDate).equals(""))
                && (!estateTypeAutoComplete.getText().toString().equals("")) && (!Utils.getText(priceText).equals("")) && (!Utils.getText(descriptionText).equals(""))
                && (!Utils.getText(addressText).equals("")) && (!Utils.getText(postalCodeText).equals("")) && (!Utils.getText(cityText).equals(""))
                && (!Utils.getText(stateText).equals("")) && (!Utils.getText(surfaceText).equals("")) && (!Utils.getText(numberRoomsText).equals(""))
                && (!Utils.getText(numberBathroomsText).equals("")) && (!Utils.getText(numberBedroomsText).equals("")) && (createEstateViewModel.getPhotosLiveData().getValue().size() != 0);
        if (!b) {
            Toast.makeText(this, "Some required fields are missing !", Toast.LENGTH_SHORT).show();
        }
        return b;
    }

    private void createEstate() {
        if (checkEstateCreation()) {
            Date beginDate = Utils.stringToDate(Utils.getText(entryDate));
            Date endDate = Utils.stringToDate(Utils.getText(soldDate));

            String realEstateAgentName = Utils.getText(agentName);
            String type = estateTypeAutoComplete.getText().toString();
            String price = Utils.getText(priceText);
            String description = Utils.getText(descriptionText);
            String address = Utils.getText(addressText);
            String complement = Utils.getText(complementText);
            String postalCode = Utils.getText(postalCodeText);
            String city = Utils.getText(cityText);
            String state = Utils.getText(stateText);
            String surface = Utils.getText(surfaceText);
            String numberRooms = Utils.getText(numberRoomsText);
            String numberBathrooms = Utils.getText(numberBathroomsText);
            String numberBedrooms =Utils.getText( numberBedroomsText);
            boolean status = soldSwitch.isChecked();
            boolean school = schoolChip.isChecked();
            boolean swimmingPool = swimmingPoolChip.isChecked();
            boolean shop = shopChip.isChecked();
            boolean library = libraryChip.isChecked();
            boolean park = parkChip.isChecked();
            boolean restaurant = restaurantChip.isChecked();

            Estate estate = new Estate(realEstateAgentName, status, beginDate.getTime(), endDate.getTime(), description, type, Integer.parseInt(price), address, complement, postalCode,
                    city, state, Integer.parseInt(surface), Integer.parseInt(numberRooms), Integer.parseInt(numberBedrooms),
                    Integer.parseInt(numberBathrooms), school, swimmingPool, shop, library, park, restaurant);
            createEstateViewModel.insertPhotos(estate.getEstateId());
            createEstateViewModel.insertEstate(estate);
            Toast.makeText(this,"Estate created", Toast.LENGTH_SHORT)
                    .show();
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}