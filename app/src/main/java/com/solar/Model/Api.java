package com.solar.Model;

import com.solar.Model.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface Api {

    @POST("register.php")
    @FormUrlEncoded
    Call<Register> register(@Field("name") String username, @Field("email") String email, @Field("phone") String phone, @Field("password") String password);

    @POST("login.php")
    @FormUrlEncoded
    Call<Register> login(@Field("email") String username, @Field("password") String password);



    @POST("get_All_prices.php")
    Call<PricesBIP> get_All_prices();
}