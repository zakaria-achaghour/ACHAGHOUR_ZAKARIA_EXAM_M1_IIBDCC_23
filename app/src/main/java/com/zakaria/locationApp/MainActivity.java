package com.zakaria.locationApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zakaria.R;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    private static final Map<String, String> users = new HashMap<>();
    EditText usernameEditText, passwordEditText;
    Button login;
    SharedPreferences sharedPreferences;

    void appUsers(){
        users.put("zakaria", "123456");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appUsers();
        usernameEditText =findViewById(R.id.usrname);
        passwordEditText =findViewById(R.id.pswd);
        login=findViewById(R.id.btnLogin);
        login.setOnClickListener(view -> {
            sharedPreferences= getSharedPreferences(getString(R.string.loginpref),MODE_PRIVATE);
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            AtomicBoolean connected = new AtomicBoolean(false);

            for (String user : users.keySet()) {
                if(user.equals(username)){
                    if(users.get(user).equals(password)){
                        Toast.makeText(this, R.string.welcome, Toast.LENGTH_LONG).show();
                        connected.set(true);
                    }else{
                        Toast.makeText(this, R.string.wrong_password, Toast.LENGTH_LONG).show();
                        connected.set(false);
                    }
                    break;
                }
            }

            if(connected.get()){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.username), username);
                editor.putString(getString(R.string.password), password);
                editor.commit();
                usernameEditText.setText("");
                passwordEditText.setText("");
                Intent intent=new Intent(MainActivity.this,IpFinderActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, R.string.user_not_found, Toast.LENGTH_LONG).show();
            }
        });
    }
}