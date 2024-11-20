package org.example.vkApi;


import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;

import java.util.List;

public class VkRepository {

    public final long APP_ID = 52724692;
    public final String CODE = "vk1.a.q3qwwoeoROkfPYxd4I6DvFI6COYU6Rc6zxyhRFlc82j-7r8pbl8fLpvJSxh5V_mCSp3Kj0FbrJW1KaE5s3s7zsGH7Ff3rn2o7GZVnhIOuaKD-beaA3wE5MGtvTSQg4StjFbzLO161kRzh9kqzMK9rG84vDMUdPHNGkSKeBryPs_L_rarSAoe9IMK3kjXoaCT";
    private final VkApiClient vk;
    private final UserActor actor;

    public VkRepository() {
        TransportClient transportClient = new HttpTransportClient();
        vk = new VkApiClient(transportClient);
        actor = new UserActor(APP_ID, CODE);
    }

    public UserFull getUserByName(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<UserFull> users = null;

        try {
            users = vk.users().search(actor)
                    .q(name)
                    .count(1)
                    .fields(Fields.SEX, Fields.BDATE, Fields.CITY, Fields.STATUS)
                    .execute()
                    .getItems();
        } catch (ApiException | ClientException e) {
            return null;
        }

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    public void printUserInfo(UserFull user) {
        if (user == null) {
            System.out.println("Пользователь не найден");
            return;
        }

        var gender = (user.getSex() != null) ? (user.getSex() == com.vk.api.sdk.objects.base.Sex.FEMALE ? "Женский" : "Мужской") : "Не указан";
        var birthDate = (user.getBdate() != null) ? user.getBdate() : "Не указана";
        System.out.println("Пол: " + gender);
        System.out.println("Дата рождения: " + birthDate);
    }
}
