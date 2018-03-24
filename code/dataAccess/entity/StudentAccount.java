package dataAccess.entity;

import java.util.Objects;

public class StudentAccount extends User {

    private int year;
    private int group;

    public StudentAccount() {
        super();
        this.year = 1;
        this.group = 1;
    }

    public StudentAccount(User user, int year, int group) {
        super(user.getId(), user.getName(), user.getId_card_number(), user.getAddress());
        this.year = year;
        this.group = group;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StudentAccount student = (StudentAccount) o;
        return getId() == student.getId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        return "StudentAccount{" +
                "year=" + year +
                ", group=" + group +
                '}';
    }
}
