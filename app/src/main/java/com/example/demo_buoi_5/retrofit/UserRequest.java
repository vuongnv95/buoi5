package com.example.demo_buoi_5.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRequest {
    @SerializedName("user")
    @Expose
    String userName;
    @SerializedName("job")
    @Expose
    String job;

    public UserRequest(String userName, String job) {
        this.userName = userName;
        this.job = job;
    }


}
