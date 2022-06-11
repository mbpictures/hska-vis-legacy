package hska.iwi.eShopMaster.model.businessLogic.manager;

public class ProductSend extends Product {
    private int categoryId;

    public ProductSend(String name, Double price, int categoryId, String details) {
        super(name, price, details);
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryInt(int categoryId) {
        this.categoryId = categoryId;
    }
}
