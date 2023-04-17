package entities.concretes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Borrow implements Serializable {
    private Person person;
    private Book book;
    private LocalDate borrowDate = LocalDate.now();
    private LocalDate returnDate;
    private boolean isReturned = false;

    public Borrow() {
    }

    public Borrow(Person person, Book book, LocalDate returnDate) {
        this.person = person;
        this.book = book;
        this.returnDate = returnDate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Borrow borrow)) return false;
        return Objects.equals(getPerson(), borrow.getPerson()) && Objects.equals(getBook(), borrow.getBook());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPerson(), getBook(), getBorrowDate());
    }
}
