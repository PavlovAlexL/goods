package DataSources;

/**
 * Сущьность, хранящаяся в базе Product.
 */
public class Product {
    private Long id;
    private String name;

    public Product(String name) {
        this.name = name;
    }

    /**
     * Геттер лдя Имени.
     * @return Имя.
     */
    public String getName() {
        return name;
    }

}
