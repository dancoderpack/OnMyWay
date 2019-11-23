package com.conlage.onmyway.api;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiCallback;

import org.jetbrains.annotations.NotNull;

public class API {
    
    public static void post(final int groupID, String message, final OnPostResponseReceived onResponseReceived){
        VK.execute(new VKPostRequest(groupID, message), new VKApiCallback<VKPostResponse>() {
            @Override
            public void success(VKPostResponse vkPostResponse) {
                onResponseReceived.onReceive(vkPostResponse);
                System.out.println("Пост успешно создан: " + vkPostResponse.getPostID());
            }

            @Override
            public void fail(@NotNull Exception e) {
                onResponseReceived.onReceive(new VKPostResponse(false, groupID, 0));
                System.out.println("Ошибка, " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        });
    }

    public static void deletePost(final int groupID, final int postID, final OnDeletePostResponseReceived onDeletePostResponseReceived){
        VK.execute(new VKDeletePostRequest(groupID, postID), new VKApiCallback<VKPostDeleteResponse>() {
            @Override
            public void success(VKPostDeleteResponse vkPostDeleteResponse) {
                onDeletePostResponseReceived.onReceive(vkPostDeleteResponse);
            }

            @Override
            public void fail(@NotNull Exception e) {
                onDeletePostResponseReceived.onReceive(new VKPostDeleteResponse(false, groupID, postID));
            }
        });
    }

    public interface OnPostResponseReceived{
        void onReceive(VKPostResponse vkPostResponse);
    }

    public interface OnDeletePostResponseReceived{
        void onReceive(VKPostDeleteResponse vkPostDeleteResponse);
    }
    
}
