package com.example.projetfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailEmploye extends AppCompatActivity {
    String addIp = "192.168.123.11";
    //http://localhost:8080/WebApplication1/webresources/database/singleEmployee&100
    String serviceWebGetById = "http://"+addIp+":8080/WebApplication1/webresources/database/singleEmployee&";
    String str = "\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n";
    String id;
    JSONObject jsonobject;
    TextView outTextViewFLname, outTextViewSalary, outTextViewEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_employe);
        outTextViewFLname = (TextView) findViewById(R.id.textViewFLname);
        outTextViewSalary = (TextView) findViewById(R.id.textViewSalary);
        outTextViewEmail = (TextView) findViewById(R.id.textViewEmail);
        Intent monIntent = getIntent();
        id = ((Intent) monIntent).getStringExtra("id");
        new DetailEmploye.MyTask().execute();
    }
    private class MyTask extends AsyncTask<Void , Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(serviceWebGetById+id);
                System.out.println(str+"Sending 'GET' request to URL: " + url+str);

                HttpURLConnection client;
                client = (HttpURLConnection) url.openConnection();
                client.setRequestMethod("GET");
                int responseCode = client.getResponseCode();
                System.out.println(str+"Response code: " + responseCode+str);

                InputStreamReader myInput = new InputStreamReader(client.getInputStream());
                BufferedReader in = new BufferedReader(myInput);
                StringBuffer response = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                jsonobject  = new JSONObject(response.toString());

                System.out.println(str+"JSONObject: " + jsonobject.toString()+str);
            } catch (ConnectException e) {
                e.printStackTrace();
                System.out.println(str+e.getMessage()+str);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(str+e.getMessage()+str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            try {
                outTextViewFLname.setText(jsonobject.getString("fname") + " " + jsonobject.getString("lname"));
                outTextViewSalary.setText(Double.toString(jsonobject.getDouble("salary")));
                outTextViewEmail.setText(jsonobject.getString("email"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }
    }
}
