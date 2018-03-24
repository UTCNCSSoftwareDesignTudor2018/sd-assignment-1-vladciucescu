package dataAccess.entity;

import java.util.Objects;

public class AdminAccount extends User {

    public AdminAccount() {
        super();
    }

    public AdminAccount(User user) {
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
        return "AdminAccount{id=" + getId() + "}";
    }
}
