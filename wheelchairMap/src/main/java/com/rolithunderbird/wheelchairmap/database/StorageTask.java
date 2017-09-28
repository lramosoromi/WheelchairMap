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
import com.rolithunderbird.wheelchairmap.R;
import com.rolithunderbird.wheelchairmap.utils.Constants;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class called when needed to download content from the web
 * Created by rolithunderbird on 28.06.16.
 */
public class StorageTask extends AsyncTask<Void, String, Void> {

    //App context
    private Context context;
    //Dialog that show download in progress
    private ProgressDialog progressDialog;
    //All files downloaded
    private ArrayList<File> files;
    //Files path to download
    private String[] filesPath;
    //Path where to find the location coordinates
    private String locationForCoordinates;
    //List of coordinates downloaded
    private ArrayList<String> coordinates;
    //All files where downloaded
    private boolean downloadFileSuccess;
    //All coordinates where downloaded
    private boolean downloadCoordinateSuccess;


    /**
     * Constructor
     * @param context context of the app
     * @param filesToDownload array of the files to download
     * @param location path of the place where to download locations
     */
    public StorageTask(Context context, String[] filesToDownload, String location) {
        this.context = context;
        this.filesPath = filesToDownload;
        this.locationForCoordinates = location;
        this.coordinates = new ArrayList<>();
    }

    /**
     * Method called before doing anything
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Show the process dialog of downloading context
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.download_task_message));
        progressDialog.setCancelable(false);
        progressDialog.show();

        files = new ArrayList<>();
    }

    /**
     * Method that will download content in background
     * @param unused
     * @return
     */
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
        //Get an instance of the Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl(Constants.storageReferencePath);

        //Iterate through all the files that need to be download
        for (final String filePath : this.filesPath) {
            downloadFileSuccess = false;
            downloadFile(storageRef, filePath);
        }
        return (null);
    }

    /**
     * Method called when previous task finished
     * @param unused
     */
    @Override
    protected void onPostExecute(Void unused) {
        //Before going on, check that all the file where downloaded
        while (!downloadFileSuccess) {
            SystemClock.sleep(200);
        }
        //Set downloaded content to Constant value
        //Constants.setCoordinates(coordinates);
        Constants.setImageFiles(files);

        // Puts the status into the Intent
        String status = "Success"; // any data that you want to send back to receivers
        String location = locationForCoordinates;
        Intent localIntent = new Intent(Constants.BROADCAST_FILTER)
                .putExtra("Status", status)
                .putExtra("Location", location);
        // Broadcasts the Intent to receivers in this app.
        LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
        //Close progress dialog
        progressDialog.dismiss();
    }

    /**
     * Method called to download a specific file from the storage reference
     * @param storageRef place where to download from
     * @param filePath path of file to download
     */
    private void downloadFile(StorageReference storageRef, final String filePath) {
        // Get reference of file to download
        StorageReference spaceRef = storageRef.child(filePath);

        File localFile = null;
        try {
            //Create a new temp file with the same name as the name in its path
            //Eliminate parent path of file (e.g. parent/child.png)
            String fileNameWithEnding = filePath.split("/")[filePath.split("/").length - 1];
            //Eliminate extension of file (e.g. .png)
            String fileName = fileNameWithEnding.substring(0, fileNameWithEnding.length() - 4);
            localFile = File.createTempFile(fileName, "png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //download file
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

    /**
     * Method called to download a specific coordinate from the database
     * @param firebase firebase database
     * @param key key used to download value
     */
    private void downloadData(Firebase firebase, String key) {
        //Download value of key
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
        //Don't go on until the coordinate has been downloaded
        while (!downloadCoordinateSuccess) {
            SystemClock.sleep(10);
        }
    }
}
