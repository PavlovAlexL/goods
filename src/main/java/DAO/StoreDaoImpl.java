package DAO;

import DataSources.Item;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StoreDaoImpl implements StoreDao{

    @Override
    public void Purchase(List<Item> order, Connection connection) {

        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Storage(name, amount, price, date) VALUES (?,?,?,?)");
            statement.setString(1, order.get(0).getName());
            statement.setInt(2, order.size());
            statement.setBigDecimal(3, order.get(0).getPrice());
            statement.setDate(4,new java.sql.Date(order.get(0).getDate().getTime()));
            statement.executeUpdate();
            System.out.println("OK");
            connection.commit();
        } catch (SQLException e){
            try{
                connection.rollback();
            } catch (SQLException ex){
                throw new RuntimeException("transaction rollback error", e);
            }
        }
    }

    @Override
    public void Demand(List<Item> order, Connection connection) {
        String demandName = order.get(0).getName();
        Integer demandAmount = order.size();
        BigDecimal demandPrice = order.get(0).getPrice();
        Date demandDate = order.get(0).getDate();

        try{
            PreparedStatement statement = connection.prepareStatement("SELECT SUM(amount) FROM Storage WHERE name = ?");
            statement.setString(1, demandName);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            if(resultSet.getInt(1) < demandAmount){
                System.out.println("ERROR");
connection.rollback();
statement.close();
                return;
            }
connection.commit();

            statement = connection.prepareStatement( "SELECT id, name, amount, price, date FROM Storage WHERE amount > 0 ");//AND name = ? ORDER BY date");
            // statement.setString(1, "name");
            resultSet = statement.executeQuery();

            List <Item> result = new ArrayList<>();
            Integer id;
            String name;
            Integer amount;
            BigDecimal price;
            Date date;

            while(resultSet.next()){
                id = resultSet.getInt("id");
                name = resultSet.getString("name");
                amount = resultSet.getInt("amount");
                price = resultSet.getBigDecimal("price");
                date = resultSet.getDate("date");

                if(demandAmount > result.size()) {
                    while (amount-- != 0) {
                        result.add(new Item(id, name, price, date));
                        if (result.size() == demandAmount) {
                            break;
                        }
                    }
                }
                if(demandAmount == result.size()){
                    break;
                }
            }
connection.commit();
            statement = connection.prepareStatement("UPDATE Storage SET amount = amount - 1 WHERE id = ?");
            for(Item item : result){
                statement.setLong(1,item.getId());
                statement.executeUpdate();
            }
connection.commit();
            statement = connection.prepareStatement("INSERT INTO Sales(name, storage_id, price, date) VALUES (?,?,?,?)");
            for(Item item : result){
                statement.setString(1, item.getName());
                statement.setInt(2, item.getId());
                statement.setBigDecimal(3,demandPrice);
                statement.setDate(4, new java.sql.Date(demandDate.getTime()));
                statement.executeUpdate();
            }

connection.commit();
            System.out.println("OK");
        } catch (SQLException e){
            System.out.println("ERROR");
            try{
                connection.rollback();
            } catch (SQLException ex){
                throw new RuntimeException("transaction rollback error", e);
            }
        }


    }
}
