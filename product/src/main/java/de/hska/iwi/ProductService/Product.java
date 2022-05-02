package de.hska.iwi.ProductService;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;

import javax.persistence.*;

/**
 * This class contains details about products.
 */
@Entity
@Table(name = "product")
public class Product implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private double price;

	@Column(name = "category_id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private int categoryId;

	@Transient
	private Category category;

	private String details;

	public Product() {
	}

	public Product(String name, double price, int categoryId) {
		this.name = name;
		this.price = price;
		this.categoryId = categoryId;
	}

	public Product(String name, double price, int categoryId, String details) {
		this.name = name;
		this.price = price;
		this.categoryId = categoryId;
		this.details = details;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public static class Category implements java.io.Serializable {
		private String name;
		private int id;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
