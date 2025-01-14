package de.hska.iwi.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
public class CategoryController {
    @Value("${PRODUCT_URL}")
    private String productServiceUrl;
    @Autowired
    private CategoryRepository categoryRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    private boolean hasProducts(int category) {
        if (category <= 0) return false;
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("categoryId", Integer.toString(category));
        try {
            ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(
                    productServiceUrl + "/products?categoryId={categoryId}",
                    Object[].class,
                    uriVariables
            );
            return Objects.requireNonNull(responseEntity.getBody()).length > 0;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

    @GetMapping("/categories")
    @ResponseBody
    Iterable<Category> getAll(@RequestParam(name = "query") Optional<String> query, HttpServletResponse response) {
        response.setHeader("Pod-Name", System.getenv("HOSTNAME"));
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
    @ResponseBody int addCategory(@RequestBody Category newCategory, HttpServletResponse response) {
        response.setHeader("Pod-Name", System.getenv("HOSTNAME"));
        return categoryRepository.save(newCategory).getId();
    }

    @GetMapping("/categories/{id}")
    @ResponseBody Category getCategory(@PathVariable Integer id, HttpServletResponse response) {

        response.setHeader("Pod-Name", System.getenv("HOSTNAME"));
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @PutMapping("/categories/{id}")
    @ResponseBody Category updateCategory(@RequestBody Category newCategory, @PathVariable int id, HttpServletResponse response) {
        response.setHeader("Pod-Name", System.getenv("HOSTNAME"));
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
    void deleteCategory(@PathVariable Integer id, HttpServletResponse response) {
        response.setHeader("Pod-Name", System.getenv("HOSTNAME"));
        if (hasProducts(id)) throw new CategoryNotEmptyException(id);
        categoryRepository.deleteById(id);
    }
}
