package entities.concretes;

import core.enums.BookStatus;
import core.enums.BookTypes;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Book implements Serializable {
    private String bookId;
    private String bookName;
    private String authorName;
    private String publisher;
    private Integer publishYear;
    private BookTypes bookType;
    private BookStatus bookStatus;

    public Book() {
    }

    public Book(String bookId) {
        this.bookId = bookId;
    }

    public Book(String bookName, String authorName, String publisher, Integer publicationYear, BookTypes bookType, BookStatus bookStatus) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.publisher = publisher;
        this.publishYear = publicationYear;
        this.bookType = bookType;
        this.bookStatus = bookStatus;

        if (bookType.toString().length() > 3) {
            this.bookId = bookType.toString().substring(0, 3) + hashCode();
        } else {
            this.bookId = bookType.toString() + hashCode();
        }
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    public BookTypes getBookType() {
        return bookType;
    }

    public void setBookType(BookTypes bookType) {
        this.bookType = bookType;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return Objects.equals(getBookId(), book.getBookId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookName(), getAuthorName(), getPublisher(), LocalDateTime.now());
    }
}
