package iss.workshop.jsonparsingexample;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PostJsonData extends AsyncTask<String, Void, String> {

    private DownloadStatus mDownloadStatus;
    private final PostJsonData.OnDownloadComplete mCallback;
    private String mJsonData;

    interface OnDownloadComplete {
        void onDownloadComplete(String data, DownloadStatus status);
    }

    public PostJsonData(PostJsonData.OnDownloadComplete callback) {
        mDownloadStatus = DownloadStatus.IDLE;
        mCallback = callback;
    }

    void runInSameThread(String s) {

        if(mCallback != null) {
            mCallback.onDownloadComplete(doInBackground(s), mDownloadStatus);
        }

    }

    void loadJsonData(String data) {
        mJsonData = data;
    }

    @Override
    protected void onPostExecute(String s) {
        if(mCallback != null) {
            mCallback.onDownloadComplete(s, mDownloadStatus);
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        if(strings == null) {
            mDownloadStatus = DownloadStatus.NOT_INITIALISED;
            return null;
        }

        try {
            mDownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);

            connection = (HttpURLConnection) url.openConnection();

//            connection.setRequestMethod("GET");
//            connection.connect();
//            int response = connection.getResponseCode();
//
//            StringBuilder result = new StringBuilder();
//
//            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
//                result.append(line).append("\n");
//            }
//
//            mDownloadStatus = DownloadStatus.OK;
//            return result.toString();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            byte[] input = mJsonData.getBytes();
            os.write(input, 0, input.length);

//            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
//            StringBuilder response = new StringBuilder();
//            String responseLine = null;
//            while ((responseLine = br.readLine()) != null) {
//                response.append(responseLine.trim());
//            }
//            System.out.println(response.toString());
//
//            mDownloadStatus = DownloadStatus.OK;
//            return response.toString();

            StringBuilder result = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                result.append(line).append("\n");
            }

            String data = result.toString();
            data = data.trim();
            data = data.substring(1, data.length() - 1);
            data = data.replace("\\", "");

            mDownloadStatus = DownloadStatus.OK;
            return data;

        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(SecurityException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            if(reader != null) {
                try {
                    reader.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }

        mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }
}
