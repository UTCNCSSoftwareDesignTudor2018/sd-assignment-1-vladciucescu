package dataAccess.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Exam extends DataEntity {

    private LocalDate date;
    private LocalTime time;
    private Boolean written;

    public Exam() {
        super();
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.written = false;
    }

    public Exam(int id, LocalDate date, LocalTime time, Boolean written) {
        super(id);
        this.date = date;
        this.time = time;
        this.written = written;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Boolean getWritten() {
        return written;
    }

    public void setWritten(Boolean written) {
        this.written = written;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return getId()==exam.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        return "Exam{" +
                "date=" + date +
                ", time=" + time +
                ", written=" + written +
                '}';
    }
}
