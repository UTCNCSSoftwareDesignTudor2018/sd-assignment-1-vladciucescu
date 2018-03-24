package dataAccess.entity;

import java.util.Objects;

public class Enrollment extends DataEntity {

    private final StudentAccount student;
    private final Course course;
    private Double grade;

    public Enrollment() {
        super();
        this.student = new StudentAccount();
        this.course = new Course();
        this.grade = 1.0;
    }

    public Enrollment(int id, StudentAccount student, Course course, Double grade) {
        super(id);
        this.student = student;
        this.course = course;
        this.grade = grade;
    }

    public StudentAccount getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Enrollment that = (Enrollment) o;
        return getId() == that.getId() &&
                Objects.equals(student, that.student) &&
                Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), student, course);
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "student=" + student +
                ", course=" + course +
                ", grade=" + grade +
                '}';
    }
}
