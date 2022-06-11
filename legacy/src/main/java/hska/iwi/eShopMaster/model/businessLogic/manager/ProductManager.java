package hska.iwi.eShopMaster.model.businessLogic.manager;


import java.util.List;

public interface ProductManager {

	public List<ProductView> getProducts();

	public ProductView getProductById(int id);

	public ProductView getProductByName(String name);

	public int addProduct(String name, double price, int categoryId, String details);

	public List<ProductView> getProductsForSearchValues(String searchValue, Double searchMinPrice, Double searchMaxPrice);
	
	public boolean deleteProductsByCategoryId(int categoryId);
	
    public void deleteProductById(int id);
    
	
}
