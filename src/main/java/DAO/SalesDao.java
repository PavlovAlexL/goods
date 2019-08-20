package DAO;

import java.sql.Connection;
import java.util.Date;

public interface SalesDao {
   void getReport(String name, Date date, Connection connection);
}
