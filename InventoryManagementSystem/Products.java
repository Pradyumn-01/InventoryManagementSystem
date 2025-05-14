package InventoryManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Products {
   private Connection connection;
   private Scanner scanner;
   public Products(Connection connection , Scanner scanner) {
       this.connection = connection;
       this.scanner = scanner;
   }
   public void addProduct() {
       System.out.println("Enter Id of the Product:");
       int id = scanner.nextInt();
       System.out.println("Enter Name of the Product: ");
       String name = scanner.next();
       System.out.println("Enter Brand of the Product:");
       String brand = scanner.next();
       System.out.println("Enter Supplier id by which product will be supply:");
       int supplier_id = scanner.nextInt();
       System.out.println("Enter price of the Product");
       double price = scanner.nextDouble();
       String query = "INSERT INTO products(id,name,brand,supplier_id , price) VALUES(?,?,?,?,?)";
       try {
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setInt(1,id);
           preparedStatement.setString(2,name);
           preparedStatement.setString(3,brand);
           preparedStatement.setInt(4,supplier_id);
           preparedStatement.setDouble(5,price);
           int rowsAffected = preparedStatement.executeUpdate();
           if(rowsAffected>0){
               System.out.println("Product is added successfully!");
           }
           else {
               System.out.println("Failed to Add!");
           }
       }catch (SQLException e){
           e.printStackTrace();
       }
   }
    public  void viewProducts(){
        String query = "SELECT * FROM products";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("PRODUCTS:");
            System.out.println("+------------+-----------------------+---------------+-------------+--------------+");
            System.out.println("| Product Id | Name                  | Brand         | Supplier Id | Price        |");
            System.out.println("+------------+-----------------------+---------------+-------------+--------------+");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String brand = resultSet.getString("brand");
                int supplier_id  = resultSet.getInt("supplier_id");
                double price = resultSet.getDouble("price");
                System.out.printf("| %-10s | %-21s | %-15s | %-13s | %-14s\n",id,name,brand,supplier_id,price);
                System.out.println("+------------+-----------------------+---------------+-------------+--------------+");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean productIsAvailableOrNot(int id){
        String query = "SELECT * FROM products WHERE id = ?";
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

