package DAO;

import DataSources.Product;

import java.sql.Connection;

public interface ProductDao {
    void newProduct(Product product, Connection connection);
}
