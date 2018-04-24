package com.example.toychi.whattodo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.toychi.whattodo.persistence.Task;

import java.util.ArrayList;
import java.util.List;

public class SubTaskView extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_task_view);

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

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        //delete this below 3 lines out after apple database
        List<String> list = new ArrayList<>();
        list.add("ssss");
        list.add("aaaa");
        list.add("oooo");
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new TaskImageAdapter(this, list));

    }

    //Save photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView mImageView = new ImageView(this);
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

            mImageView.setImageBitmap(imageBitmap);
        }
    }

}
