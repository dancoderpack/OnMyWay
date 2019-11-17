package com.conlage.onmyway;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.conlage.onmyway.api.API;
import com.conlage.onmyway.api.VKPostDeleteResponse;
import com.conlage.onmyway.api.VKPostResponse;
import com.conlage.onmyway.system.Constants;
import com.conlage.onmyway.system.OnMyWayService;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;
import com.vk.api.sdk.auth.VKScope;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int selectedGroupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        if (!isAuthorize()){
            authorize();
        }
        startService();
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click));
        switch (v.getId()){
            case R.id.btn1:
                setTitle(Constants.GROUP_TITLE_1);
                selectedGroupID = Constants.GROUP_ID_1;

                break;

            case R.id.btn2:
                setTitle(Constants.GROUP_TITLE_2);
                selectedGroupID = Constants.GROUP_ID_2;

                break;

            case R.id.btn3:
                setTitle(Constants.GROUP_TITLE_3);
                selectedGroupID = Constants.GROUP_ID_3;

                break;

            case R.id.btn4:
                setTitle(Constants.GROUP_TITLE_4);
                selectedGroupID = Constants.GROUP_ID_4;

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        VKAuthCallback callback = new VKAuthCallback() {
            @Override
            public void onLogin(@NotNull VKAccessToken vkAccessToken) {
                Log.i("VK-AUTH", "Авторизация прошла успешно!");
                Toast.makeText(MainActivity.this, "Авторизация прошла успешнно!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoginFailed(int i) {
                showConnectionErrorAlertDialog();
            }
        };

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //Работа с VK SDK//

    private boolean isAuthorize(){
        return VK.isLoggedIn();
    }

    private void authorize(){
        VK.login(this, new ArrayList<VKScope>(){{ add(VKScope.GROUPS); }});
    }

    private void showConnectionErrorAlertDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Не удалось авторизоваться")
                .setMessage("Произошла ошибка при попытке подключения к ВКонтакте. Попробуйте еще раз!")
                .setPositiveButton("Попробовать еще раз", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        authorize();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();
    }

    private void post(int groupID, String message){
        API.post(groupID, message, new API.OnPostResponseReceived() {
            @Override
            public void onReceive(VKPostResponse vkPostResponse) {

            }
        });
    }

    private void deletePost(int groupID, int postID){
        API.deletePost(groupID, postID, new API.OnDeletePostResponseReceived() {
            @Override
            public void onReceive(VKPostDeleteResponse vkPostDeleteResponse) {

            }
        });
    }


    //Работа с View//

    private void setupViews(){
        CardView btn1 = findViewById(R.id.btn1);
        CardView btn2 = findViewById(R.id.btn2);
        CardView btn3 = findViewById(R.id.btn3);
        CardView btn4 = findViewById(R.id.btn4);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

        setBtnImages();
    }

    private void setBtnImages(){
        ImageView ivBtn1 = findViewById(R.id.iv_btn_1);
        ImageView ivBtn2 = findViewById(R.id.iv_btn_2);
        ImageView ivBtn3 = findViewById(R.id.iv_btn_3);
        ImageView ivBtn4 = findViewById(R.id.iv_btn_4);
        Glide.with(MainActivity.this).load("https://sun9-62.userapi.com/c638525/v638525945/3555a/3Ry8buHurpM.jpg").into(ivBtn1);
        Glide.with(MainActivity.this).load("https://sun9-23.userapi.com/c633220/v633220170/240eb/nfkzuZTtzT8.jpg").into(ivBtn2);
        Glide.with(MainActivity.this).load("https://sun9-3.userapi.com/Es0QgDkg2U5jLlmveIGMPnFTeLndWmCokn-3vQ/w88V4lOEV9c.jpg").into(ivBtn3);
        Glide.with(MainActivity.this).load("https://sun9-24.userapi.com/c850532/v850532211/1b868a/OPmZDkE6BcE.jpg").into(ivBtn4);
    }

    private void setTitle(String title){
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(title);
        tvTitle.setTextSize(21f);
    }

    private void setButtonView(int state){

    }


    //Работа с сервисом//

    private void startService(){
        Intent startIntent = new Intent(MainActivity.this, OnMyWayService.class);
        startIntent.setAction(Constants.ACTION.START_ACTION);
        startService(startIntent);
    }

    private void stopService(){
        Intent stopIntent = new Intent(MainActivity.this, OnMyWayService.class);
        stopIntent.setAction(Constants.ACTION.STOP_ACTION);
        startService(stopIntent);
    }

}
