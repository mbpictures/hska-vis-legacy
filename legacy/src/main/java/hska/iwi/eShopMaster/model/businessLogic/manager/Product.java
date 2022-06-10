package hska.iwi.eShopMaster.model.businessLogic.manager;

/**
 * This class contains details about products.
 */
public class Product implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int id;

	private String name;

	private double price;

	private Category category;

	private String details;

	public Product() {
	}

	public Product(String name, double price, int categoryId) {
		this.name = name;
		this.price = price;
	}

	public Product(String name, double price, int categoryId, String details) {
		this.name = name;
		this.price = price;
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
}
