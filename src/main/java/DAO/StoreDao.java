package DAO;

import DataSources.Item;

import java.sql.Connection;
import java.util.List;

/**
 * DAO для работы с таблицей Store.
 */
public interface StoreDao {

    /**
     * Закупка партии товара.
     *
     * @param order      Список, содержащий закупленный товар.
     * @param connection Соединение с БД.
     */
    void Purchase(List<Item> order, Connection connection);

    /**
     * Продажа партии товара.
     *
     * @param order      Список проданных товаров.
     * @param connection Соединение с БД.
     */
    void Demand(List<Item> order, Connection connection);
}
