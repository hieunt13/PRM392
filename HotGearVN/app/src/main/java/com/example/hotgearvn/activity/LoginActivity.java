package com.example.hotgearvn.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotgearvn.MainActivity;
import com.example.hotgearvn.R;
import com.example.hotgearvn.dao.UsersDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Users;
import com.example.hotgearvn.utils.HandleEvent;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    private final String REQUIRE = "Require";
    Button btnLogin;
    Button btnLoginHeader;
    CheckBox checkBox;
    TextView tvSignUp;

    public static final String MYPREFERENCES = "MyPrefs";
    public static final String USERNAME = "UsernameKey";
    public static final String PASSWORD = "PasswordKey";
    public static final String PHONE = "PhoneKey";
    public static final String FULLNAME = "FullnameKey";
    public static final String USERID = "UseridKey";
    public static final String EMAIL = "EmailKey";

    public static final String STATUS = "StatusKey";
    public static final String  SAVEINFO = "SaveinfoKey";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsernameSI);
        etPassword = findViewById(R.id.etPasswordSI);
        btnLogin = findViewById(R.id.btnSignIn);
        checkBox = findViewById(R.id.cbLuu);
        btnLoginHeader = findViewById(R.id.btnLogIn_LogOut);
        sharedpreferences = getSharedPreferences(MYPREFERENCES, MODE_PRIVATE);

        tvSignUp = findViewById(R.id.tvSignUp);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkInput()) {
                    return;
                } else {
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();
                    boolean Saveinfo = checkBox.isChecked();
                    String save ="";
                    if (Saveinfo == true){
                        save = "save";
                    } else {
                        save = "not save";
                    }
                    HotGearDatabase database = HotGearDatabase.getDatabase(getApplicationContext());
                    UsersDao usersDao = database.usersDao();
                    Log.d("user", usersDao.getAll().toString());
                    String finalSave = save;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Users users = usersDao.login(username, password);
                            if (users == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(USERID, users.getUserId().toString());
                                editor.putString(USERNAME, users.getUsername());
                                editor.putString(EMAIL, users.getEmail());
                                editor.putString(FULLNAME, users.getFullName());
                                editor.putString(PHONE, users.getPhone());
                                editor.putString(PASSWORD, users.getPassword());
                                editor.putString(STATUS, "login");
                                editor.putString(SAVEINFO, finalSave);
                                editor.commit();
                                btnLoginHeader.setText("Logout");
                                Intent in = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(in);
                            }
                        }
                    }).start();
                }
            }
        });

        tvSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private boolean checkInput() {
        //UserName
        if (TextUtils.isEmpty(etUsername.getText().toString())) {
            etUsername.setError(REQUIRE);
            return false;
        }
        //Password
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError(REQUIRE);
            return false;
        }
        //valid
        return true;
    }

    public void showPopUp(View v) {
        HandleEvent.showPopUp(v, this);
    }

    public void login_logout(View view) {
        HandleEvent.onClickLogin_Logout(view, this);
    }
}