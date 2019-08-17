package DataSources;

import java.math.BigDecimal;
import java.util.Date;

public class Item {

    private String name;
    private Integer amount;
    private BigDecimal price;
    private Date date;

    public Item(String name, Integer amount, BigDecimal price, Date date) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public Integer getAmount() {
        return amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }
}
