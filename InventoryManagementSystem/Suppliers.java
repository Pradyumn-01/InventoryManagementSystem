package InventoryManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Suppliers {
    private Connection connection;
    private Scanner scanner;
    public Suppliers(java.sql.Connection connection , Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
    public void viewSuppliers() {
        String query = "SELECT id, name, mobile_no FROM suppliers";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("SUPPLIERS:");
            System.out.println("+--------------+-----------------------+-------------------+");
            System.out.println("| Supplier Id  | Name                  | Mobile Number     |");
            System.out.println("+--------------+-----------------------+-------------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                long mobile_no = resultSet.getLong("mobile_no");
                System.out.printf("| %-14s | %-21s | %-19s |\n", id, name, mobile_no);
                System.out.println("+--------------+-----------------------+-------------------+");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


    }
}
