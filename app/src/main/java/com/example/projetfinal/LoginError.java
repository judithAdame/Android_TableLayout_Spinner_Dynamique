package com.example.projetfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginError extends AppCompatActivity {

    TextView messageError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_error);

        messageError = (TextView) findViewById(R.id.messageError);

        Intent monIntent = getIntent();
        String utilisateur = monIntent.getStringExtra("utilisateur");
        String motPass = monIntent.getStringExtra("motPass");

        messageError.setText("The username "+utilisateur+" and Pasaword "+ motPass+" not a valid combination");
    }

    public void loginScreen(View v){
        Intent monIntent = new Intent(this, Login.class);
        startActivity(monIntent);

    }
}
