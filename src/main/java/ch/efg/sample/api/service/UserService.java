package ch.efg.sample.api.service;

import ch.efg.sample.api.model.User;
import ch.efg.sample.api.repository.UserDB;
import com.google.common.util.concurrent.Striped;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserService implements IUserService<User, String> {
    private final Map<String, User> users = UserDB.create();
    private Striped<ReadWriteLock> striped = Striped.lazyWeakReadWriteLock(10);

    public List<User> findAll() {
        return executeLockedRead((users) -> new ArrayList<>(users.values()));
    }

    public List<User> findById(String id) {
        return executeLockedRead((users) -> users.values().stream()
                .filter(user -> user.getId().equals(id))
                .collect(Collectors.toList()));
    }

    public <S extends User> List<S> saveAll(Iterable<S> newUsers) {
        return executeLockedWrite(users, (users) -> {
            List<S> newUsersList = new ArrayList<>();
            newUsers.forEach(user -> {
                users.put(user.getId(), user);
                newUsersList.add(user);
            });
            return newUsersList;
        });
    }

    public <S extends User> S save(S user) {
        executeLockedWrite(user.getId(), (users) -> users.put(user.getId(), user));
        return user;
    }

    public User delete(String userId) {
        return executeLockedWrite(userId, (users) -> users.remove(userId));
    }

    public Map<String, List<User>> findAllGroupByGroupId() {
        return executeLockedRead(users -> users.values().stream()
                .collect(Collectors.groupingBy(User::getGroupId)));
    }

    private <T> T executeLockedRead(Function<Map<String, User>, T> function) {
        ReadWriteLock lock = striped.get(users);
        lock.readLock().lock();
        try {
            return function.apply(users);
        } finally {
            lock.readLock().unlock();
        }
    }

    private <T> T executeLockedWrite(Object lockKey, Function<Map<String, User>, T> function) {
        ReadWriteLock lock = striped.get(lockKey);
        lock.writeLock().lock();
        try {
            return function.apply(users);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
