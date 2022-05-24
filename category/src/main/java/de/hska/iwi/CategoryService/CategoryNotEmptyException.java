package de.hska.iwi.CategoryService;

public class CategoryNotEmptyException extends RuntimeException {
    public CategoryNotEmptyException(Integer id) {
        super("The desired category (" + id + ") has assigned products");
    }
}
