package DAO;

import DataSources.Item;

import java.sql.Connection;
import java.util.List;

public interface StoreDao {
    void Purchase(List<Item> order, Connection connection);
    void Demand(List<Item> order, Connection connection);
}
