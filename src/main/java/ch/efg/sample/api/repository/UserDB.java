package ch.efg.sample.api.repository;

import ch.efg.sample.api.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserDB {
    public static Map<String, User> create() {
        Map<String, User> users = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            users.put(String.valueOf(i), new User(String.valueOf(i), "user_" + i, String.valueOf(i % 10)));
        }

        return users;
    }
}
