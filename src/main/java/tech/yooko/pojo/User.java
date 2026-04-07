package tech.yooko.pojo;

import lombok.Data;
import tech.yooko.annotation.Serialize;

import java.util.Map;

@Data
public class User {
    @Serialize
    private String username;
    @Serialize
    private String password;
    @Serialize
    private String nickname;
    @Serialize
    private Map<String, String> map;
}
