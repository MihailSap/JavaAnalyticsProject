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
import java.util.List;

public class VkRepository {

    public final long APP_ID;
    public final String ACCESS_TOKEN;
    private final VkApiClient vk;
    private final UserActor actor;

    public VkRepository() throws IOException {
        TransportClient transportClient = new HttpTransportClient();
        APP_ID = getId();
        ACCESS_TOKEN = getToken();
        vk = new VkApiClient(transportClient);
        actor = new UserActor(APP_ID, ACCESS_TOKEN);
    }

    private int getId() throws IOException {
        var path = "C:\\Users\\msape\\Desktop\\app_id.txt";
        return Integer.parseInt(Files.readString(Paths.get(path)));
    }

    private String getToken() throws IOException {
        var path = "C:\\Users\\msape\\Desktop\\access_token.txt";
        return Files.readString(Paths.get(path));
    }

    public String getStudentBirthMonth(String inp) {
        var newName = getRightFormName(inp);
        getSleep();
        try {
            var users = getUsers(newName);
            if (!users.isEmpty()) {
                var user = users.get(0);
                var birthDate = (user.getBdate() != null) ? user.getBdate() : "Не указана))))";
                return getRightFormMonth(birthDate);
            } else {
                return "Нет данных";
            }
        } catch (ApiException | ClientException e) {
            System.err.println("Произошла ошибка при обращении к VK API: " + e.getMessage());
        }
        return "Нет данных";
    }

    public String getRightFormName(String inp){
        var splitInput = inp.split("\\s+");
        if (splitInput.length > 1) {
            var lastName = splitInput[0];
            var firstName = splitInput[1];
            return lastName + " " + firstName;
        }
        return inp;
    }

    public void getSleep(){
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<UserFull> getUsers(String name) throws ApiException, ClientException {
        var splitName = name.split("\\s+");
        if (splitName.length < 2) {
            return List.of();  // Если не удалось разбить имя на части, возвращаем пустой список
        }
        var firstName = splitName[1];
        var lastName = splitName[0];

        return vk.users()
                .search(actor)
                .q(firstName) // передаем имя
                .fields(Fields.BDATE)
                .execute()
                .getItems();
//                .stream()
//                .filter(user -> lastName.equals(user.getLastName())) // фильтруем по фамилии
//                .toList();

//        return vk.users()
//                .search(actor)
//                .q(name)
//                .fields(Fields.BDATE)
//                .execute()
//                .getItems();
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