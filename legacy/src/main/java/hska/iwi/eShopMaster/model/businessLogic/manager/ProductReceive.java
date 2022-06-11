package hska.iwi.eShopMaster.model.businessLogic.manager;

public class ProductReceive extends Product implements java.io.Serializable {
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
