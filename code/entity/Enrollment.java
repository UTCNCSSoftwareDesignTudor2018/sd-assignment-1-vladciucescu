package entity;

import java.util.Objects;

public class Enrollment extends DataEntity {

    private final StudentProfile studentProfile;
    private final Course course;
    private final Double grade;

    public Enrollment() {
        super();
        this.studentProfile = new StudentProfile();
        this.course = new Course();
        this.grade = 1.0;
    }

    public Enrollment(int id, StudentProfile studentProfile, Course course, Double grade) {
        super(id);
        this.studentProfile = studentProfile;
        this.course = course;
        this.grade = grade;
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public Course getCourse() {
        return course;
    }

    public Double getGrade() {
        return grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(studentProfile, that.studentProfile) &&
                Objects.equals(course, that.course) &&
                Objects.equals(grade, that.grade);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), studentProfile, course, grade);
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "studentProfile=" + studentProfile +
                ", course=" + course +
                ", grade=" + grade +
                '}';
    }
}
