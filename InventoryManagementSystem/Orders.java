package InventoryManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Orders {
    private Connection connection;
    private Scanner scanner;
    public Orders(java.sql.Connection connection , Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
    public void orderProduct(){
        System.out.println("Enter Order Id:");
        int id = scanner.nextInt();
        System.out.println("Enter Name of the Product , which you want to order: ");
        String product_name = scanner.next();
        System.out.println("Enter price of the That Product");
        double price = scanner.nextDouble();
        String query = "INSERT INTO orders(id,product_name, price) VALUES(?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,product_name);
            preparedStatement.setDouble(3,price);
            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected>0){
                System.out.println("Product is Ordered Successfully!");
            }
            else {
                System.out.println("Failed to Order the Product!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean productIsBookedOrNot(int id){
        String query = "SELECT * FROM orders WHERE id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return  true;
            }
            else {
                return false;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}


