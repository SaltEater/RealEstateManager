package com.colin.realestatemanager.controllers.activities;

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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.colin.realestatemanager.R;
import com.colin.realestatemanager.models.EstateWithPhotos;
import com.colin.realestatemanager.models.Photo;
import com.colin.realestatemanager.utils.ImagePickerUtils;
import com.colin.realestatemanager.utils.Utils;
import com.colin.realestatemanager.viewmodels.ModifyViewModel;
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

public class ModifyEstateActivity extends AppCompatActivity implements PhotoAlertDialog.PhotoAlertDialogListener, DatePickerDialog.OnDateSetListener {
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

    private ModifyViewModel modifyViewModel;

    private EstateWithPhotos estateWithPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_estate);
        bind(this);
        estateWithPhotos = (EstateWithPhotos) getIntent().getSerializableExtra("Estate");
        modifyViewModel = new ViewModelProvider(this).get(ModifyViewModel.class);
        modifyViewModel.init(estateWithPhotos);
        configDropdownMenu();
        configToolbar();
        configRecyclerView();
        configSwitch();
        configEntryDate();
        configSoldDate();
        configFields();
    }


    // ---------------------------  
    // TOOLBAR
    // ---------------------------

    private void configToolbar() {
        toolbar.setTitle("Modify an Estate");
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // --------------
    // VIEWS
    // --------------

    private void configDropdownMenu() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.estate_type_dropdown_items, estateTypes);
        estateTypeAutoComplete.setAdapter(adapter);
        estateTypeAutoComplete.setText(estateWithPhotos.getEstate().getType());
    }

    private void configRecyclerView() {
        adapter = new PhotoListAdapter(modifyViewModel.getPhotos());
        adapter.setClickable(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void configSwitch() {
        soldSwitch.setChecked(estateWithPhotos.getEstate().isStatus());
        soldDate.setEnabled(soldSwitch.isChecked());
        soldSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            soldDate.setEnabled(isChecked);
            if (!isChecked) {
                soldDate.setText("");
            }
        });
    }

    private void configEntryDate() {
        Date d = new Date(estateWithPhotos.getEstate().getBeginDate());
        entryDate.setText(Utils.dateToString(d));
        entryDate.setClickable(false);
    }

    private void configSoldDate() {
        soldDate.setFocusable(false);
        soldDate.setClickable(false);
        if (soldDate.isEnabled()) {
            Date d = new Date(estateWithPhotos.getEstate().getEndDate());
            soldDate.setText(Utils.dateToString(d));
        }
    }

    private void configFields() {
        priceText.setText(String.valueOf(estateWithPhotos.getEstate().getPrice()));
        addressText.setText(estateWithPhotos.getEstate().getAddress());
        descriptionText.setText(estateWithPhotos.getEstate().getDescription());
        agentName.setText(estateWithPhotos.getEstate().getRealEstateAgent());
        postalCodeText.setText(estateWithPhotos.getEstate().getPostalCode());
        cityText.setText(estateWithPhotos.getEstate().getCity());
        complementText.setText(estateWithPhotos.getEstate().getComplement());
        stateText.setText(estateWithPhotos.getEstate().getState());
        surfaceText.setText(String.valueOf(estateWithPhotos.getEstate().getSurface()));
        numberBathroomsText.setText(String.valueOf(estateWithPhotos.getEstate().getNbBathrooms()));
        numberBedroomsText.setText(String.valueOf(estateWithPhotos.getEstate().getNbBedrooms()));
        numberRoomsText.setText(String.valueOf(estateWithPhotos.getEstate().getNbRooms()));
        libraryChip.setChecked(estateWithPhotos.getEstate().isLibrary());
        parkChip.setChecked(estateWithPhotos.getEstate().isPark());
        restaurantChip.setChecked(estateWithPhotos.getEstate().isRestaurant());
        schoolChip.setChecked(estateWithPhotos.getEstate().isSchool());
        swimmingPoolChip.setChecked(estateWithPhotos.getEstate().isSwimmingPool());
        shopChip.setChecked(estateWithPhotos.getEstate().isShop());
        createEstateButton.setText(R.string.modify);
    }

    @OnClick(R.id.add_photo_img)
    public void onClickAddPhoto() {
        PhotoAlertDialog dialog = new PhotoAlertDialog();
        dialog.show(getSupportFragmentManager(), "photoAlertDialog");
    }

    @OnClick(R.id.create_estate_button)
    public void onClickModifyEstate() {
        modifyEstate();
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
        soldDate.setText(Utils.calendarToString(c));
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

    private boolean checkEstateEdit() {
        return (!Utils.getText(agentName).equals("")) && (!Utils.getText(entryDate).equals("")) && (!soldSwitch.isChecked() || !Utils.getText(soldDate).equals(""))
                && (!estateTypeAutoComplete.getText().toString().equals("")) && (!Utils.getText(priceText).equals("")) && (!Utils.getText(descriptionText).equals(""))
                && (!Utils.getText(addressText).equals("")) && (!Utils.getText(postalCodeText).equals("")) && (!Utils.getText(cityText).equals(""))
                && (!Utils.getText(stateText).equals("")) && (!Utils.getText(surfaceText).equals("")) && (!Utils.getText(numberRoomsText).equals(""))
                && (!Utils.getText(numberBathroomsText).equals("")) && (!Utils.getText(numberBedroomsText).equals("")) && (modifyViewModel.getPhotos().size() != 0);
    }

    private void modifyEstate() {
        if (checkEstateEdit()) {
            editEstateValues();
            modifyPhotos();
            finish();
        }
    }

    private void modifyPhotos() {
        if (!checkPhotos()) {
            for (int i = 0; i < estateWithPhotos.getPhotos().size(); i++) {
                modifyViewModel.deletePhoto(estateWithPhotos.getPhotos().get(i));
            }
            for (int i = 0; i < modifyViewModel.getPhotos().size(); i++) {
                modifyViewModel.getPhotos().get(i).setOrder((byte) i);
                modifyViewModel.insertPhoto(modifyViewModel.getPhotos().get(i));
            }
        }
    }

    private boolean checkPhotos() {
        if (estateWithPhotos.getPhotos().size() != modifyViewModel.getPhotos().size())
            return false;
        for (int i = 0; i < modifyViewModel.getPhotos().size(); i++)
            if (modifyViewModel.getPhotos().get(i) != estateWithPhotos.getPhotos().get(i))
                return false;
        return true;
    }

    private void editEstateValues() {
        Date beginDate = Utils.stringToDate(Utils.getText(entryDate));
        Date endDate = Utils.stringToDate(Utils.getText(soldDate));

        estateWithPhotos.getEstate().setRealEstateAgent(Utils.getText(agentName));
        estateWithPhotos.getEstate().setStatus(soldSwitch.isChecked());
        if (beginDate != null && endDate != null) {
            estateWithPhotos.getEstate().setBeginDate(beginDate.getTime());
            estateWithPhotos.getEstate().setEndDate(endDate.getTime());
        }
        estateWithPhotos.getEstate().setType(estateTypeAutoComplete.getText().toString());
        estateWithPhotos.getEstate().setPrice(Integer.parseInt(Utils.getText(priceText)));
        estateWithPhotos.getEstate().setDescription(Utils.getText(descriptionText));
        estateWithPhotos.getEstate().setAddress(Utils.getText(addressText));
        estateWithPhotos.getEstate().setComplement(Utils.getText(complementText));
        estateWithPhotos.getEstate().setPostalCode(Utils.getText(postalCodeText));
        estateWithPhotos.getEstate().setCity(Utils.getText(cityText));
        estateWithPhotos.getEstate().setState(Utils.getText(stateText));
        estateWithPhotos.getEstate().setSurface(Utils.getTextAsInteger(surfaceText));
        estateWithPhotos.getEstate().setNbRooms(Utils.getTextAsInteger(numberRoomsText));
        estateWithPhotos.getEstate().setNbBathrooms(Utils.getTextAsInteger(numberBathroomsText));
        estateWithPhotos.getEstate().setNbBedrooms(Utils.getTextAsInteger(numberBedroomsText));
        estateWithPhotos.getEstate().setSchool(schoolChip.isChecked());
        estateWithPhotos.getEstate().setSwimmingPool(swimmingPoolChip.isChecked());
        estateWithPhotos.getEstate().setShop(shopChip.isChecked());
        estateWithPhotos.getEstate().setLibrary(libraryChip.isChecked());
        estateWithPhotos.getEstate().setPark(parkChip.isChecked());
        estateWithPhotos.getEstate().setRestaurant(restaurantChip.isChecked());

        modifyViewModel.updateEstate(estateWithPhotos.getEstate());
    }
}
