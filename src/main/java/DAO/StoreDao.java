package DAO;

import DataSources.Item;

public interface StoreDao {
    void Purchase(Item item);
    void Demand(Item item);
}
