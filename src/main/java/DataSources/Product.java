package DataSources;

public class Product {
    private Long id;
    private String name;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
