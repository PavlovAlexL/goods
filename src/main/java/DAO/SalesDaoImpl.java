package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SalesDaoImpl implements SalesDao{

    @Override
    public void getReport(String name, Date date, Connection connection) {
        //SELECT sum(a.price - b.price) from sales as a inner join storage as b on b.id = a.storage_id where a.date < DATE '2017-03-02' and a.name = 'iphone';
        try{
        PreparedStatement statement = connection.prepareStatement("SELECT sum(a.price - b.price) from sales as a inner join storage as b on b.id = a.storage_id where a.date < ? and a.name = ?");
        statement.setDate(1, new java.sql.Date(date.getTime()));
        statement.setString(2, name);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            System.out.println(resultSet.getInt(1));
            }
        connection.commit();
        } catch (SQLException e) {
            try{
                connection.rollback();
            } catch (SQLException ex){
                throw new RuntimeException("transaction rollback error", e);
            }
        }
    }
}
