package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Exam extends DataEntity {

    private final LocalDate date;
    private final LocalTime time;
    private final Boolean written;

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

    public LocalTime getTime() {
        return time;
    }

    public Boolean getWritten() {
        return written;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Exam exam = (Exam) o;
        return Objects.equals(date, exam.date) &&
                Objects.equals(time, exam.time) &&
                Objects.equals(written, exam.written);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), date, time, written);
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
