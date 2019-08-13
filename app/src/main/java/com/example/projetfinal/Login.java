package com.example.projetfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private static final String utilisateur = "abcd2019";
    private static final String motPass = "Abcd1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public static boolean isAlphanumerqiue(String chaine){
        Pattern p = Pattern.compile("[^a-zA-Z0-9]");
        boolean hasSpecialChar = p.matcher(chaine).find();
        if(hasSpecialChar){
            return  false;
        }
        return true;
    }
    public static boolean isCorrectPassword(String chaine){
        Pattern p1 = Pattern.compile("[^a-zA-Z0-9!@#$%&*()@:;,.'?/^_+=|<>?{}\\\\[\\\\]~-]");
        //Pattern p2 = Pattern.compile("[-+]");
        boolean hasSpecialChar1 = p1.matcher(chaine).find();
        //boolean hasSpecialChar2 = p2.matcher(chaine).find();

        if(hasSpecialChar1){
            return  false;
        }
        return true;
    }

    public static boolean isEmpty(String chaine){
        if(chaine.equals("")) return true;
        return false;
    }

    public void goLogin(View vue) {
        Intent monIntent = new Intent(this, LoginError.class);
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        String user = username.getText().toString();
        String pass = password.getText().toString();

        if (isEmpty(user)) {
           username.setError("Champs Obligatoire");
           return;
        }
        if (isEmpty(pass)) {
            password.setError("Champs Obligatoire");
            return;
        }
        if(!isAlphanumerqiue(user)){
            username.setError("Alphanumeriqur Demande!");
            return;
        }
        if(pass.length() < 8){
            password.setError("Taille minmale de 8 caractères demande");
            return;
        }
        if(!isCorrectPassword(pass)){
            password.setError("Acepte juste ces caractères spéciaux !@#$%&*()_+=|<>?{}\\\\[\\\\]~-]");
            return;
        }
        if(!(utilisateur.equals(user) && motPass.equals(pass))){
            monIntent.putExtra("utilisateur", user);
            monIntent.putExtra("motPass", pass);
            startActivity(monIntent);
        }else{
            Intent monIntent2 = new Intent(this, ListeEmployes.class);
            startActivity(monIntent2);
        }
    }
}
