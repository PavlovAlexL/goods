package DAO;

import DataSources.Item;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DAO для работы с таблицей Store.
 */
public class StoreDaoImpl implements StoreDao{

    private String transactionProductName;
    private Integer transactionProductId;
    private Integer transactionItemsAmount;
    private BigDecimal transactionItemPrice;
    private java.sql.Date transactionDate;

    /**
     * Закупка партии товара.
     *
     * @param order      Список, содержащий закупленный товар.
     * @param connection Соединение с БД.
     */
    @Override
    public void Purchase(List<Item> order, Connection connection) {

        try {
            fillFields(order, connection);
            if (transactionProductId < 1) {
                System.out.print("ERROR");
                return;
            }

            PreparedStatement statement = connection.prepareStatement("INSERT INTO Storage(product_id, amount, price, date) VALUES (?,?,?,?)");
            statement.setInt(1, transactionProductId);
            statement.setInt(2, transactionItemsAmount);
            statement.setBigDecimal(3, transactionItemPrice);
            statement.setDate(4, transactionDate);
            statement.executeUpdate();
            System.out.print("OK");
            connection.commit();
        } catch (SQLException e){
            try{
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("transaction rollback error", ex);
            }
        }
    }

    /**
     * Продажа партии товара.
     *
     * @param order      Список проданных товаров.
     * @param connection Соединение с БД.
     */
    @Override
    public void Demand(List<Item> order, Connection connection) {
        fillFields(order, connection);
        if (transactionProductId < 1) {
            System.out.print("ERROR");
            return;
        }

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT SUM(amount) FROM Storage WHERE product_id = ?");
            statement.setInt(1, transactionProductId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            if (resultSet.getInt(1) < transactionItemsAmount) {
                System.out.print("ERROR");
                connection.rollback();
                statement.close();
                return;
            }

            statement = connection.prepareStatement("SELECT id, product_id, amount, price, date FROM Storage WHERE amount > 0 AND product_id = ? ORDER BY date");
            statement.setInt(1, transactionProductId);
            resultSet = statement.executeQuery();

            List <Item> result = new ArrayList<>();
            Integer id;
            Integer prductId;
            Integer amount;
            BigDecimal itemPrice;
            Date date;

            while(resultSet.next()){
                id = resultSet.getInt("id");
                prductId = resultSet.getInt("product_id");
                amount = resultSet.getInt("amount");
                itemPrice = resultSet.getBigDecimal("price");
                date = resultSet.getDate("date");

                if (transactionItemsAmount > result.size()) {
                    while (amount-- != 0) {
                        result.add(new Item(id, prductId, itemPrice, date));
                        if (result.size() == transactionItemsAmount) {
                            break;
                        }
                    }
                }
                if (transactionItemsAmount == result.size()){
                    break;
                }
            }

            statement = connection.prepareStatement("UPDATE Storage SET amount = amount - 1 WHERE id = ?");
            for(Item item : result){
                statement.setLong(1,item.getId());
                statement.executeUpdate();
            }

            statement = connection.prepareStatement("INSERT INTO Sales(storage_id, price, date) VALUES (?,?,?)");
            for (Item item : result) {
                statement.setInt(1, item.getId());
                statement.setBigDecimal(2, transactionItemPrice);
                statement.setDate(3, transactionDate);
                statement.executeUpdate();
            }

            connection.commit();
            System.out.print("OK");
        } catch (SQLException e) {
            System.out.print("ERROR");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("transaction rollback error", ex);
            }
        }


    }

    /**
     * Заполнение полей класса данными по закупке/продаже.
     *
     * @param order
     * @param connection
     */
    private void fillFields(List<Item> order, Connection connection) {
        transactionProductName = order.get(0).getName();
        transactionProductId = getProductId(transactionProductName, connection);
        transactionItemsAmount = order.size();
        transactionItemPrice = order.get(0).getPrice();
        transactionDate = new java.sql.Date(order.get(0).getDate().getTime());
    }

    /**
     * Получить Product_Id по наименованию товара.
     *
     * @param name       Наименование.
     * @param connection Соединение с БД.
     * @return Идентификатор.
     */
    private Integer getProductId(String name, Connection connection) {
        Integer productId = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM Product WHERE name = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                productId = resultSet.getInt(1);
            }
            connection.commit();

            return productId;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("transaction rollback error", ex);
            }
        }
        return productId;
    }
}
