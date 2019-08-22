package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * DAO для работы со таблицей Sales.
 */
public class SalesDaoImpl implements SalesDao{

    /**
     * Получить отчет по прибвльности.
     *
     * @param name       Наименование товара
     * @param date       Дата для отчета.
     * @param connection Соединение с БД.
     */
    @Override
    public void getReport(String name, Date date, Connection connection) {
        BigDecimal result;
        Integer productId = getProductId(name, connection);
        java.sql.Date reportDate = new java.sql.Date(date.getTime());
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT SUM(a.price - b.price) from sales as a inner join storage as b on b.id = a.storage_id where a.date < ? and b.product_id= ?");
            statement.setDate(1, reportDate);
            statement.setInt(2, productId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getBigDecimal(1);
                System.out.print(result);
            }
            connection.commit();
        } catch (SQLException e) {
            try{
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("transaction rollback error", ex);
            }
        }
    }

    /**
     * Получить Product_Id по наименованию товара.
     *
     * @param name       Наименование.
     * @param connection Соединение с БД.
     * @return Идентификатор, либо 0
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
