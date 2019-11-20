package com.conlage.onmyway;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
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
import com.conlage.onmyway.system.Config;
import com.conlage.onmyway.system.Constants;
import com.conlage.onmyway.system.OnMyWay;
import com.conlage.onmyway.system.OnMyWayService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;
import com.vk.api.sdk.auth.VKScope;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {

    private int selectedGroupID;
    private int currentFragment; //-1 0 1 2 3

    private FloatingActionButton fabSend;
    private EditText etMessage1, etMessage2;
    private TextView txtMessage1, txtMessage2;
    private SeekBar seekBar;
    private TextView seekBarTint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        if (!isAuthorize()){
            authorize();
        }
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click));
        showRightViews();
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

            case R.id.btn5:
                setTitle("Настройки");
                seekBar.setVisibility(View.VISIBLE);
                seekBarTint.setVisibility(View.VISIBLE);
                hideRightView();
                break;

            case R.id.fab_send:
                fabClick();

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
        CardView btn5 = findViewById(R.id.btn5);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);

        fabSend = findViewById(R.id.fab_send);
        fabSend.setOnClickListener(this);

        etMessage1 = findViewById(R.id.et_msg_1);
        etMessage2 = findViewById(R.id.et_msg_2);

        txtMessage1 = findViewById(R.id.txt_msg_1);
        txtMessage2 = findViewById(R.id.txt_msg_2);

        etMessage1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                fabSend.setEnabled(!(TextUtils.isEmpty(charSequence) &&
                        TextUtils.isEmpty(etMessage2.getText())));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etMessage2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence) && TextUtils.isEmpty(etMessage1.getText())){
                    fabSend.setEnabled(false);
                }else{
                    fabSend.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        seekBar = findViewById(R.id.seek_bar_delay);
        seekBar.setOnSeekBarChangeListener(this);

        seekBarTint = findViewById(R.id.slider_tint);

        setBtnImages();
        hideRightView();
    }

    private void hideRightView(){
        fabSend.hide();
        etMessage1.setVisibility(View.GONE);
        etMessage2.setVisibility(View.GONE);
        txtMessage1.setVisibility(View.GONE);
        txtMessage2.setVisibility(View.GONE);
    }

    private void showRightViews(){
        fabSend.show();
        etMessage1.setVisibility(View.VISIBLE);
        etMessage2.setVisibility(View.VISIBLE);
        txtMessage1.setVisibility(View.VISIBLE);
        txtMessage2.setVisibility(View.VISIBLE);
    }

    private void setBtnImages(){
        ImageView ivBtn1 = findViewById(R.id.iv_btn_1);
        ImageView ivBtn2 = findViewById(R.id.iv_btn_2);
        ImageView ivBtn3 = findViewById(R.id.iv_btn_3);
        ImageView ivBtn4 = findViewById(R.id.iv_btn_4);
        Glide.with(MainActivity.this).load(Constants.GROUP_IMAGE_1).into(ivBtn1);
        Glide.with(MainActivity.this).load(Constants.GROUP_IMAGE_2).into(ivBtn2);
        Glide.with(MainActivity.this).load(Constants.GROUP_IMAGE_3).into(ivBtn3);
        Glide.with(MainActivity.this).load(Constants.GROUP_IMAGE_4).into(ivBtn4);
    }

    private void setTitle(String title){
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(title);
        tvTitle.setTextSize(21f);
    }

    //0 - off
    //1 - on
    //2 -
    private void setButtonView(int state){

    }

    private void fabClick(){
        String message1 = etMessage1.getText().toString();
        String message2 = etMessage2.getText().toString();
//        int delay = delaySlider.get;
        switch (currentFragment){
            case 0:
//                OnMyWay.getInstance().group1 = new Config.Group(true, message1, message2, delay);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;

        }
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

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        String text = "Сообщения отправятся повторно через " + i + " секунд";
        seekBarTint.setText(text);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
