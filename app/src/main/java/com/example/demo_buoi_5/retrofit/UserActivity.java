package com.example.demo_buoi_5.retrofit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo_buoi_5.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Log.d("UserActivity", "handleMessage() called with: msg = [" + msg + "]");
        }
    };
    private Retrofit retrofit;
    private UserApiInterface userApiInterface;
    private int count = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_user);
        initRetrofit();
        setOnClickViews();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userApiInterface = retrofit.create(UserApiInterface.class);
    }

    private void setOnClickViews() {
        findViewById(R.id.get_list_user_btn).setOnClickListener(this);
        findViewById(R.id.add_user_btn).setOnClickListener(this);
        findViewById(R.id.edit_user_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_list_user_btn:
                requestAllUser();
                break;
            case R.id.add_user_btn:
                count++;
                addUser("User"+count, "Develop");
                break;
            case R.id.edit_user_btn:
                updateUser("User1","Tester","1");
                break;
        }
    }

    private void updateUser(String userName, String job,String userId) {
        if (userApiInterface != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    userApiInterface.editUser(new UserRequest(userName, job), userId).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Message dataToSend = new Message();
                            Bundle bundle = new Bundle();
                            if (response != null) {
                                bundle.putString("data", response.body());
                                dataToSend.setData(bundle);
                                handler.sendMessage(dataToSend);
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }).start();
        }
    }

    private void addUser(String userName, String job) {
        if (userApiInterface != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    userApiInterface.addUser(new UserRequest(userName,job)).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Message dataToSend = new Message();
                            Bundle bundle = new Bundle();
                            if (response != null) {
                                bundle.putString("data", response.body());
                                dataToSend.setData(bundle);
                                handler.sendMessage(dataToSend);
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }).start();
        }
    }

    private void requestAllUser() {
        if (userApiInterface != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    userApiInterface.loadUser().enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Message dataToSend = new Message();
                            Bundle bundle = new Bundle();
                            if (response != null) {
                                bundle.putString("data", response.body());
                                dataToSend.setData(bundle);
                                handler.sendMessage(dataToSend);
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }).start();
        }
    }
}
