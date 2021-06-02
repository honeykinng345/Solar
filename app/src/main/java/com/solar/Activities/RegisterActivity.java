package com.solar.Activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.solar.Helper;
import com.solar.Model.Api;

import com.solar.Model.Register;
import com.solar.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.nameEt)
    EditText nameEt;
    @BindView(R.id.phoneET)
    EditText phoneET;
    @BindView(R.id.RemailET)
    EditText RemailET;
    @BindView(R.id.Rpassword)
    EditText Rpassword;
    @BindView(R.id.Cpassword)
    EditText Cpassword;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for making full screen
        setContentView(R.layout.activity_register);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.loginBtn)
    public void onViewClicked() {
        ValidateUserData();
        //   startActivity(new Intent(RegisterActivity.this,MainActivity.class));
    }

    private void ValidateUserData() {
        //find values
        final String reg_name = nameEt.getText().toString();
        final String reg_email = RemailET.getText().toString();
        final String reg_phone = phoneET.getText().toString();
        final String reg_password = Rpassword.getText().toString();
        final String reg_cpassword = Cpassword.getText().toString();
//        checking if username is empty
        if (TextUtils.isEmpty(reg_name)) {
            nameEt.setError("Please enter username");
            nameEt.requestFocus();
            // Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
        //checking if email is empty
        if (TextUtils.isEmpty(reg_email)) {
            RemailET.setError("Please enter email");
            RemailET.requestFocus();
            // Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(reg_phone)) {
            phoneET.setError("Please enter phone");
            phoneET.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
        if (TextUtils.isEmpty(reg_password)) {
            Rpassword.setError("Please enter Password");
            Rpassword.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
        if (reg_password.length() <= 6) {
            Rpassword.setError("Password Must Be greater then 6 digits");
            Rpassword.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
        //validating email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(reg_email).matches()) {
            RemailET.setError("Enter a valid email");
            RemailET.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
        //checking if password matches
        if (!reg_password.equals(reg_cpassword)) {
            Rpassword.setError("Password Does not Match");
            Rpassword.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
        //After Validating we register User
        registerUser(reg_name, reg_email, reg_phone, reg_password);
    }

    private void registerUser(String name, String email, String phone, String password) {
        Helper.ShowDialoug(RegisterActivity.this, "Please Wait...");
        final String reg_username = nameEt.getText().toString();
        final String reg_email = RemailET.getText().toString();
        final String reg_password = Rpassword.getText().toString();
        final String reg_phone = phoneET.getText().toString();
        //call retrofit
        //making api call
        Api api = ApiClient.getClient().create(Api.class);
        Call<Register> login = api.register(name, email, phone, password);
        login.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (!response.body().getError()) {
                    Helper.hideDialoug(RegisterActivity.this);
                    //get username
                   // String user = response.body().getUser().getName();
                    Helper.transectionActivityToActivity(RegisterActivity.this, MainActivity.class);
                    finish();
                    //startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                } else {
                    Helper.hideDialoug(RegisterActivity.this);
                    Toast.makeText(RegisterActivity.this, response.body().getError_msg().toString(), Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Helper.hideDialoug(RegisterActivity.this);
                Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}