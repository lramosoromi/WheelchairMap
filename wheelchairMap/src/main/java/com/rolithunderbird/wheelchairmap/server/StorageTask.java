package com.rolithunderbird.wheelchairmap.server;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rolithunderbird.wheelchairmap.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by rolithunderbird on 28.06.16.
 */
public class StorageTask extends AsyncTask<Void, String, Void> {

    private Context context;
    private ProgressDialog progressDialog;
    private ArrayList<File> files;
    private boolean downloadSuccess;


    public StorageTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Downloading content");
        progressDialog.setCancelable(false);
        progressDialog.show();

        files = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(Void... unused) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl(
                "gs://wheelchairmap.appspot.com");

        for (final String filePath : Constants.FILES_PATH) {
            downloadSuccess = false;
            downloadFile(storageRef, filePath);
        }
        Constants.setImageFiles(files);
        return (null);
    }

    @Override
    protected void onPostExecute(Void unused) {
        while (!downloadSuccess) {
            SystemClock.sleep(200);
        }
        // Puts the status into the Intent
        String status = "Success"; // any data that you want to send back to receivers
        Intent localIntent = new Intent(Constants.BROADCAST_FILTER)
                .putExtra("Status", status);
        // Broadcasts the Intent to receivers in this app.
        LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
        progressDialog.dismiss();
    }

    public void downloadFile(StorageReference storageRef, final String filePath) {
        // Child references can also take paths
        // spaceRef now points to "users/me/profile.png
        // imagesRef still points to "images"
        StorageReference spaceRef = storageRef.child(filePath);

        File localFile = null;
        try {
            String fileNameWithEnding = filePath.split("/")[filePath.split("/").length - 1];
            String fileName = fileNameWithEnding.substring(0, fileNameWithEnding.length() - 4);
            localFile = File.createTempFile(fileName, "png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        spaceRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                downloadSuccess = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        files.add(localFile);
    }
}
