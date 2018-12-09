import ch.efg.sample.api.model.User;
import ch.efg.sample.api.repository.UserDB;
import ch.efg.sample.api.service.IUserService;
import ch.efg.sample.api.service.UserService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {
    IUserService userService = new UserService();
    List<User> rawDatabase = new ArrayList<>(UserDB.create().values());

    @Test
    public void testFindAllExpectAllUsers() {
        assertEquals(rawDatabase, userService.findAll());
    }

    @Test
    public void testFindByIdExpectedUser() {
        assertEquals(new User("1", "user_1", "1"), userService.findById("1").get(0));
    }

    @Test
    public void testFindByIdExpectedNoUser() {
        assertTrue(userService.findById("100").isEmpty());
    }

    @Test
    public void testSaveAllExpectedContainNewUsers() {
        List<User> newUsers = Arrays.asList(
                new User("111", "user_111", "12"),
                new User("222", "user_222", "12"),
                new User("333", "user_333", "12"),
                new User("444", "user_444", "12"),
                new User("555", "user_555", "12"));
        userService.saveAll(newUsers);

        assertTrue(userService.findAll().contains(newUsers.get(0)));
        assertTrue(userService.findAll().contains(newUsers.get(1)));
        assertTrue(userService.findAll().contains(newUsers.get(2)));
        assertTrue(userService.findAll().contains(newUsers.get(3)));
        assertTrue(userService.findAll().contains(newUsers.get(4)));
    }

    @Test
    public void testSaveAllExpectedReturnNewUsersList() {
        List<User> newUsers = Arrays.asList(
                new User("111", "user_111", "12"),
                new User("222", "user_222", "12"),
                new User("333", "user_333", "12"),
                new User("444", "user_444", "12"),
                new User("555", "user_555", "12"));

        assertEquals(newUsers, userService.saveAll(newUsers));
    }

    @Test
    public void testSaveExpectedContainNewUser() {
        User newUser = new User("111", "user_111", "12");
        userService.save(newUser);

        assertTrue(userService.findAll().contains(newUser));
    }

    @Test
    public void testDeleteExpectedNormalDelete() {
        userService.delete("1");

        assertTrue(userService.findById("1").isEmpty());
    }

    @Test
    public void testDeleteExpectedNoDeletedUser() {
        userService.delete("111");

        assertEquals(rawDatabase, userService.findAll());
    }

    @Test
    public void testDeleteExpectedReturnDeletedUser() {
        assertEquals(new User("1", "user_1", "1"), userService.delete("1"));
    }

    @Test
    public void testFindAllGroupByIdExpectedReturnGroup() {
        Map<String, List<User>> groupedUsers = rawDatabase.stream().collect(Collectors.groupingBy(User::getGroupId));

        assertEquals(groupedUsers, userService.findAllGroupByGroupId());
    }
}
