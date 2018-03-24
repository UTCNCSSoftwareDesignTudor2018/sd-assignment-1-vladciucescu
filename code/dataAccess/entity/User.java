package dataAccess.entity;

import java.util.Objects;

public class User extends DataEntity {

    private String name;
    private final int id_card_number;
    private String address;

    public User() {
        super();
        this.name = "Name";
        this.id_card_number = 9999;
        this.address = "Address";
    }

    public User(int id, String name, int id_card_number, String address) {
        super(id);
        this.name = name;
        this.id_card_number = id_card_number;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_card_number() {
        return id_card_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() && id_card_number == user.id_card_number;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id_card_number);
    }

    @Override
    public String toString() {
        return "User{id=" + getId() + '\'' +
                "name='" + name + '\'' +
                ", id_card_number=" + id_card_number +
                ", address='" + address + '\'' +
                '}';
    }
}
