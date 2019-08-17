package DAO;

import DataSources.Product;
import Service.DBService;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDaoImpl implements ProductDao {

    @Override
    public void newProduct(Product product, Connection connection) {
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO Product(name) " + "VALUES (?);")){
            statement.setString(1, product.getName());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
