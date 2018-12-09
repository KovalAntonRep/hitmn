package ch.efg.sample.api.model;

import java.util.Objects;

public class User implements IUser {
    private String id;
    private String name;
    private String groupId;

    public User(String id, String name, String groupId) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGroupId() {
        return groupId;
    }

    public String toString() {
        return name + "::" + id + "::" + groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                name.equals(user.name) &&
                groupId.equals(user.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, groupId);
    }
}
