package logic.nextGenPersistance;

import database.PersistanceHandler;
import logic.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonMapper implements AbstractClassMapper<Person> {
    public Connection conn;
    public void getConnection() {
        if (conn == null) {
            conn = PersistanceHandler.getConn();
        }
    }

    public Person getFromDB(int id) {
        getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT person_id,personName,personDesc FROM persons WHERE person_id = ?");
            stmt.setInt(1, id);
            ResultSet sqlRV = stmt.executeQuery();
            if (!sqlRV.next()){
                return null;
            }
            return new Person(sqlRV.getInt(1),sqlRV.getString(2),sqlRV.getString(3));
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Person> getAllFromDB() {
        getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT person_id,personName,personDesc FROM persons ");
            ResultSet sqlRV = stmt.executeQuery();
            int rowcount = 0;
            ArrayList<Person> returnValue = new ArrayList<>();
            while (sqlRV.next()){
                returnValue.add(new Person(sqlRV.getInt(1),sqlRV.getString(2),sqlRV.getString(3)));
            }
            return returnValue;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void addToDB(Person o) {
        getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO programs (person_id,personName,personDesc) VALUES (?,?,?");
            stmt.setInt(1, o.getId());
            stmt.setString(2,o.getName());
            stmt.setString(3,o.getDesc());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void addAllToDB(ArrayList<Person> o) {
        for (Person p : o) {
            addToDB(p);
        }
    }
}
