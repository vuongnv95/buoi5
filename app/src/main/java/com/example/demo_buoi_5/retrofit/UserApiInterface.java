package com.example.demo_buoi_5.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

interface UserApiInterface {
    @GET("api/users")
    Call<String> loadUser();

    @POST("api/users")
    Call<String> addUser(@Body UserRequest userRequest);

    @PUT("api/users/{user_id}")
    Call<String> editUser(@Body UserRequest userRequest, @Path("user_id") String userId);
}
