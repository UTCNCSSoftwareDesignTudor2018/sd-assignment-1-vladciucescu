package dataAccess.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Course extends DataEntity {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Exam exam;

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

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Course course = (Course) o;
        return getId() == course.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
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
