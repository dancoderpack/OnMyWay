package com.conlage.onmyway.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.conlage.onmyway.api.API;
import com.conlage.onmyway.system.Constants;
import com.conlage.onmyway.system.OnMyWay;

import static android.content.Context.MODE_PRIVATE;

class ServiceHelper {

    private Context context;
    private MyAsync myAsync;

    ServiceHelper(Context context) {
        this.context = context;
    }

    private void post(int groupID, String message, API.OnPostResponseReceived onPostResponseReceived) {
        API.post(groupID, message, onPostResponseReceived);
    }

    private void deletePost(int groupID, int postID) {
        API.deletePost(groupID, postID, vkPostDeleteResponse -> {

        });
    }

    private void update() {

        new Thread(() -> {
            SharedPreferences sharedPreferences = context.getSharedPreferences("PeriodData", MODE_PRIVATE);
            int periodValue = sharedPreferences.getInt("PeriodValue", 30); //По default - 30 секунд
            try {
                Thread.sleep(periodValue * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            System.out.println("Обновление");

            if (OnMyWay.getInstance().group1 != null && OnMyWay.getInstance().group1.isRun()) {
                //Если первая группа запущена

                if (!OnMyWay.getInstance().group1.getMessage1().equals("")) {
                    //Если первое сообщение существует

                    //Существует ли уже пост?
                    if (OnMyWay.getInstance().group1.getPostID1() != -1) {
                        //Если да - удаляем его
                        System.out.println("Удаляем пост с ID: " + OnMyWay.getInstance().group1.getPostID1());
                        deletePost(Constants.GROUP_ID_1, OnMyWay.getInstance().group1.getPostID1());
                    }

                    //Отправляем сообщение
                    System.out.println("Отправляем сообщение: " + OnMyWay.getInstance().group1.getMessage1());
                    post(Constants.GROUP_ID_1, OnMyWay.getInstance().group1.getMessage1(), vkPostResponse ->
                            OnMyWay.getInstance().group1.setPostID1(vkPostResponse.getPostID()));
                }

                //Аналогично для второго сообщения
                if (!OnMyWay.getInstance().group1.getMessage2().equals("")) {
                    if (OnMyWay.getInstance().group1.getPostID2() != -1) {
                        System.out.println("Удаляем пост с ID: " + OnMyWay.getInstance().group1.getPostID2());
                        deletePost(Constants.GROUP_ID_1, OnMyWay.getInstance().group1.getPostID2());
                    }
                    System.out.println("Отправляем сообщение: " + OnMyWay.getInstance().group1.getMessage2());
                    post(Constants.GROUP_ID_1, OnMyWay.getInstance().group1.getMessage2(), vkPostResponse ->
                            OnMyWay.getInstance().group1.setPostID2(vkPostResponse.getPostID()));
                }
            }

            if (OnMyWay.getInstance().group2 != null && OnMyWay.getInstance().group2.isRun()) {
                //Если первая группа запущена

                if (!OnMyWay.getInstance().group2.getMessage1().equals("")) {
                    //Если первое сообщение существует

                    //Существует ли уже пост?
                    if (OnMyWay.getInstance().group2.getPostID1() != -1) {
                        //Если да - удаляем его
                        System.out.println("Удаляем пост с ID: " + OnMyWay.getInstance().group2.getPostID1());
                        deletePost(Constants.GROUP_ID_2, OnMyWay.getInstance().group2.getPostID1());
                    }

                    //Отправляем сообщение
                    System.out.println("Отправляем сообщение: " + OnMyWay.getInstance().group2.getMessage1());
                    post(Constants.GROUP_ID_2, OnMyWay.getInstance().group2.getMessage1(), vkPostResponse ->
                            OnMyWay.getInstance().group2.setPostID1(vkPostResponse.getPostID()));
                }

                //Аналогично для второго сообщения
                if (!OnMyWay.getInstance().group2.getMessage2().equals("")) {
                    if (OnMyWay.getInstance().group2.getPostID2() != -1) {
                        System.out.println("Удаляем пост с ID: " + OnMyWay.getInstance().group2.getPostID2());
                        deletePost(Constants.GROUP_ID_2, OnMyWay.getInstance().group2.getPostID2());
                    }
                    System.out.println("Отправляем сообщение: " + OnMyWay.getInstance().group2.getMessage2());
                    post(Constants.GROUP_ID_2, OnMyWay.getInstance().group2.getMessage2(), vkPostResponse ->
                            OnMyWay.getInstance().group2.setPostID2(vkPostResponse.getPostID()));
                }
            }

            if (OnMyWay.getInstance().group3 != null && OnMyWay.getInstance().group3.isRun()) {
                //Если первая группа запущена

                if (!OnMyWay.getInstance().group3.getMessage1().equals("")) {
                    //Если первое сообщение существует

                    //Существует ли уже пост?
                    if (OnMyWay.getInstance().group3.getPostID1() != -1) {
                        //Если да - удаляем его
                        System.out.println("Удаляем пост с ID: " + OnMyWay.getInstance().group3.getPostID1());
                        deletePost(Constants.GROUP_ID_3, OnMyWay.getInstance().group3.getPostID1());
                    }

                    //Отправляем сообщение
                    System.out.println("Отправляем сообщение: " + OnMyWay.getInstance().group3.getMessage1());
                    post(Constants.GROUP_ID_3, OnMyWay.getInstance().group3.getMessage1(), vkPostResponse ->
                            OnMyWay.getInstance().group3.setPostID1(vkPostResponse.getPostID()));
                }

                //Аналогично для второго сообщения
                if (!OnMyWay.getInstance().group3.getMessage2().equals("")) {
                    if (OnMyWay.getInstance().group3.getPostID2() != -1) {
                        System.out.println("Удаляем пост с ID: " + OnMyWay.getInstance().group3.getPostID2());
                        deletePost(Constants.GROUP_ID_3, OnMyWay.getInstance().group3.getPostID2());
                    }
                    System.out.println("Отправляем сообщение: " + OnMyWay.getInstance().group3.getMessage2());
                    post(Constants.GROUP_ID_3, OnMyWay.getInstance().group3.getMessage2(), vkPostResponse ->
                            OnMyWay.getInstance().group3.setPostID2(vkPostResponse.getPostID()));
                }
            }

            if (OnMyWay.getInstance().group4 != null && OnMyWay.getInstance().group4.isRun()) {
                //Если первая группа запущена

                if (!OnMyWay.getInstance().group4.getMessage1().equals("")) {
                    //Если первое сообщение существует

                    //Существует ли уже пост?
                    if (OnMyWay.getInstance().group4.getPostID1() != -1) {
                        //Если да - удаляем его
                        System.out.println("Удаляем пост с ID: " + OnMyWay.getInstance().group4.getPostID1());
                        deletePost(Constants.GROUP_ID_4, OnMyWay.getInstance().group4.getPostID1());
                    }

                    //Отправляем сообщение
                    System.out.println("Отправляем сообщение: " + OnMyWay.getInstance().group4.getMessage1());
                    post(Constants.GROUP_ID_4, OnMyWay.getInstance().group4.getMessage1(), vkPostResponse ->
                            OnMyWay.getInstance().group4.setPostID1(vkPostResponse.getPostID()));
                }

                //Аналогично для второго сообщения
                if (!OnMyWay.getInstance().group4.getMessage2().equals("")) {
                    if (OnMyWay.getInstance().group4.getPostID2() != -1) {
                        System.out.println("Удаляем пост с ID: " + OnMyWay.getInstance().group4.getPostID2());
                        deletePost(Constants.GROUP_ID_4, OnMyWay.getInstance().group4.getPostID2());
                    }
                    System.out.println("Отправляем сообщение: " + OnMyWay.getInstance().group4.getMessage2());
                    post(Constants.GROUP_ID_4, OnMyWay.getInstance().group4.getMessage2(), vkPostResponse ->
                            OnMyWay.getInstance().group4.setPostID2(vkPostResponse.getPostID()));
                }
            }

            if (OnMyWay.getInstance().isServiceWorked()) {
                update();
            }
        }).run();

    }

    void startService() {
        if (myAsync != null)
            myAsync.cancel(true);
        myAsync = new MyAsync();
        myAsync.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class MyAsync extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            System.out.println("ЧЕЕЕЕ");
            update();
            return null;
        }

        MyAsync() {

        }
    }

}
