package DAO;

import DataSources.Product;
import java.sql.Connection;

/**
 * DAO для работы со справочником Product.
 */
public interface ProductDao {

    /**
     * Поместить товар в спарвочник.
     * @param product Объект для сохранения.
     * @param connection Соединение к БД.
     */
    void newProduct(Product product, Connection connection);
}
