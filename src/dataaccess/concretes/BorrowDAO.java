package dataaccess.concretes;

import dataaccess.abstracts.DataProcess;
import entities.concretes.Borrow;
import entities.concretes.Penalty;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO implements DataProcess<Borrow> {

    private List<Borrow> borrowList = new ArrayList<>();

    @Override
    public void read() {

        try {
            File file = new File(PATH + "/BorrowData");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            List<Borrow> borrows = (List<Borrow>) ois.readObject();
            borrowList = borrows;

            fis.close();
            ois.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void write() {

        try {
            File file = new File(PATH + "/BorrowData");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(borrowList);

            fos.close();
            oos.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Borrow> getData() {
        return borrowList;
    }
}
