import DAO.ProductDao;
import DAO.ProductDaoImpl;
import DAO.SalesDao;
import DAO.SalesDaoImpl;
import DAO.StoreDao;
import DAO.StoreDaoImpl;
import DataSources.Item;
import DataSources.Product;
import Exceptions.WrongInputException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Основной сервис-бизнесспроцесс.
 */
public class Goods {

    private ProductDao productDao = new ProductDaoImpl();
    private SalesDao salesDao = new SalesDaoImpl();
    private StoreDao storeDao = new StoreDaoImpl();
    private Connection connection;

    public Goods(Connection connection) {
        this.connection = connection;
    }

    /**
     * Метод, выбирающий соответствующую комманде функцию.
     * @param command Вводимые данные.
     */
    public void execute(String command){
        String name;
        Integer amount;
        BigDecimal price;
        Date date;
        String[] result = command.split("\\s");

        switch (result[0]){

            case "NEWPRODUCT":
                name = result[1];
                newProduct(new Product(name));
                break;

            case "PURCHASE":
                name = result[1];
                amount = Integer.parseInt(result[2]);
                price = parsePrice(result[3]);
                date = parseDate(result[4]);
                purchase(name, amount, price, date);
                break;

            case "DEMAND":
                name = result[1];
                amount = Integer.parseInt(result[2]);
                price = parsePrice(result[3]);
                date = parseDate(result[4]);
                demand(name, amount, price, date);
                break;

            case "SALESREPORT":
                name = result[1];
                date = parseDate(result[2]);
                salesReport(name, date);
                break;

            default: throw new WrongInputException("Cannot execute command.");
        }
    }

    /**
     * Ввод нового наименования товара.
     * @param product Наименование.
     */
    private void newProduct(Product product){

        productDao.newProduct(product, connection);
    }

    /**
     * Закупка товара.
     * @param name Наименование.
     * @param amount Колличество.
     * @param price Цена.
     * @param date Дата закупки.
     */
    private void purchase(String name, Integer amount, BigDecimal price, Date date){
        List <Item> order = new ArrayList<>();
        for(int i = 0; i < amount; i++ ){
            order.add(new Item(name, price, date));
        }
        storeDao.Purchase(order, connection);
    }

    /**
     * Продажа товара со склада.
     * @param name Наименование.
     * @param amount Колличество.
     * @param price Цена.
     * @param date Дата продажи.
     */
    private void demand (String name, Integer amount, BigDecimal price, Date date){
        List <Item> order = new ArrayList<>();
        for(int i = 0; i < amount; i++ ){
            order.add(new Item(name, price, date));
        }
        storeDao.Demand(order, connection);
    }

    /**
     * Отчет по продажам.
     * @param name Наименование товара.
     * @param date Дата для формирования отчета.
     */
    private void salesReport (String name, Date date){
        salesDao.getReport(name, date, connection);
    }

    /**
     * Преобразует String дату в объект java.util.Date
     * @param value Входное значение.
     * @return Преобразованные данные.
     */
    private Date parseDate(String value) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date date = formatter.parse(value);
            return date;
        } catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Преобразует String параметр в денежный BigDecimal.
     * @param value Входное значение.
     * @return Преобразованные данные.
     */
    private BigDecimal parsePrice(String value) {
        BigDecimal price = BigDecimal.valueOf(Long.parseLong(value));
        return price;
    }

}
