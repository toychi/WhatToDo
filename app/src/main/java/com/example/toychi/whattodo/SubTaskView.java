package com.example.toychi.whattodo;

import android.Manifest;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.toychi.whattodo.persistence.Task;
import com.example.toychi.whattodo.ui.PhotoViewModel;
import com.example.toychi.whattodo.ui.PhotoViewModelFactory;
import com.example.toychi.whattodo.ui.TaskViewModel;
import com.example.toychi.whattodo.ui.TaskViewModelFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SubTaskView extends AppCompatActivity {
    private static final String TAG = SubTaskView.class.getSimpleName();
    static final int REQUEST_IMAGE_CAPTURE = 1;

    // Room Database
    private PhotoViewModelFactory pViewModelFactory;
    private PhotoViewModel pViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    int tid;
    Uri testUri;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_task_view);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);

        Intent myIntent = getIntent();
        tid = myIntent.getIntExtra("tid",1);

        int check = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (check == PackageManager.PERMISSION_GRANTED) {
            //Do nothing
        } else {
            // Request for permission
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1024);
        }

        gridView = findViewById(R.id.gridview);
        // (Photo) Room Database
        pViewModelFactory = Injection.providePhotoViewModelFactory(this);
        pViewModel = ViewModelProviders.of(this, pViewModelFactory).get(PhotoViewModel.class);
        mDisposable.add(pViewModel.getPhotoUris(tid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photos -> {
                    PhotoAdapter tt = new PhotoAdapter(this, photos);
                    gridView.setAdapter(tt);
                }, throwable -> Log.e("Error in Task View", "Unable to load photos", throwable)));

        //Task nname
        TextView TaskNum = findViewById(R.id.TaskNum);
        TaskNum.setText("Task 1");
        //DueDate
        TextView DueDate = findViewById(R.id.DueDate);
        DueDate.setText("Due on:");
        //Progress bar
        RoundCornerProgressBar progressBar = findViewById(R.id.RoundPg);
        progressBar.setMax(7);
        progressBar.setProgress(3);
        //Percent
        TextView TaskPercent = findViewById(R.id.TaskPercent);
        String TaskPc = String.valueOf(Math.round(progressBar.getProgress() / progressBar.getMax() * 100));
        TaskPercent.setText(TaskPc + "%");

        FloatingActionButton CameraBut = findViewById(R.id.CameraBut);
        CameraBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        //In-Progress
        /*ListView In_Progress = findViewById(R.id.InProgressList);

        for(int x=0;x<progressBar.getMax()-progressBar.getProgress();x++){
            TextView t = new TextView(this);
            t.setText("Subtask 1");
        }
        In_Progress.setAdapter(new ProgressAdapter(this,In_Progress));

        //Completed
        ListView Completed = findViewById(R.id.CompletedList);
        */

    }

    //Save photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            mDisposable.add(pViewModel.addPhoto(tid, testUri.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                            },
                            throwable -> Log.e(TAG, "Unable to add photo", throwable)));
        }
            /*
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            );
            mImageView.setLayoutParams(param);
            mImageView.setMaxHeight(300);
            mImageView.setMinimumHeight(300);
            mImageView.setMaxWidth(300);
            mImageView.setMinimumWidth(300);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageView.setAdjustViewBounds(true);
            */
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.toychi.fileprovider",
                        photoFile);
                takePictureIntent.putExtra("output", photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                testUri = photoURI;
                System.out.println(photoURI.toString());
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
