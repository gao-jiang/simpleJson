package tech.yooko;

import tech.yooko.pojo.User;
import tech.yooko.serializer.SimpleSerializer;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        User user = new User();
        user.setNickname("john");
        user.setUsername("yoko");
        user.setPassword("1888");
        Map<String , String> map = new HashMap<>();
        map.put("yoko", "kawaii");
        map.put("john", "awesome");
        user.setMap(map);
        System.out.println(SimpleSerializer.serializeObject(user, user.getClass()));
    }
}
