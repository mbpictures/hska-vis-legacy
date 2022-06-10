package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import hska.iwi.eShopMaster.model.businessLogic.manager.Category;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;

import java.util.ArrayList;
import java.util.List;

public class CategoryManagerImpl implements CategoryManager{
	
	public CategoryManagerImpl() {
	}

	public List<Category> getCategories() {
		return new ArrayList<Category>();
	}

	public Category getCategory(int id) {
		return null;
	}

	public Category getCategoryByName(String name) {
		return null;
	}

	public void addCategory(String name) {
	}

	public void delCategory(Category cat) {

// 		Products are also deleted because of relation in Category.java
	}

	public void delCategoryById(int id) {

	}
}
