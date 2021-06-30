package com.example.demo_buoi_5.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo_buoi_5.R;


public class MainActivity extends AppCompatActivity {

    private TextView countTv;
    //init handle
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            countTv.setText(countTv.getText() + "Item " + msg.getData().getString("x") + "\n");
        }
    };
    Thread background = new Thread(new Runnable() {
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    b.putString("x", "My Value: " + i);
                    msg.setData(b);
                    handler.sendMessage(msg);
                } catch (Exception e) {
                }
            }
        }
    });

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setContentView(R.layout.activity_main);
        //init views
        countTv = findViewById(R.id.count_tv);

//        createThread();
    }

    @Override
    protected void onStart() {
        super.onStart();
        background.start();
    }

    private void createThread() {
        // viết thread theo cách thứ nhất
        class MyThread extends Thread {
            public void run() {
// phần thực thi của thread viết ở đây
            }
        }
// viết thread theo cách thứ hai
        class MyRunnable implements Runnable {
            public void run() {
// phần thực thi của thread viết ở đây
            }
        }
// tạo và chạy các thread
        MyThread t1 = new MyThread();
        t1.start();
        MyRunnable t2 = new MyRunnable();
        new Thread(t2).start();
    }
}
