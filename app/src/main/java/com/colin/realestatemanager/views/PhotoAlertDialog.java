package com.colin.realestatemanager.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.colin.realestatemanager.R;
import com.colin.realestatemanager.utils.ImagePickerUtils;

import butterknife.BindView;
import static butterknife.ButterKnife.bind;

public class PhotoAlertDialog extends AppCompatDialogFragment {

    @BindView(R.id.photo_name_ed)
    EditText photoEditText;

    private PhotoAlertDialogListener listener;

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
                    checkClick(photoEditText.getText().toString().trim(), ImagePickerUtils.CAMERA_REQUEST);
                })
                .setPositiveButton("Gallery", (dialog, which) -> {
                    checkClick(photoEditText.getText().toString().trim(), ImagePickerUtils.GALLERY_REQUEST);
                });

        return builder.create();
    }

    private void checkClick(String name, int tag) {
        if (name.equals("")) {
            Toast.makeText(requireContext(), "Please specify a name", Toast.LENGTH_SHORT).show();
        } else {
            listener.dialogResult(name, tag);
        }
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
        void dialogResult(String name, int tag);
    }
}
