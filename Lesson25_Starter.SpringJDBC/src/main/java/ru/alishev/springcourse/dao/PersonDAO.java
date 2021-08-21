package ru.alishev.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Neil Alishev
 */
@Component
public class PersonDAO {
//    private static int PEOPLE_COUNT;
    private static final String URL = "jdbc:mysql://localhost:3306/taskjdbc";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root-123";

    private static Connection connection;
   static {
       try {
           Class.forName("com.mysql.jdbc.Driver");
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }

       try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Person> index() {
        List <Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT*FROM person";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    public Person show(int id) {
        Person person = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from person where id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;

//        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
//        return null;
    }

    public void save(Person person) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into person values(1, ?, ?, ?)");
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setString(3, person.getEmail());

//            String sql = "INSERT INTO PERSON VALUES(" + 1 + ",'" + person.getName() + "'," +  person.getAge() + ",'" + person.getEmail() + "')";
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        person.setId(++PEOPLE_COUNT);
//        people.add(person);
    }

    public void update(int id, Person updatedPerson) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update person set name=?, age=?, email=? where id=?");

            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setInt(2, updatedPerson.getAge());
            preparedStatement.setString(3, updatedPerson.getEmail());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//Person personToBeUpdated = show(id);
//        personToBeUpdated.setName(updatedPerson.getName());
//        personToBeUpdated.setAge(updatedPerson.getAge());
//        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from person where id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        people.removeIf(p -> p.getId() == id);
    }
}
