package com.conlage.onmyway;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import com.conlage.onmyway.system.Config;
import com.conlage.onmyway.system.Constants;
import com.conlage.onmyway.service.MyForeGroundService;
import com.conlage.onmyway.system.OnMyWay;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;
import com.vk.api.sdk.auth.VKScope;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {

    private int selectedGroupID;
    private int currentFragment;

    private FloatingActionButton fabSend;
    private EditText etMessage1, etMessage2;
    private TextView txtMessage1, txtMessage2;
    private SeekBar seekBar;
    private TextView seekBarTint;

    private int periodValue;

    public static String id1 = "test_channel_01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        if (!isAuthorize()) {
            authorize();
        }
        initPeriod();
        startService();
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click));
        showRightViews();
        switch (v.getId()) {
            case R.id.btn1:
                setTitle(Constants.GROUP_TITLE_1);
                selectedGroupID = Constants.GROUP_ID_1;
                setCurrentFragment(FRAGMENT_GROUP_1);
                break;

            case R.id.btn2:
                setTitle(Constants.GROUP_TITLE_2);
                selectedGroupID = Constants.GROUP_ID_2;
                setCurrentFragment(FRAGMENT_GROUP_2);
                break;

            case R.id.btn3:
                setTitle(Constants.GROUP_TITLE_3);
                selectedGroupID = Constants.GROUP_ID_3;
                setCurrentFragment(FRAGMENT_GROUP_3);
                break;

            case R.id.btn4:
                setTitle(Constants.GROUP_TITLE_4);
                selectedGroupID = Constants.GROUP_ID_4;
                setCurrentFragment(FRAGMENT_GROUP_4);
                break;

            case R.id.btn5:
                setTitle("Настройки");
                openSettings();
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

        if (seekBar.getProgress() <= 10) {
            seekBar.setProgress(10);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("PeriodData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("PeriodValue", seekBar.getProgress());
        editor.apply();
    }

    //Работа с VK SDK//

    private boolean isAuthorize() {
        return VK.isLoggedIn();
    }

    private void authorize() {
        VK.login(this, new ArrayList<VKScope>() {{
            add(VKScope.GROUPS);
        }});
    }

    private void showConnectionErrorAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Не удалось авторизоваться")
                .setMessage("Произошла ошибка при попытке подключения к ВКонтакте. Попробуйте еще раз!")
                .setPositiveButton("Попробовать еще раз", (dialog, which) -> authorize())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();
    }

    //Работа с View//

    private static final int FRAGMENT_NONE = -1;
    private static final int FRAGMENT_GROUP_1 = 1;
    private static final int FRAGMENT_GROUP_2 = 2;
    private static final int FRAGMENT_GROUP_3 = 3;
    private static final int FRAGMENT_GROUP_4 = 4;
    private static final int FRAGMENT_SETTINGS = 5;

    private void setCurrentFragment(int fragmentID) {
        fabSend.setEnabled(false);
        this.currentFragment = fragmentID;
        switch (fragmentID) {
            case FRAGMENT_GROUP_1:
                if (OnMyWay.getInstance().group1 != null && OnMyWay.getInstance().group1.isRun()) {
                    //Если группа запущена
                    fabSend.setImageResource(R.drawable.ic_stop);
                    fabSend.setEnabled(true);
                } else {
                    //Если группа не запущена
                    fabSend.setImageResource(R.drawable.ic_send);
                }
                break;
            case FRAGMENT_GROUP_2:
                if (OnMyWay.getInstance().group2 != null && OnMyWay.getInstance().group2.isRun()) {
                    //Если группа запущена
                    fabSend.setImageResource(R.drawable.ic_stop);
                    fabSend.setEnabled(true);
                } else {
                    //Если группа не запущена
                    fabSend.setImageResource(R.drawable.ic_send);
                }
                break;
            case FRAGMENT_GROUP_3:
                if (OnMyWay.getInstance().group3 != null && OnMyWay.getInstance().group3.isRun()) {
                    //Если группа запущена
                    fabSend.setImageResource(R.drawable.ic_stop);
                    fabSend.setEnabled(true);
                } else {
                    //Если группа не запущена
                    fabSend.setImageResource(R.drawable.ic_send);
                }
                break;
            case FRAGMENT_GROUP_4:
                if (OnMyWay.getInstance().group4 != null && OnMyWay.getInstance().group4.isRun()) {
                    //Если группа запущена
                    fabSend.setImageResource(R.drawable.ic_stop);
                    fabSend.setEnabled(true);
                } else {
                    //Если группа не запущена
                    fabSend.setImageResource(R.drawable.ic_send);
                }
                break;
        }
        initEditTexts(fragmentID);
    }

    private void initEditTexts(int fragmentID) {
        SharedPreferences sharedPreferences = getSharedPreferences("EditTextsData", MODE_PRIVATE);
        String key1 = "ET_" + fragmentID + "_1";
        String key2 = "ET_" + fragmentID + "_2";
        etMessage1.setText(sharedPreferences.getString(key1, ""));
        etMessage2.setText(sharedPreferences.getString(key2, ""));
    }

    private void initPeriod() {
        SharedPreferences sharedPreferences = getSharedPreferences("PeriodData", MODE_PRIVATE);
        periodValue = sharedPreferences.getInt("PeriodValue", 30); //По default - 30 секунд
        seekBar.setProgress(periodValue);
    }

    private void openSettings() {
        seekBar.setVisibility(View.VISIBLE);
        seekBarTint.setVisibility(View.VISIBLE);
        hideRightView();
        SharedPreferences sharedPreferences = getSharedPreferences("PeriodData", MODE_PRIVATE);
        periodValue = sharedPreferences.getInt("PeriodValue", 30); //По default - 30 секунд
        seekBar.setProgress(periodValue);
    }

    private void setupViews() {
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
                if (TextUtils.isEmpty(charSequence) && TextUtils.isEmpty(etMessage1.getText())) {
                    fabSend.setEnabled(false);
                } else {
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

    private void hideRightView() {
        fabSend.hide();
        etMessage1.setVisibility(View.GONE);
        etMessage2.setVisibility(View.GONE);
        txtMessage1.setVisibility(View.GONE);
        txtMessage2.setVisibility(View.GONE);
    }

    private void showRightViews() {
        fabSend.show();
        etMessage1.setVisibility(View.VISIBLE);
        etMessage2.setVisibility(View.VISIBLE);
        txtMessage1.setVisibility(View.VISIBLE);
        txtMessage2.setVisibility(View.VISIBLE);
        TextView txtSelectGroup = findViewById(R.id.txt_select_group);
        txtSelectGroup.setVisibility(View.GONE);
        seekBar.setVisibility(View.GONE);
        seekBarTint.setVisibility(View.GONE);
    }

    private void setBtnImages() {
        ImageView ivBtn1 = findViewById(R.id.iv_btn_1);
        ImageView ivBtn2 = findViewById(R.id.iv_btn_2);
        ImageView ivBtn3 = findViewById(R.id.iv_btn_3);
        ImageView ivBtn4 = findViewById(R.id.iv_btn_4);
        Glide.with(MainActivity.this).load(Constants.GROUP_IMAGE_1).into(ivBtn1);
        Glide.with(MainActivity.this).load(Constants.GROUP_IMAGE_2).into(ivBtn2);
        Glide.with(MainActivity.this).load(Constants.GROUP_IMAGE_3).into(ivBtn3);
        Glide.with(MainActivity.this).load(Constants.GROUP_IMAGE_4).into(ivBtn4);
    }

    private void setTitle(String title) {
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(title);
        tvTitle.setTextSize(title.equals("Настройки") ? 28f : 21f);
    }

    private void fabClick() {
        String message1 = etMessage1.getText().toString();
        String message2 = etMessage2.getText().toString();

        switch (currentFragment) {
            case 1:
                if (OnMyWay.getInstance().group1 == null) {
                    OnMyWay.getInstance().group1 = new Config.Group(true, message1, message2);
                } else {
                    OnMyWay.getInstance().group1 = new Config.Group(!OnMyWay.getInstance().group1.isRun(), message1, message2);
                }
                fabSend.setImageResource(OnMyWay.getInstance().group1.isRun() ? R.drawable.ic_stop : R.drawable.ic_send);
                break;
            case 2:
                if (OnMyWay.getInstance().group2 == null) {
                    OnMyWay.getInstance().group2 = new Config.Group(true, message1, message2);
                } else {
                    OnMyWay.getInstance().group2 = new Config.Group(!OnMyWay.getInstance().group2.isRun(), message1, message2);
                }
                fabSend.setImageResource(OnMyWay.getInstance().group2.isRun() ? R.drawable.ic_stop : R.drawable.ic_send);
                break;
            case 3:
                if (OnMyWay.getInstance().group3 == null) {
                    OnMyWay.getInstance().group3 = new Config.Group(true, message1, message2);
                } else {
                    OnMyWay.getInstance().group3 = new Config.Group(!OnMyWay.getInstance().group3.isRun(), message1, message2);
                }
                fabSend.setImageResource(OnMyWay.getInstance().group3.isRun() ? R.drawable.ic_stop : R.drawable.ic_send);
                break;
            case 4:
                if (OnMyWay.getInstance().group4 == null) {
                    OnMyWay.getInstance().group4 = new Config.Group(true, message1, message2);
                } else {
                    OnMyWay.getInstance().group4 = new Config.Group(!OnMyWay.getInstance().group4.isRun(), message1, message2);
                }
                fabSend.setImageResource(OnMyWay.getInstance().group4.isRun() ? R.drawable.ic_stop : R.drawable.ic_send);
                break;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("EditTextsData", MODE_PRIVATE);
        String key1 = "ET_" + currentFragment + "_1";
        String key2 = "ET_" + currentFragment + "_2";
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key1, message1);
        editor.putString(key2, message2);
        editor.apply();
    }


    //Работа с сервисом//

    private void startService(){
        createChannel();  //needed for the persistent notification created in service.

        //IntentService start with 5 random number toasts

        Intent service = new Intent(getBaseContext(), MyForeGroundService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!isServiceRunning())
                startForegroundService(service);
        } else {
            if (!isServiceRunning())
                startService(service);
        }
    }

    private void createChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel mChannel = new NotificationChannel(id1,
                    getString(R.string.channel_name),  //name of the channel
                    NotificationManager.IMPORTANCE_LOW);   //importance level
            //important level: default is is high on the phone.  high is urgent on the phone.  low is medium, so none is low?
            // Configure the notification channel.
            mChannel.setDescription(getString(R.string.channel_description));
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this channel, if the device supports this feature.
            mChannel.setShowBadge(true);
            if (nm != null) {
                nm.createNotificationChannel(mChannel);
            }
        }
    }

    private boolean isServiceRunning() {
        boolean serviceRunning = false;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        assert am != null;
        List<ActivityManager.RunningServiceInfo> l = am.getRunningServices(50);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : l) {
            if (runningServiceInfo.service.getClassName().equals("MyForeGroundService")) {
                serviceRunning = true;
            }
            return serviceRunning;
        }
        return false;
    }

}
