package org.example;

import org.example.db.models.Project;
import org.example.db.models.User;
import org.example.db.shard.Connection;
import org.example.db.factory.DBConnectionFactory;

import java.util.concurrent.ExecutionException;

public class ShardDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // Instantiate Users
        System.out.println("\nInstantiating Users");
        User user1 = new User(1, "User1", "Location1");
        User user2 = new User(2, "User2", "Location2");
        User user3 = new User(3, "User3", "Location3");
        System.out.println("Instantiating Users completed\n");

        // Instantiate Projects
        System.out.println("Instantiating Projects");
        Project project1 = new Project(1, "Project1", user1);
        Project project2 = new Project(2, "Project2", user2);
        Project project3 = new Project(3, "Project3", user3);
        System.out.println("Instantiating Users Projects\n");

        // Start DB Connection
        Connection dbConnection = DBConnectionFactory.getConnection();

        // save objects
        System.out.println("Saving Users");
        dbConnection.save(user1);
        dbConnection.save(user2);
        dbConnection.save(user3);
        System.out.println("Saving Users completed\n");

        System.out.println("Saving Projects");
        dbConnection.save(project1);
        dbConnection.save(project2);
        dbConnection.save(project3);
        System.out.println("Saving Projects completed\n");

        // Retrieve objects
        System.out.println("Retrieving Data");
        User u1 = (User) dbConnection.getData(user1.getId(), User.class.getName());
        User u2 = (User) dbConnection.getData(user2.getId(), User.class.getName());
        User u3 = (User) dbConnection.getData(user3.getId(), User.class.getName());

        Project p1 = (Project) dbConnection.getData(project1.getId(), Project.class.getName());
        Project p2 = (Project) dbConnection.getData(project2.getId(), Project.class.getName());
        Project p3 = (Project) dbConnection.getData(project3.getId(), Project.class.getName());
        System.out.println("Retrieving Data completed\n");
        System.out.println("Closing DB Connection");
        dbConnection.close();
    }
}
