package DAO;

import DataSources.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO для работы со справочником Product.
 */
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
            statement.close();
            connection.commit();
        } catch (SQLException e){
            e.printStackTrace();
            try{
                connection.rollback();
            } catch (SQLException ex){
                throw new RuntimeException("transaction rollback error", e);
            }
        }
    }
}
