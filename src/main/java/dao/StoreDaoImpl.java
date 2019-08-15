package dao;

import DataSources.Product;

import java.math.BigDecimal;
import java.util.Date;

public class StoreDaoImpl implements StoreDao{

    @Override
    public void save(Product product, Integer amount, BigDecimal price, Date date) {

    }

    @Override
    public void demand(Integer amount, BigDecimal price, Date date) {

    }

    @Override
    public Integer salesReport(Date date) {
        return null;
    }
}
