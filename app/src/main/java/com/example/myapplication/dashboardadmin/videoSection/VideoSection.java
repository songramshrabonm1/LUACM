package com.example.myapplication.dashboardadmin.videoSection;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.DefaultLoadControl;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class VideoSection extends AppCompatActivity {


    private ExoPlayer player;
    private PlayerView playerView;
    ProgressBar progressBar;
    private TextView loadingMessage ;

    @OptIn(markerClass = UnstableApi.class)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_section);


        playerView = findViewById(R.id.player_views);
        progressBar = findViewById(R.id.progress_bar);
        loadingMessage = findViewById(R.id.loading_message);
        // সঠিক গুগল ড্রাইভ ফাইল লিঙ্ক ব্যবহার করুন
        String driveLink = getIntent().getStringExtra("driveLink");

//        String driveLink = "https://drive.google.com/file/d/1vUEbrHEixqllhwwUtHELK_P7NbBP5I8H/view?usp=sharing"; // Replace FILE_ID with the actual ID
        String videoUrl = GoogleDriveLinkExtractor.extractDirectDownloadLink(driveLink);


        if (videoUrl != null) {

            try {

                // Initialize ExoPlayer with custom LoadControl
                @OptIn(markerClass = UnstableApi.class) DefaultLoadControl loadControl = new DefaultLoadControl.Builder()
                        .setBufferDurationsMs(
                                40000,  // Minimum buffer duration in milliseconds
                                80000, // Maximum buffer duration in milliseconds
                                5000,  // Buffer duration before playback starts
                                10000   // Buffer duration after rebuffer
                        )
                        .build();


                // Initialize ExoPlayer
//                player = new ExoPlayer.Builder(this).build();
                player = new ExoPlayer.Builder(this)
                        .setLoadControl(loadControl)
                        .build();
                playerView.setPlayer(player);


                // Build the MediaItem
                Uri uri = Uri.parse(videoUrl);
                MediaItem mediaItem = MediaItem.fromUri(uri);

                player.addListener(new Player.Listener() {
                    @Override
                    public void onPlaybackStateChanged(int playbackState){
                        if (playbackState == Player.STATE_READY) {
                            progressBar.setVisibility(View.GONE); // Hide progress bar when ready
                            loadingMessage.setVisibility(View.GONE);
                        }
                    }
                });

                progressBar.setVisibility(View.VISIBLE);
                loadingMessage.setVisibility(View.VISIBLE);

                // Prepare the player with the media item
                player.setMediaItem(mediaItem);
                player.prepare();
                player.setPlayWhenReady(true); // Start playing when ready



            }catch (Exception e){
                showAlertMessage("Error, Unable to play the video. Please check the link.");
            }

        } else {

            showAlertMessage("Invalid Google Drive link or unable to extract file ID.");
//            Toast.makeText(VideoSection.this, "Invalid Google Drive link or unable to extract file ID." , Toast.LENGTH_SHORT).show();
//            System.out.println("Invalid Google Drive link or unable to extract file ID.");
        }
    }

    private void showAlertMessage(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(VideoSection.this);
        builder.setTitle("Alert")
                .setMessage(s)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                }).setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                }).show();
    }


    public static class GoogleDriveLinkExtractor {
        public static String extractDirectDownloadLink(String viewLink) {
            String field = extractFileId(viewLink);
            if (field != null) {
                return "https://drive.google.com/uc?export=download&id=" + field;
            }
            return null;
        }

        private static String extractFileId(String viewLink) {
            if (viewLink == null || viewLink.isEmpty()) {
                return null;
            }
            Uri uri = Uri.parse(viewLink);
            String path = uri.getPath();
            if (path != null) {
                if (path.startsWith("/file/d/")) {
                    int startIndex = "/file/d/".length();
                    int endIndex = path.indexOf('/', startIndex);
                    if (endIndex != -1) {
                        return path.substring(startIndex, endIndex);
                    }
                    return path.substring(startIndex); // If no trailing slash, return till end
                }
            }
            return null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}