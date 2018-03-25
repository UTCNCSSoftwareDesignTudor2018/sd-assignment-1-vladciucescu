package entity;

import java.util.Objects;

public class StudentProfile extends Profile {

    private final int year;
    private final int group;

    public StudentProfile() {
        super();
        this.year = 1;
        this.group = 1;
    }

    public StudentProfile(Profile profile, int year, int group) {
        super(profile.getId(), profile.getName(), profile.getId_card_number(), profile.getAddress());
        this.year = year;
        this.group = group;
    }

    public int getYear() {
        return year;
    }

    public int getGroup() {
        return group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StudentProfile studentProfile = (StudentProfile) o;
        return year == studentProfile.year &&
                group == studentProfile.group;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), year, group);
    }

    @Override
    public String toString() {
        return "StudentProfile{" +
                "year=" + year +
                ", group=" + group +
                '}';
    }
}
