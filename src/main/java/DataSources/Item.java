package DataSources;

import java.math.BigDecimal;
import java.util.Date;

public class Item {

    private String name;
    private Integer amount;
    private BigDecimal price;
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Item(String name, Integer amount, BigDecimal price, Date date) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.date = date;
    }
}
