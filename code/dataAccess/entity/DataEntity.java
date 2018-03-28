package dataAccess.entity;

import java.util.Objects;

public abstract class DataEntity {

    private final int id;

    DataEntity() {
        this.id = 0;
    }

    DataEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataEntity that = (DataEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
