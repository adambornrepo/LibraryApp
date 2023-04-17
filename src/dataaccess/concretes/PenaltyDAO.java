package dataaccess.concretes;

import dataaccess.abstracts.DataProcess;
import entities.concretes.Borrow;
import entities.concretes.Penalty;

import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class PenaltyDAO implements DataProcess<Penalty> {

    private List<Penalty> penaltyList = new ArrayList<>();

    @Override
    public void read() {
        try {
            File file = new File(PATH + "/PenaltyData");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            List<Penalty> penalties = (List<Penalty>) ois.readObject();
            penaltyList = penalties;

            fis.close();
            ois.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void write() {

        try {
            File file = new File(PATH + "/PenaltyData");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(penaltyList);

            fos.close();
            oos.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Penalty> getData() {
        return penaltyList;
    }


}
