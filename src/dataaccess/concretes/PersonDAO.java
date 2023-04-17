package dataaccess.concretes;

import dataaccess.abstracts.DataProcess;
import entities.concretes.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements DataProcess<Person> {
    private List<Person> personList = new ArrayList<>();

    @Override
    public void read() {

        try {
            File file = new File(PATH + "/PersonData");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            List<Person> people = (List<Person>) ois.readObject();
            personList = people;

            fis.close();
            ois.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void write() {

        try {
            File file = new File(PATH + "/PersonData");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(personList);

            fos.close();
            oos.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Person> getData() {
        return personList;
    }
}
