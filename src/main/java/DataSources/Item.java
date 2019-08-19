package DataSources;

import java.math.BigDecimal;
import java.util.Date;

public class Item {

    private Long id;
    private String name;
    private BigDecimal price;
    private Date date;

    public Item(String name, BigDecimal price, Date date) {
        this.name = name;
        this.price = price;
        this.date = date;
    }

    public Item(Long id, String name, BigDecimal price, Date date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }
}
