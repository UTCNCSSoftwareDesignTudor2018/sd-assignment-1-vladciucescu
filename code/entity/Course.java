package entity;

import java.time.LocalDate;
import java.util.Objects;

public class Course extends DataEntity {

    private final String name;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Exam exam;

    public Course() {
        super();
        this.name = "Name";
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now();
        this.exam = new Exam();
    }

    public Course(int id, String name, LocalDate startDate, LocalDate endDate, Exam exam) {
        super(id);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.exam = exam;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Exam getExam() {
        return exam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name) &&
                Objects.equals(startDate, course.startDate) &&
                Objects.equals(endDate, course.endDate) &&
                Objects.equals(exam, course.exam);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, startDate, endDate, exam);
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", exam=" + exam +
                '}';
    }
}
