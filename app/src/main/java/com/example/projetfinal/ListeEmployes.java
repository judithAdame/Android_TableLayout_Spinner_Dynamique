package com.example.projetfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListeEmployes extends AppCompatActivity {
        String addIp = "192.168.123.11";
        String serviceWebGetAll = "http://"+addIp+":8080/WebApplication1/webresources/database/employeeList";
        //http://localhost:8080/WebApplication1/webresources/database/employeeList
        String str = "\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n";
        TableLayout outTableLayout;
        TableRow tr1;
        TextView theID;
        TextView  theFName;
        TextView  theLName;
        JSONArray jsonarray;
        Spinner outSpinnerId;
        List<String> spinnerArray = new ArrayList<String>();
        String id;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_liste_employes);

            outTableLayout = findViewById(R.id.tableLayoutEmployes);

            outSpinnerId = (Spinner) findViewById(R.id.spinnerId);

            outSpinnerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    id=   outSpinnerId.getItemAtPosition(outSpinnerId.getSelectedItemPosition()).toString();
                    //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // DO Nothing here
                }
            });

            /*tr1 = new TableRow(this);
            theID = new TextView(this);
            theID.setText(" ID ");
            tr1.addView(theID);

            theFName = new TextView(this);
            theFName.setText(" FNAME ");
            tr1.addView(theFName);

            theLName = new TextView(this);
            theLName.setText(" LNAME ");
            tr1.addView(theLName);

            outTableLayout.addView(tr1);*/

            new ListeEmployes.MyTask().execute();

        }

        private void setTable(){
                JSONObject jsonobject;
                String id, fname, lname;
                try {
                    for (int i = 0; i < jsonarray.length(); i++) {
                        jsonobject = jsonarray.getJSONObject(i);
                        tr1 = new TableRow(this);
                        theID = new TextView(this);
                        theFName = new TextView(this);
                        theLName = new TextView(this);

                        tr1.setLayoutParams(new TableRow.LayoutParams(
                                TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));

                        id = jsonobject.getString("id");
                        theID.setText(id);
                        tr1.addView(theID);
                        spinnerArray.add(id);

                        fname = jsonobject.getString("fname");
                        theFName.setText(fname);
                        tr1.addView(theFName);

                        lname = jsonobject.getString("lname");
                        theLName.setText(lname);
                        tr1.addView(theLName);

                        outTableLayout.addView(tr1);
                    }
                    // Create an ArrayAdapter using the string array and a default spinner layout
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            this, android.R.layout.simple_spinner_item, spinnerArray);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    outSpinnerId.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
        public void onClickDetail(View DetailEmploye) {
            Intent monIntent = new Intent(this, DetailEmploye.class);
            monIntent.putExtra("id", id);
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
                    //System.out.println(str+"jsonarray: " + jsonarray.toString()+str);


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
                setTable();
            }
        }

    }