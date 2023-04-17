package dataaccess.concretes;

import dataaccess.abstracts.DataProcess;
import entities.concretes.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements DataProcess<Book> {

    private List<Book> bookList = new ArrayList<>();

    @Override
    public void read() {

        try {
            File file = new File(PATH + "/BookData");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            List<Book> books = (List<Book>) ois.readObject();
            bookList = books;

            fis.close();
            ois.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void write() {

        try {
            File file = new File(PATH + "/BookData");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(bookList);

            fos.close();
            oos.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Book> getData() {
        return bookList;
    }

}
