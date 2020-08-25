package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

public class DelegateEmployeeDetailActivity extends AppCompatActivity implements GetRawData.OnDownloadComplete {
    @Override
    public void getRawDataOnDownloadComplete(String data, DownloadStatus status) {

    }
}
