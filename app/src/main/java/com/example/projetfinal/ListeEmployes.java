package com.example.projetfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
        String addIp = "192.168.123.11";
        String serviceWebGetAll = "http://"+addIp+":8080/WebApplication1/webresources/database/employeeList";
        // String serviceWebGetById = "http://"+addIp+"/WebApplication1/webresources/database/singleEmployee&100";
        String str = "\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n";
        TableLayout outTableLayout;
        TableRow tr1;
        TextView theID;
        TextView  theFName;
        TextView  theLName;
        JSONArray jsonarray;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_liste_employes);

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
        private class MyTask extends AsyncTask<Void , Void, Void> {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL(serviceWebGetAll);
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
                super.onPostExecute(result);
            }
        }
        private void setTable(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonobject;
                    String id, fname, lname;
                    try {
                        for (int i = 0; i < jsonarray.length(); i++) {
                            jsonobject = jsonarray.getJSONObject(i);

                            id = jsonobject.getString("id");
                            theID.setText(id);
                            tr1.addView(theID);

                            fname = jsonobject.getString("fname");
                            theFName.setText(fname);
                            tr1.addView(theFName);

                            lname = jsonobject.getString("lname");
                            theLName.setText(lname);
                            tr1.addView(theLName);

                            outTableLayout.addView(tr1);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }