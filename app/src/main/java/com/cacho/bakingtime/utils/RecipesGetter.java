package com.cacho.bakingtime.utils;

import android.os.AsyncTask;

import com.cacho.bakingtime.OnTaskDoneListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RecipesGetter extends AsyncTask<String , Void ,String> {

    private String mServer_response;
    private OnTaskDoneListener mOnTaskDoneListener;

    public RecipesGetter(OnTaskDoneListener onTaskDoneListener){
        mOnTaskDoneListener = onTaskDoneListener;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK) {
                mServer_response = readStream(urlConnection.getInputStream());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        return mServer_response;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (mOnTaskDoneListener != null && s != null) {
            mOnTaskDoneListener.onTaskDone(s);
        } else {
            mOnTaskDoneListener.onError();
        }
    }


    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
