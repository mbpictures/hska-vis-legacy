package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductReceive;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductSend;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductView;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductManagerImpl implements ProductManager {
	private final String BASE_URI = System.getenv("PRODUCT_URL");
	private final WebClient webClient = WebClient.create(BASE_URI);

	public ProductManagerImpl() {
	}

	public List<ProductView> getProducts() {
		List<ProductReceive> productReceives = Arrays.asList(Objects.requireNonNull(webClient
				.get()
				.uri("/products")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(ProductReceive[].class)
				.block()));

		return productReceives.stream().map(product -> new ProductView(product, product.getCategory())).collect(Collectors.toList());
	}

	public List<ProductView> getProductsForSearchValues(String searchDescription,
														Double searchMinPrice, Double searchMaxPrice) {
		List<ProductReceive> productReceives = Arrays.asList(Objects.requireNonNull(webClient
				.get()
				.uri(uriBuilder -> uriBuilder
						.path("/products")
						.queryParam("query", searchDescription)
						.queryParam("minPrice", searchMinPrice)
						.queryParam("maxPrice", searchMaxPrice)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(ProductReceive[].class)
				.block()));

		return productReceives.stream().map(product -> new ProductView(product, product.getCategory())).collect(Collectors.toList());
	}

	public ProductView getProductById(int id) {
		ProductReceive productReceive = Objects.requireNonNull(webClient
				.get()
				.uri(uriBuilder -> uriBuilder
						.path("/products/{id}")
						.build(id))
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(ProductReceive.class)
				.block());

		return new ProductView(productReceive, productReceive.getCategory());
	}

	public ProductView getProductByName(String name) {
		List<ProductReceive> productReceives = Arrays.asList(Objects.requireNonNull(webClient
				.get()
				.uri(uriBuilder -> uriBuilder
						.path("/products")
						.queryParam("productName", name)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(ProductReceive[].class)
				.block()));

		return new ProductView(productReceives.get(0), productReceives.get(0).getCategory());
	}

	public int addProduct(String name, double price, int categoryId, String details) {
		ProductSend product = new ProductSend(name, price, categoryId, details);
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
				.retrieve()
				.bodyToMono(void.class)
				.block();
		;
	}

	public boolean deleteProductsByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		return false;
	}

}
