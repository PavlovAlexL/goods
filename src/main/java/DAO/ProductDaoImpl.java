package DAO;

import DataSources.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDaoImpl implements ProductDao {

    @Override
    public void newProduct(Product product, Connection connection){
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM Product WHERE name = ?");
            statement.setString(1, product.getName());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                System.out.println("ERROR");
                return;
            }
                statement = connection.prepareStatement("INSERT INTO Product(name) VALUES (?)");
                statement.setString(1, product.getName());
                statement.executeUpdate();
            System.out.println("OK");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
