package dao;

import DataSources.Product;

import java.math.BigDecimal;
import java.util.Date;

public interface StoreDao {

    void save(Product product, Integer amount, BigDecimal price, Date date);

    void demand(Integer amount, BigDecimal price, Date date);

    Integer salesReport(Date date);
}
