package de.hska.iwi.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    @ResponseBody Iterable<Category> getAll(@RequestParam(name = "query") Optional<String> query) {
        return () -> StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .filter(product -> {
                    boolean result = true;
                    if (query.isPresent())
                        result = product.getName().contains(query.get());
                    return result;
                })
                .iterator();
    }

    @PostMapping("/categories")
    @ResponseBody int addCategory(@RequestBody Category newCategory) {
        return categoryRepository.save(newCategory).getId();
    }

    @GetMapping("/categories/{id}")
    @ResponseBody Category getCategory(@PathVariable Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @PutMapping("/categories/{id}")
    @ResponseBody Category updateCategory(@RequestBody Category newCategory, @PathVariable int id) {

        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(newCategory.getName());
                    return categoryRepository.save(category);
                })
                .orElseGet(() -> {
                    newCategory.setId(id);
                    return categoryRepository.save(newCategory);
                });
    }

    @DeleteMapping("/categories/{id}")
    void deleteCategory(@PathVariable Integer id) {
        categoryRepository.deleteById(id);
    }
}
