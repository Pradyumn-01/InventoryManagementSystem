package InventoryManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class InventoryManagementSystem {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/inventory_management_system";
    private static final String username = "root";
    private static final String password = "123";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Products products = new Products(connection, scanner);
            Customers customers = new Customers(connection, scanner);
            Orders orders = new Orders(connection, scanner);
            Suppliers suppliers = new Suppliers(connection, scanner);
            while (true) {
                System.out.println("=======*Inventory Management System*========");
                System.out.println("1.Add products:");
                System.out.println("2.Show Available Products:");
                System.out.println("3.Add Customers:");
                System.out.println("4.View Suppliers");
                System.out.println("5.Order Product:");
                System.out.println("6.Get order Place:");
                System.out.println("Enter your choice:");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        products.addProduct();
                        break;
                    case 2:
                        products.viewProducts();
                        break;
                    case 3:
                        customers.addCustomers();
                        break;
                    case 4:
                        suppliers.viewSuppliers();
                        break;
                    case 5:
                        orders.orderProduct();
                        break;
                    case 6:
                        placeOrder(products,customers,orders,suppliers,connection,scanner);
                        break;
                    case 7:
                        System.out.println("Thank you ! For Using Inventory Management System");
                        return;
                    default:
                        System.out.println("Please! Enter a valid choice");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        public static void placeOrder(Products products,Customers customers,Orders orders, Suppliers suppliers , Connection connection , Scanner scanner){
            System.out.print("Enter Customer Id:");
            int customer_id = scanner.nextInt();
            System.out.print("Enter Product Id:");
            int product_id = scanner.nextInt();
            System.out.print("Enter The Id of recent order: ");
            int order_id = scanner.nextInt();
            System.out.println("Enter Quantity of product:");
            int quantity = scanner.nextInt();
            if(products.productIsAvailableOrNot(product_id)&&orders.productIsBookedOrNot(order_id)){
                if (checkProductAvailability(order_id,product_id,connection)){
                    String appointmentQuery = "INSERT INTO order_detail(customer_id,product_id,order_id,quantity) VALUES(? ,? ,?,?)";
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                        preparedStatement.setInt(1,customer_id);
                        preparedStatement.setInt(2,product_id);
                        preparedStatement.setInt(3,order_id);
                        preparedStatement.setInt(4,quantity);
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected>0){
                            System.out.println("Order Placed  Successfully!!");
                        }
                        else {
                            System.out.println("Failed to Place order!!");
                        }
                    }
                    catch (SQLException e){
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("Product is not available on this id!!");
                }
            }
            else {
                System.out.println("Either Product or order id  doesn't exist!!!");
            }
        }
        public static boolean checkProductAvailability(int order_id , int product_id,Connection connection){
            String query = "SELECT COUNT(*) FROM order_detail WHERE order_id = ? AND product_id = ?";
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,order_id);
                preparedStatement.setInt(2,product_id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if(count==0){
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
            return false;
        }
    }

