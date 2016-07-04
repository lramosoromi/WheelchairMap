package com.rolithunderbird.wheelchairmap.server;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

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
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by rolithunderbird on 28.06.16.
 */
public class StorageService extends IntentService {

    private Context context;

    public StorageService() {
        super("StorageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Gets data from the incoming Intent
        String dataString = intent.getDataString();

        // Do work here, based on the contents of dataString
        // E.g. get data from a server in your case
        List<File> files = new ArrayList<>();

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl(
                "gs://wheelchairmap.appspot.com");

        for (final String filePath : Constants.filesPath) {
            downloadFile(storageRef, filePath, files);
            SystemClock.sleep(5000);
        }
        Constants.setmImages(files);
    }

    public void downloadFile(StorageReference storageRef, final String filePath, List<File> files) {
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
        context = this;
        spaceRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                // Puts the status into the Intent
                String status = "Success"; // any data that you want to send back to receivers
                Intent localIntent = new Intent("com.rolithunderbird.wheelchairmap.BROADCAST")
                        .putExtra("Status", status);
                // Broadcasts the Intent to receivers in this app.
                LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                // Puts the status into the Intent
                String status = "Failure"; // any data that you want to send back to receivers
                Intent localIntent = new Intent("com.rolithunderbird.wheelchairmap.BROADCAST")
                        .putExtra("Status", status + " " + filePath);
                // Broadcasts the Intent to receivers in this app.
                LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
            }
        });
        files.add(localFile);
    }

    public void addItem() {

        // Add items via the Button and EditText at the bottom of the window.
/*
        final EditText text = (EditText) findViewById(R.id.todoText);
        final Button button = (Button) findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Firebase("https://[YOUR-FIREBASE-APP].firebaseio.com/todoItems")
                        .push()
                        .child("text")
                        .setValue(text.getText().toString());
            }
        });
*/
    }

    public void removeItem() {

        // Delete items when clicked
/*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                new Firebase("https://[YOUR-FIREBASE-APP].firebaseio.com/todoItems")
                        .orderByChild("text")
                        .equalTo((String) listView.getItemAtPosition(position))
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChildren()) {
                                    DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                    firstChild.getRef().removeValue();
                                }
                            }
                            public void onCancelled(FirebaseError firebaseError) { }
                        });
            }
        });
*/
    }
}
