package com.colin.realestatemanager.views;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.colin.realestatemanager.R;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import static butterknife.ButterKnife.bind;

public class PhotoAlertDialog extends AppCompatDialogFragment {

    @BindView(R.id.photo_name_ed)
    EditText photoEditText;

    private PhotoAlertDialogListener listener;
    public static final int CAMERA_REQUEST = 1090;
    public static final int GALLERY_PICTURE = 8769;
    public static final int PERMISSION_CODE = 9710;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder strictModeBuilder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(strictModeBuilder.build());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.photo_alert_dialog, null);
        bind(this, view);
        builder.setView(view)
                .setTitle("Add a picture")
                .setMessage("How do you want to set your picture?")
                .setNegativeButton("Camera", (dialog, which) -> {
                    String name = photoEditText.getText().toString();
                    if (!name.equals("")) {
                        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                        if (checkAndRequestPerms(perms)) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));
                            listener.applyText(name, intent, CAMERA_REQUEST);
                        }
                    }
                })
                .setPositiveButton("Gallery", (dialog, which) -> {
                    String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    String name = photoEditText.getText().toString().trim();
                    if (!name.equals("")) {
                        if (checkAndRequestPerms(perms)) {
                            Intent intent = new Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            listener.applyText(name, intent, GALLERY_PICTURE);
                        }
                    }
                });

        return builder.create();
    }

    private boolean checkAndRequestPerms(String[] perms) {
        List<String> permsNeeded = new ArrayList<>();
        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(requireContext(), perm) != PackageManager.PERMISSION_GRANTED) {
                permsNeeded.add(perm);
            }
        }
        if (!permsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(requireActivity(), permsNeeded.toArray(new String[permsNeeded.size()]), PERMISSION_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (PhotoAlertDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement PhotoAlertDialogListener");
        }
    }

    public interface PhotoAlertDialogListener {
        void applyText(String name, Intent intent, int tag);
    }
}
