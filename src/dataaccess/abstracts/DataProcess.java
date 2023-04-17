package dataaccess.abstracts;

import java.util.List;

public interface DataProcess<T> {

    String PATH = "src/data";

    void read();//Bunlar void olmayacak

    void write();

    List<T> getData();


}
