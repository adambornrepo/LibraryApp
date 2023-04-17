package entities.concretes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Penalty implements Serializable {

    private Person person;
    private LocalDate start = LocalDate.now();
    private LocalDate end;
    private Boolean isActive = true;

    public Penalty() {
    }

    public Penalty(Person person, LocalDate end) {
        this.person = person;
        this.end = end;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Penalty penalty)) return false;
        return Objects.equals(getPerson(), penalty.getPerson());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPerson(), getStart());
    }
}
