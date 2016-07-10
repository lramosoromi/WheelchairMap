package com.rolithunderbird.wheelchairmap.database;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
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
    private String[] filesPath;
    private String locationForCoordinates;
    private ArrayList<String> coordinates;
    private boolean downloadFileSuccess;
    private boolean downloadCoordinateSuccess;


    public StorageTask(Context context, String[] filesToDownload, String location) {
        this.context = context;
        this.filesPath = filesToDownload;
        this.locationForCoordinates = location;
        this.coordinates = new ArrayList<>();
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
        // Use Firebase to populate the coordinates list.
        Firebase.setAndroidContext(context);

/*        //Get the location that will be used to access its database coordinates
        String locationPath = locationForCoordinates.replace(" ", "_").toLowerCase();
        Firebase firebaseRef = new Firebase(Constants.databaseReferencePath + "/").child(locationPath);
        for (String databaseKey : Constants.KEYS_PATH) {
            downloadCoordinateSuccess = false;
            downloadData(firebaseRef, databaseKey);
        }
*/
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl(Constants.storageReferencePath);

        for (final String filePath : this.filesPath) {
            downloadFileSuccess = false;
            downloadFile(storageRef, filePath);
        }
        return (null);
    }

    @Override
    protected void onPostExecute(Void unused) {
        while (!downloadFileSuccess) {
            SystemClock.sleep(200);
        }
        //Constants.setCoordinates(coordinates);
        Constants.setImageFiles(files);

        // Puts the status into the Intent
        String status = "Success"; // any data that you want to send back to receivers
        Intent localIntent = new Intent(Constants.BROADCAST_FILTER)
                .putExtra("Status", status);
        // Broadcasts the Intent to receivers in this app.
        LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
        progressDialog.dismiss();
    }

    private void downloadFile(StorageReference storageRef, final String filePath) {
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
                downloadFileSuccess = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        files.add(localFile);
    }

    private void downloadData(Firebase firebase, String key) {
        firebase.child(key).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        coordinates.add((String)dataSnapshot.getValue());
                        downloadCoordinateSuccess = true;
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        String s = "";
                    }
                });
        while (!downloadCoordinateSuccess) {
            SystemClock.sleep(200);
        }
    }
}
