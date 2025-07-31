package org.example.vkApi;


import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class VkRepository {

    public final long APP_ID;
    public final String ACCESS_TOKEN;
    private final VkApiClient vk;
    private final UserActor actor;

    private List<Long> groupIds = new ArrayList<>();

    public VkRepository() throws IOException, ClientException, ApiException {
        TransportClient transportClient = new HttpTransportClient();
        APP_ID = getId();
        ACCESS_TOKEN = getToken();
        vk = new VkApiClient(transportClient);
        actor = new UserActor(APP_ID, ACCESS_TOKEN);

        groupIds = getGroupIds();
    }

    private int getId() throws IOException {
        var path = "C:\\Users\\msape\\Desktop\\app_id.txt";
        return Integer.parseInt(Files.readString(Paths.get(path)));
    }

    private String getToken() throws IOException {
        var path = "C:\\Users\\msape\\Desktop\\access_token.txt";
        return Files.readString(Paths.get(path));
    }

    public String getStudentBirthMonth(String studentName) {
        try {
            for (var groupId : groupIds) {
                var users = getUsersSubscribed(studentName, groupId);
                if (!users.isEmpty()) {
                    var user = getUrfuStudent(users);
                    var birthDate = (user.getBdate() != null) ? user.getBdate() : "Нет данных";
                    return getRightFormMonth(birthDate);
                }
            }

            var users = getUsers(studentName);
            if (!users.isEmpty()) {
                var user = users.getFirst();
                var birthDate = (user.getBdate() != null) ? user.getBdate() : "Нет данных";
                return getRightFormMonth(birthDate);
            }
            return "Нет данных";

        } catch (ApiException | ClientException e) {
            System.err.println("Произошла ошибка при обращении к VK API: " + e.getMessage());
        }
        return "Нет данных";
    }

    public void getSleep(){
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public UserFull getUrfuStudent(List<UserFull> users) throws ClientException, ApiException {
        for (var user : users) {
            if (isUrFUStudent(user)) {
                return user;
            }

        }
        for (var user : users) {
            if(user.getBdate() != null){
                return user;
            }

        }
        return users.getFirst();
    }

    public boolean isUrFUStudent(UserFull user) {
        var universities = user.getUniversities();
        if (universities == null || universities.isEmpty()) {
            return false;
        }

        for(var university : universities){
            if (university.getName().equals("УрФУ им. первого Президента России Б. Н. Ельцина")){
                return true;
            }
        }

        return false;
    }

    public List<Long> getGroupIds() throws ClientException, ApiException {
        var groupNames = List.of(
                "Уральский федеральный университет | УрФУ",
                "Студент УрФУ",
                "RTF MEMES",
                "URFU MEMES",
                "БРС УрФУ Бот"
        );

        List<Long> groupIds = new ArrayList<>();
        for (var groupName : groupNames) {
            getSleep();
            var group = vk.groups()
                    .search(actor, groupName)
                    .count(1)
                    .execute()
                    .getItems()
                    .getFirst();
            var id = group.getId();
            groupIds.add(id);
        }
        return groupIds;
    }


    public List<UserFull> getUsers(String name) throws ClientException, ApiException {
        getSleep();
        return vk.users()
                .search(actor)
                .q(name)
                .fields(Fields.BDATE)
                .execute()
                .getItems();
    }

    public List<UserFull> getUsersSubscribed(String name, Long subscriptionGroupId) throws ApiException, ClientException {
        getSleep();
        return vk.users()
                .search(actor)
                .q(name)
                .fields(Fields.BDATE)
                .groupId(subscriptionGroupId)
                .execute()
                .getItems();
    }

    public String getRightFormMonth(String bdate){
        var months = new String[]{
                "январь", "февраль", "март", "апрель", "май",
                "июнь", "июль", "август", "сентябрь",
                "октябрь", "ноябрь", "декабрь"
        };

        var parts = bdate.split("\\.");
        if (parts.length < 2) {
            return "Нет данных";
        }

        try {
            var monthNum = Integer.parseInt(parts[1]) - 1;
            return months[monthNum];
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return "Нет данных";
        }
    }
}