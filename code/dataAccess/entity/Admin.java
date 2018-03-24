package dataAccess.entity;

import java.util.Objects;

public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(User user) {
        super(user.getId(), user.getName(), user.getId_card_number(), user.getAddress());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Admin{id=" + getId() + "}";
    }
}
