package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import hska.iwi.eShopMaster.model.businessLogic.manager.Product;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ProductManagerImpl implements ProductManager {
	private final String BASE_URI = System.getenv("PRODUCT_URL");
	private final WebClient webClient = WebClient.create(BASE_URI);

	public ProductManagerImpl() {
	}

	public List<Product> getProducts() {
		return Arrays.asList(Objects.requireNonNull(webClient
				.get()
				.uri("/products")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Product[].class)
				.block()));
	}

	public List<Product> getProductsForSearchValues(String searchDescription,
													Double searchMinPrice, Double searchMaxPrice) {
		return Arrays.asList(Objects.requireNonNull(webClient
				.get()
				.uri(uriBuilder -> uriBuilder
						.path("/products")
						.queryParam("query", searchDescription)
						.queryParam("minPrice", searchMinPrice)
						.queryParam("maxPrice", searchMaxPrice)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Product[].class)
				.block()));
	}

	public Product getProductById(int id) {
		return Objects.requireNonNull(webClient
				.get()
				.uri(uriBuilder -> uriBuilder
						.path("/products/{id}")
						.build(id))
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Product.class)
				.block());
	}

	public Product getProductByName(String name) {
		return null;
	}
	
	public int addProduct(String name, double price, int categoryId, String details) {
		Product product = new Product(name, price, categoryId, details);
		return Objects.requireNonNull(webClient
				.post()
				.uri("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(product)
				.retrieve()
				.bodyToMono(Integer.class)
				.block());
	}
	

	public void deleteProductById(int id) {
		webClient
				.delete()
				.uri(uriBuilder -> uriBuilder
						.path("/products/{id}")
						.build(id))
				.retrieve();
	}

	public boolean deleteProductsByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		return false;
	}

}
