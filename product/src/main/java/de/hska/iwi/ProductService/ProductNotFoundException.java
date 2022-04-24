package de.hska.iwi.ProductService;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Integer id) {
        super("Could not find product for id " + id);
    }
}
