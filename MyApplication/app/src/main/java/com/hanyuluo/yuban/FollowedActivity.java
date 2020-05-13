package com.hanyuluo.yuban;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FollowedActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private static ArrayList<String> data = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followed);
        Intent getIntent = getIntent();
        String userid = getIntent.getStringExtra("userid");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_followed);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getData(userid);

    }


    private void getData(String userid) {


        //关注数
        String url2="http://39.97.251.92:8000/home/api/user/follow/"+userid;
        //第一步获取okHttpClient对象
        OkHttpClient client2 = new OkHttpClient.Builder() .build();
        //第二步构建Request对象
        Request request2 = new Request.Builder()
                .url(url2)
                .get()
                .build();
        //第三步构建Call对象
        Call call2 = client2.newCall(request2);
        //第四步:异步get请求
        call2.enqueue(new Callback() {
            @Override
            public void onFailure(Call call2, IOException e) {
                Log.i("onFailure", e.getMessage());
            }

            @Override
            public void onResponse(Call call2, Response response) throws IOException {
                //得到的子线程
                String result2 = response.body().string();
                Gson gson=new Gson();
                final List<Loginuser>loginusers2=gson.fromJson(result2,new TypeToken<List<Loginuser>>(){}.getType());

                FollowedActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new LoginuserAdapter(FollowedActivity.this,loginusers2);
                        recyclerView.setAdapter(mAdapter);
                    }
                });


            }
        });

    }
}
