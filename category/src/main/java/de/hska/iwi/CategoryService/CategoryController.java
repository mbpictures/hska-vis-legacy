package de.hska.iwi.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    @ResponseBody
    Iterable<Category> getAll() {
        return categoryRepository.findAll();
    }

    @PostMapping("/categories")
    @ResponseBody Category addCategory(@RequestBody Category newCategory) {
        return categoryRepository.save(newCategory);
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
