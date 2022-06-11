package hska.iwi.eShopMaster.model.businessLogic.manager;

public class ProductView {
    private Integer id;

    private Category category;

    private String name;

    private Double price;

    private String details;

    public ProductView(Product product, Category category) {
        this.id = product.getId();
        this.category = category;
        this.name = product.getName();
        this.price = product.getPrice();
        this.details = product.getDetails();
    }

    public String getName() {
        return name;
    }

    public ProductView setName(String name) {
        this.name = name;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public ProductView setCategory(Category category) {
        this.category = category;
        return this;
    }

    public ProductView setPrice(Double price) {
        this.price = price;
        return this;
    }

    public ProductView setDetails(String details) {
        this.details = details;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public String getDetails() {
        return details;
    }

    public Integer getId() {
        return id;
    }
}
