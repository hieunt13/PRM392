package com.example.hotgearvn.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hotgearvn.R;
import com.example.hotgearvn.dao.UsersDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Users;
import com.example.hotgearvn.utils.HandleEvent;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etEmail, etRepassword, etPhone, etFullname;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etFullname = (EditText) findViewById(R.id.etFullName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRepassword = (EditText) findViewById(R.id.etRepassword);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = etUsername.getText().toString();
                String Email = etEmail.getText().toString();
                String Fullname = etFullname.getText().toString();
                String Phone = etPhone.getText().toString();
                String Password = etPassword.getText().toString();
                String Repassword = etRepassword.getText().toString();
                Users user = new Users(Username, Password, Email, Fullname, Phone);
                if (validateInput(user)){
                    //Do insert operation
                    UsersDao usersDao;
                } else {
                    Toast.makeText(RegisterActivity.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Boolean validateInput(Users user) {
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getEmail().isEmpty()
                || user.getFullName().isEmpty() ||
                user.getPhone().isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showPopUp(View v) {
        HandleEvent.showPopUp(v, this);
    }

    public void login_logout(View view) {
        HandleEvent.onClickLogin_Logout(view, this);
    }
}