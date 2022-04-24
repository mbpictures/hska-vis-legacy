package de.hska.iwi.CategoryService;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Integer id) {
        super("Could not find product for id " + id);
    }
}
