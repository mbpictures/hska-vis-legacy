package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import hska.iwi.eShopMaster.model.businessLogic.manager.Product;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;

import java.util.ArrayList;
import java.util.List;

public class ProductManagerImpl implements ProductManager {
	
	public ProductManagerImpl() {
	}

	public List<Product> getProducts() {
		return new ArrayList<Product>();
	}

	public List<Product> getProductsForSearchValues(String searchDescription,
													Double searchMinPrice, Double searchMaxPrice) {
		return new ArrayList<Product>();
	}

	public Product getProductById(int id) {
		return null;
	}

	public Product getProductByName(String name) {
		return null;
	}
	
	public int addProduct(String name, double price, int categoryId, String details) {
		return 0;
	}
	

	public void deleteProductById(int id) {
	}

	public boolean deleteProductsByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		return false;
	}

}
