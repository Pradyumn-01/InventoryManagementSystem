package InventoryManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Customers {
    private Connection connection;
    private Scanner scanner;
    public Customers(java.sql.Connection connection , Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
    public void addCustomers() {
        System.out.println("Enter Id of the Customer:");
        int id = scanner.nextInt();
        System.out.println("Enter Name of the Customer: ");
        String name = scanner.next();
        System.out.println("Enter Mobile number of Customer:");
        long mobile_no = scanner.nextLong();
        System.out.println("Enter Address of Customer:");
        String address = scanner.next();

        String query = "INSERT INTO customers(id,name,mobile_no,address) VALUES(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,name);
            preparedStatement.setLong(3,mobile_no);
            preparedStatement.setString(4,address);
            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected>0){
                System.out.println("Customer is added successfully!");
            }
            else {
                System.out.println("Failed to Add!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
