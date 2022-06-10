package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import hska.iwi.eShopMaster.model.businessLogic.manager.Category;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CategoryManagerImpl implements CategoryManager {

	private final String BASE_URI = System.getenv("CATEGORY_URL");
	private final WebClient webClient = WebClient.create(BASE_URI);

	public CategoryManagerImpl() {
	}

	public List<Category> getCategories() {
		return Arrays.asList(Objects.requireNonNull(webClient
				.get()
				.uri("/category")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Category[].class)
				.block()));
	}

	public Category getCategory(int id) {
		return Objects.requireNonNull(webClient
				.get()
				.uri(uriBuilder -> uriBuilder
						.path("/category/{id}")
						.build(id))
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Category.class)
				.block());
	}

	public Category getCategoryByName(String name) {
		return null;
	}

	public void addCategory(String name) {
		Category product = new Category(name);
		Objects.requireNonNull(webClient
				.post()
				.uri("/category")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(product)
				.retrieve()
				.bodyToMono(Integer.class)
				.block());
	}

	public void delCategory(Category cat) {
		delCategoryById(cat.getId());
	}

	public void delCategoryById(int id) {
		webClient
				.delete()
				.uri(uriBuilder -> uriBuilder
						.path("/category/{id}")
						.build(id))
				.retrieve();
	}
}
