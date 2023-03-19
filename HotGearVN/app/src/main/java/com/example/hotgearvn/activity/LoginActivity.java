package com.example.hotgearvn.activity;

import static com.example.hotgearvn.constants.MyPreferenceKey.EMAIL;
import static com.example.hotgearvn.constants.MyPreferenceKey.FULLNAME;
import static com.example.hotgearvn.constants.MyPreferenceKey.MYPREFERENCES;
import static com.example.hotgearvn.constants.MyPreferenceKey.PASSWORD;
import static com.example.hotgearvn.constants.MyPreferenceKey.PHONE;
import static com.example.hotgearvn.constants.MyPreferenceKey.SAVEINFO;
import static com.example.hotgearvn.constants.MyPreferenceKey.STATUS;
import static com.example.hotgearvn.constants.MyPreferenceKey.ADDRESS;
import static com.example.hotgearvn.constants.MyPreferenceKey.USERID;
import static com.example.hotgearvn.constants.MyPreferenceKey.USERNAME;

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
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.hotgearvn.MainActivity;
import com.example.hotgearvn.R;
import com.example.hotgearvn.dao.UsersDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Users;
import com.example.hotgearvn.utils.HandleEvent;
import com.google.android.material.navigation.NavigationView;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;

    //Navigation
    DrawerLayout drawerLayout;
    NavigationView navView;

    CheckBox checkBox;
    TextView tvSignUp;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        drawerLayout = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.nav_view);
        //Handle button login logout header
        Button btnLoginHeader;
        btnLoginHeader = findViewById(R.id.btnLogIn_LogOut);
        HandleEvent.buttonLoginLogoutEvent(btnLoginHeader, this);

        etUsername = findViewById(R.id.etUsernameSI);
        etPassword = findViewById(R.id.etPasswordSI);
        btnLogin = findViewById(R.id.btnSignIn);
        checkBox = findViewById(R.id.cbLuu);
        sharedpreferences = getSharedPreferences(MYPREFERENCES, MODE_PRIVATE);
        tvSignUp = findViewById(R.id.tvSignUp);
        String preUsername = sharedpreferences.getString("UsernameKey", "");

        String prePassword = sharedpreferences.getString("PasswordKey", "");
        Log.d("pre", prePassword + "," + preUsername);
        if (!preUsername.equals("")) {
            etUsername.setText(preUsername);
            etPassword.setText(prePassword);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkInput()) {
                    return;
                } else {

                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();

                    boolean Saveinfo = checkBox.isChecked();
                    String save = "";
                    if (Saveinfo == true) {
                        save = "save";
                    } else {
                        save = "not save";
                    }
                    HotGearDatabase database = HotGearDatabase.getDatabase(getApplicationContext());
                    UsersDao usersDao = database.usersDao();
                    Log.d("user", usersDao.getAll().toString());
                    String finalSave = save;
                    String finalUsername = username;
                    String finalPassword = password;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Users users = usersDao.login(finalUsername, finalPassword);
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
                                editor.putString(ADDRESS, users.getAddress());
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
        String REQUIRE = "Require";
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
        HandleEvent.showNavigation(this,navView,drawerLayout);
    }

    public void login_logout(View view) {
        HandleEvent.onClickLogin_Logout(view, this);
    }
}