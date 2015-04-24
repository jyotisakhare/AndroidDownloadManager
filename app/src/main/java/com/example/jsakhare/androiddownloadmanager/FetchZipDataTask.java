package com.example.jsakhare.androiddownloadmanager;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * Created by jsakhare on 12/04/15.
 */
public class FetchZipDataTask extends AsyncTask<BaseRequest, Integer, BaseResponse> {

    BaseRequest request;
    @Override
    protected void onProgressUpdate(Integer... values) {
        //super.onProgressUpdate(values);
        //mProgress.setProgress(values[0]);
        Log.d("progress", String.valueOf(values[0]));
        request.getHandler().onProgress(values[0]);
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();


    }

    @Override
    protected void onPostExecute(BaseResponse response) {
        super.onPostExecute(response);
        request.getHandler().onComplete(response);
    }



    @Override
    protected BaseResponse doInBackground(BaseRequest... requests) {
        int count;
        request = requests[0];
        BaseResponse resultObj = new BaseResponse(null, null, null, requests[0]);
        String filename = "";
        String result = "";

        if(DiskCacheService.checkData(requests[0].getUrlStr())) {
            result = DiskCacheService.getData(requests[0].getUrlStr());
            resultObj.setResponse("from cache"+result);
            resultObj.setStatus(200);
            return resultObj;
        }
        try {

            URL url = new URL(requests[0].getUrlStr());
            URLConnection conection = url.openConnection();
            conection.connect();

            int lenghtOfFile = conection.getContentLength();
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(),100*1024);
            // Output stream to write file in SD card
            String state = Environment.getExternalStorageState();
            String dirPath="";
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            }else {
                dirPath = Environment.getDataDirectory().getPath();
            }
            dirPath = dirPath + "/AppData";
            File dataDir = new File(dirPath);
            if(!dataDir.isDirectory()) {
                dataDir.mkdirs();
            }
            Log.d("dir path", dirPath);
            String[] parts = requests[0].getUrlStr().split("/");
             filename = parts[parts.length -1];
            Log.d("filename", filename);
            OutputStream output = new FileOutputStream(dirPath +"/"+filename);
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {

                total += count;
                // Publish the progress which triggers onProgressUpdate method
                publishProgress((int) ((total * 100) / lenghtOfFile));

                // Write data to file
                output.write(data, 0, count);

            }
            // Flush output
            output.flush();
            // Close streams
            output.close();
            input.close();
            resultObj.setStatus(200);
            result = " Download complete. \n" + "File Path :- " + dirPath + "/" + filename;
            resultObj.setResponse(result);
            Date expiryDate = new Date();
            long t=expiryDate.getTime();
            expiryDate = new Date(t + (10 * 60000));
            DiskCacheService.saveData(requests[0].getUrlStr(), result, expiryDate);
            return  resultObj;
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            resultObj.setStatus(400);
            resultObj.setResponse(filename + " Download Failed ");
            requests[0].getHandler().onError(resultObj);
        }
        return resultObj;

    }
}


