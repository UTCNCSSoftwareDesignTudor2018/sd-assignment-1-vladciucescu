package entity;

import java.util.Objects;

public class AdminProfile extends Profile {

    public AdminProfile() {
        super();
    }

    public AdminProfile(Profile profile) {
        super(profile.getId(), profile.getName(), profile.getId_card_number(), profile.getAddress());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminProfile user = (AdminProfile) o;
        return getId() == user.getId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "AdminProfile{id=" + getId() + "}";
    }
}
