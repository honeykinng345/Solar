package com.solar.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.solar.Helper;
import com.solar.Model.Api;
import com.solar.Model.Register;
import com.solar.R;
import com.solar.SQLiteHandler;
import com.solar.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.emailET)
    EditText emailET;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    SessionManager session;
 SQLiteHandler sqLiteHandler ;
    ProgressDialog progressDialog;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for making full screen
      /*  getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_main);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        progressDialog = new ProgressDialog(MainActivity.this);
        // Session manager
        session = new SessionManager(getApplicationContext());
        sqLiteHandler = new SQLiteHandler(MainActivity.this);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.loginBtn, R.id.noAccountTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                validateUserData();
                break;
            case R.id.noAccountTv:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;
        }
    }

    private void validateUserData() {
        //first getting the values
        final String username = emailET.getText().toString();
        final String reqpasword = password.getText().toString();
        //checking if username is empty
        if (TextUtils.isEmpty(username)) {
            emailET.setError("Please enter your username");
            emailET.requestFocus();
            // Vibrate for 100 milliseconds
            v.vibrate(100);
            loginBtn.setEnabled(true);
            return;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(reqpasword)) {
            password.setError("Please enter your password");
            password.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            loginBtn.setEnabled(true);
            return;
        }
        //Login User if everything is fine
        loginUser(username, reqpasword);


    }

    private void loginUser(String username, String reqpasword) {
        Helper.ShowDialoug(MainActivity.this, "Please wait..");
        Api api = ApiClient.getClient().create(Api.class);
        Call<Register> login = api.login(username, reqpasword);
        login.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (!response.body().getError()) {

                    //get username
                   saveUserData(response.body().getUser().getName(),response.body().getUser().getEmail(),response.body().getUser().getPhone());
                    //storing the user in shared preferences
                  //  Toast.makeText(MainActivity.this, user, Toast.LENGTH_LONG).show();
//                    Toast.makeText(MainActivity.this,response.body().getUsername(),Toast.LENGTH_LONG).show();
                } else {
                    Helper.hideDialoug(MainActivity.this);
                    Toast.makeText(MainActivity.this, response.body().getError_msg().toString(), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Helper.hideDialoug(MainActivity.this);
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void saveUserData(String name, String email, String phone) {
Helper.ShowDialoug(MainActivity.this,"Saving Data...");
       boolean test = sqLiteHandler.insertData(name,email,phone);

      if (test){
          Helper.SHowToast(MainActivity.this,"Done");
          session.setLogin(true);
          Helper.hideDialoug(MainActivity.this);
          Helper.transectionActivityToActivity(MainActivity.this,DashBoardActivity.class);
          finish();
      }else{
          Helper.SHowToast(MainActivity.this,"false");
      }



    }
}