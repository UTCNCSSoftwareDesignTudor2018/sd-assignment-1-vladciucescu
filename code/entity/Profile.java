package entity;

import java.util.Objects;

public class Profile extends DataEntity {

    private final String name;
    private final int id_card_number;
    private final String address;

    public Profile() {
        super();
        this.name = "Name";
        this.id_card_number = 9999;
        this.address = "Address";
    }

    public Profile(int id, String name, int id_card_number, String address) {
        super(id);
        this.name = name;
        this.id_card_number = id_card_number;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public int getId_card_number() {
        return id_card_number;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Profile profile = (Profile) o;
        return id_card_number == profile.id_card_number &&
                Objects.equals(name, profile.name) &&
                Objects.equals(address, profile.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, id_card_number, address);
    }

    @Override
    public String toString() {
        return "Profile{id=" + getId() + '\'' +
                "name='" + name + '\'' +
                ", id_card_number=" + id_card_number +
                ", address='" + address + '\'' +
                '}';
    }
}
