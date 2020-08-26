package com.cacho.bakingtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.cacho.bakingtime.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnTaskDoneListener {

    private Recipe[] mRecips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getRecipes();
    }


    private void getRecipes(){
        if (checkForInternet()) {
            new RecipesGetter(this).execute("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
        } else {
            Toast.makeText(getApplicationContext(), R.string.http_recipes_error, Toast.LENGTH_LONG).show();
        }
    }




    private Boolean checkForInternet() {
        ConnectivityManager mgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), R.string.nointernet_error, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void onTaskDone(String responseData) {
        Gson gson = new Gson();
        Recipe[] mRecips = gson.fromJson( responseData, Recipe[].class );
    }

    @Override
    public void onError() {

    }


}