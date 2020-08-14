package com.colin.realestatemanager.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class ImagePickerUtils {
    public static final String[] galleryPerms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final String[] cameraPerms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};;
    public static final int CAMERA_REQUEST = 1090;
    public static final int GALLERY_REQUEST = 8769;

    public static boolean checkAndRequestPerms(AppCompatActivity activity, int tag) {
        List<String> permsNeeded = new ArrayList<>();
        String[] perms = null;
        if (tag == GALLERY_REQUEST) {
            perms = galleryPerms;
        } else if (tag == CAMERA_REQUEST) {
            perms = cameraPerms;
        }
        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(activity, perm) != PackageManager.PERMISSION_GRANTED) {
                permsNeeded.add(perm);
            }
        }
        if (!permsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permsNeeded.toArray(new String[permsNeeded.size()]), tag);
            return false;
        }
        return true;
    }

    public static Intent createIntent(int tag) {
        Intent intent = null;
        switch (tag) {
            case GALLERY_REQUEST:
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                break;
            case CAMERA_REQUEST:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                break;
        }
        return intent;
    }


    public static String getCameraFilePath() {
        File f = new File(Environment.getExternalStorageDirectory()
                .toString());
        for (File temp : f.listFiles()) {
            if (temp.getName().equals("temp.jpg")) {
                f = temp;
                break;
            }
        }
        return f.getAbsolutePath();
    }

    public static String getGalleryFilePath(@NonNull Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor c = context.getContentResolver().query(selectedImage, filePath,
                null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        String selectedImagePath = c.getString(columnIndex);
        c.close();
        return selectedImagePath;
    }
}
