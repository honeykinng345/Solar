package com.solar.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Register {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("error_msg")
    @Expose
    private String error_msg;
    @SerializedName("user")
    @Expose
    private User user;


    /**
     * No args constructor for use in serialization
     */
    public Register() {
    }

    /**
     * @param uid
     * @param error
     * @param user
     */
    public Register(Boolean error, String uid, User user, String error_msg) {
        super();
        this.error = error;
        this.uid = uid;
        this.user = user;
        this.error_msg = error_msg;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}