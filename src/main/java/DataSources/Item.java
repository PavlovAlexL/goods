package DataSources;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Сущьность, хранящаяся в таблице Storage.
 */
public class Item {

    private Integer id;
    private String name;
    private BigDecimal price;
    private Date date;

    public Item(String name, BigDecimal price, Date date) {
        this.name = name;
        this.price = price;
        this.date = date;
    }

    public Item(Integer id, String name, BigDecimal price, Date date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
    }

    /**
     * Геттер лдя идентификатора в БД.
     * @return Идентификатор.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Геттер для наименования.
     * @return Наименование.
     */
    public String getName() {
        return name;
    }

    /**
     * Геттер для цены.
     * @return Цена.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Геттер для даты.
     * @return Дата.
     */
    public Date getDate() {
        return date;
    }
}
