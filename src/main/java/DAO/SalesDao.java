package DAO;

import java.sql.Connection;
import java.util.Date;

/**
 * DAO для работы со таблицей Sales.
 */
public interface SalesDao {

    /**
     * Получить отчет по прибвльности.
     *
     * @param name       Наименование товара
     * @param date       Дата для отчета.
     * @param connection Соединение с БД.
     */
    void getReport(String name, Date date, Connection connection);
}
