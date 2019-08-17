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
import java.util.Date;

public class Goods {

    private ProductDao productDao = new ProductDaoImpl();
    private SalesDao salesDao = new SalesDaoImpl();
    private StoreDao storeDao = new StoreDaoImpl();
    private Connection connection;

    public Goods(Connection connection) {
        this.connection = connection;
    }

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
                purchase(new Item(name, amount, price, date));
                break;

            case "DEMAND":
                name = result[1];
                amount = Integer.parseInt(result[2]);
                price = parsePrice(result[3]);
                date = parseDate(result[4]);
                demand(new Item(name, amount, price, date));
                break;

            case "SALESREPORT":
                name = result[1];
                date = parseDate(result[2]);
                salesReport(name, date);
                break;

            default: throw new WrongInputException("Cannot execute command.");
        }
    }

    private void newProduct(Product product){
        productDao.newProduct(product, connection);
    }

    private void purchase(Item item){
        storeDao.Purchase(item);
    }

    private void demand (Item item){
        storeDao.Demand(item);
    }

    private void salesReport (String name, Date date){
        salesDao.getReport(name, date);
    }

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

    private BigDecimal parsePrice(String value) {
        BigDecimal price = BigDecimal.valueOf(Long.parseLong(value));
        return price;
    }



}
