package com.example.demo_buoi_5.download_file;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.demo_buoi_5.R;

public class DownLoadFileActivity extends AppCompatActivity {
    TextView statusTv;
    private DownloadManager downloadManager;
    private long downloadImageId = -1;
    private BroadcastReceiver onComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            statusTv.setText(getStatusMessage(downloadImageId));
        }
    };
    private BroadcastReceiver onNotificationClick = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TAG", "run download ");
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_download_file);
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        registerReceiver(onNotificationClick, new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        findViewById(R.id.startDownloadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload(Uri.parse("https://commonsware.com/misc/test.mp4"));
            }
        });
        statusTv = findViewById(R.id.downloadStatusText);
        findViewById(R.id.cancelDownloadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downloadManager != null) {
                    downloadManager.remove(downloadImageId);
                }
            }
        });
    }

    private long startDownload(Uri uri) {

        Long downloadReference;

        DownloadManager.Request request = new DownloadManager.Request(uri);

        // Setting title of request
        request.setTitle("Data Download");

        // Setting description of request
        request.setDescription("Android Data download using DownloadManager.");

        // Set the local destination for the downloaded file to a path
        // within the application's external files directory
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "test.mp4");
        // Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);
        return downloadReference;
    }

    private String getStatusMessage(Long downloadId) {

        DownloadManager.Query query = new DownloadManager.Query();
        // set the query filter to our previously Enqueued download
        query.setFilterById(downloadId);

        // Query the download manager about downloads that have been requested.
        Cursor cursor = downloadManager.query(query);
        if (cursor != null && cursor.moveToFirst()) {
            return downloadStatus(cursor);
        }
        return "NO_STATUS_INFO";
    }

    private String downloadStatus(Cursor cursor) {

        // column for download  status
        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
        int status = cursor.getInt(columnIndex);
        // column for reason code if the download failed or paused
        int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
        int reason = cursor.getInt(columnReason);

        String statusText = "";
        String reasonText = "";

        switch (status) {
            case DownloadManager.STATUS_FAILED: {
                statusText = "STATUS_FAILED";
                switch (reason) {
                    case DownloadManager.ERROR_CANNOT_RESUME:
                        reasonText = "ERROR_CANNOT_RESUME";
                        break;
                    case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                        reasonText = "ERROR_DEVICE_NOT_FOUND";
                        break;
                    case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                        reasonText = "ERROR_FILE_ALREADY_EXISTS";
                        break;
                    case DownloadManager.ERROR_FILE_ERROR:
                        reasonText = "ERROR_FILE_ERROR";
                        break;
                    case DownloadManager.ERROR_HTTP_DATA_ERROR:
                        reasonText = "ERROR_HTTP_DATA_ERROR";
                        break;
                    case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                        reasonText = "ERROR_INSUFFICIENT_SPACE";
                        break;
                    case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                        reasonText = "ERROR_TOO_MANY_REDIRECTS";
                        break;
                    case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                        reasonText = "ERROR_UNHANDLED_HTTP_CODE";
                        break;
                    case DownloadManager.ERROR_UNKNOWN:
                        reasonText = "ERROR_UNKNOWN";
                        break;
                    default:
                        break;
                }
            }
            break;
            case DownloadManager.STATUS_PAUSED: {
                statusText = "STATUS_PAUSED";
                switch (reason) {
                    case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                        reasonText = "PAUSED_QUEUED_FOR_WIFI";
                        break;
                    case DownloadManager.PAUSED_UNKNOWN:
                        reasonText = "PAUSED_UNKNOWN";
                        break;
                    case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                        reasonText = "PAUSED_WAITING_FOR_NETWORK";
                        break;
                    case DownloadManager.PAUSED_WAITING_TO_RETRY:
                        reasonText = "PAUSED_WAITING_TO_RETRY";
                        break;
                    default:
                        break;
                }
            }
            break;
            case DownloadManager.STATUS_PENDING:
                statusText = "STATUS_PENDING";
                break;
            case DownloadManager.STATUS_RUNNING:
                statusText = "STATUS_RUNNING";
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                statusText = "STATUS_SUCCESSFUL";
                break;
        }


        return "Download Status: reason" + reason + "  statusText:" + statusText;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onComplete);
        unregisterReceiver(onNotificationClick);
    }

    private void requestStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{(Manifest.permission.WRITE_EXTERNAL_STORAGE)},
                        1
                );
            }
        }
    }

}
