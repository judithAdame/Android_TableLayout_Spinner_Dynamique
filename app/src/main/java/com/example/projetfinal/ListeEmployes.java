package com.example.projetfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
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

public class ListeEmployes extends AppCompatActivity {
        String addIp = "10.150.134.5";
        String serviceWebGetAll = "http://"+addIp+":8080/WebApplication1/webresources/database/employeeList";
        //String id = "100";
        // String serviceWebGetById = "http://"+addIp+"/WebApplication1/webresources/database/singleEmployee&"+id;
        String str = "\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n";
        String message;
        TextView outTextViewTmp;
        TableLayout outTableLayout;
        TableRow tr1;
        TextView theID, theFName, theLName;
        JSONArray jsonarray;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_liste_employes);
            outTextViewTmp = (TextView) findViewById(R.id.textViewTmp);

            outTableLayout = (TableLayout) findViewById(R.id.tableLayoutEmployes);

            tr1 = new TableRow(this);

            theID = new TextView(this);
            theID.setText(" ID ");
            tr1.addView(theID);

            theFName = new TextView(this);
            theFName.setText(" FNAME ");
            tr1.addView(theFName);

            theLName = new TextView(this);
            theLName.setText(" LNAME ");
            tr1.addView(theLName);

            outTableLayout.addView(tr1);

            new ListeEmployes.MyTask().execute();

        }
        public void onClickDetail(View vue) {
            Intent monIntent = new Intent(this, DetailEmploye.class);
            startActivity(monIntent);
        }
        private class MyTask extends AsyncTask<Void, Void, Void> {
            ListeEmployes l ;

            String id, fname, lname;

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL(serviceWebGetAll);
                    //URL url = new URL(serviceWebGetById);
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

                    jsonarray  = new JSONArray(response.toString());

                    JSONObject jsonobject;

                    for (int i = 0; i < jsonarray.length(); i++) {
                        jsonobject = jsonarray.getJSONObject(i);
                        id = jsonobject.getString("id");
                        fname = jsonobject.getString("fname");
                        lname = jsonobject.getString("lname");
                        message += "id : " + id+"fname : " + fname+"lname : " + lname + "\n";
                        System.out.println(str+message+str);
                    }

                } catch (ConnectException e) {
                    e.printStackTrace();
                    System.out.println(str+e.getMessage()+str);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    System.out.println(str+e.getMessage()+str);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result){
                outTextViewTmp.setText(message);
                JSONObject jsonobject1 = new JSONObject();
              /*  for(int i=0; i<jsonarray.length(); i++) {
                    try {
                        jsonobject1 = jsonarray.getJSONObject(i);
                        tr1 = new TableRow(this);
                        theID = new TextView(this.getClass().);
                        theID.setText(jsonobject1.getString("id"));
                        tr1.addView(theID);
                        theFName = new TextView(this);
                        theFName.setText(jsonobject1.getString("fname"));
                        tr1.addView(theFName);
                        theLName = new TextView(this);
                        theLName.setText(jsonobject1.getString("lname"));
                        tr1.addView(theLName);
                        outTableLayout.addView(tr1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }*/
                super.onPostExecute(result);
            }
        }
    }