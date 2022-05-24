package de.hska.iwi.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    private Product.Category getCategory(int id) {
        if (id <= 0) return null;
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", Integer.toString(id));
        try {
            ResponseEntity<Product.Category> responseEntity = restTemplate.getForEntity(
                    "http://category-service:8888/categories/{id}",
                    Product.Category.class,
                    uriVariables
            );
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            return null;
        }
    }

    @GetMapping("/products")
    @ResponseBody
    Iterable<Product> getAll(@RequestParam(name = "query") Optional<String> query,
                             @RequestParam(name = "minPrice") Optional<Double> minPrice,
                             @RequestParam(name = "maxPrice") Optional<Double> maxPrice,
                             @RequestParam(name = "categoryId") Optional<Integer> categoryId) {
        return () -> StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .filter(product -> {
                    boolean result = true;
                    if (query.isPresent())
                        result = product.getName().contains(query.get()) || product.getDetails().contains(query.get());
                    if (minPrice.isPresent())
                        result &= product.getPrice() >= minPrice.get();
                    if (maxPrice.isPresent())
                        result &= product.getPrice() <= maxPrice.get();
                    if (categoryId.isPresent())
                        result &= product.getCategoryId() == categoryId.get();
                    return result;
                })
                .peek(product -> product.setCategory(getCategory(product.getCategoryId())))
                .iterator();
    }

    @PostMapping("/products")
    @ResponseBody int addProduct(@RequestBody Product newProduct) {
        return productRepository.save(newProduct).getId();
    }

    @GetMapping("/products/{id}")
    @ResponseBody Product getProduct(@PathVariable Integer id) {
        return productRepository.findById(id).map(product -> {
                    product.setCategory(getCategory(product.getCategoryId()));
                    return product;
                })
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PutMapping("/products/{id}")
    @ResponseBody Product updateProduct(@RequestBody Product newProduct, @PathVariable int id) {

        return productRepository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setDetails(newProduct.getDetails());
                    product.setPrice(newProduct.getPrice());
                    product.setCategoryId(newProduct.getCategoryId());
                    return productRepository.save(product);
                })
                .orElseGet(() -> {
                    newProduct.setId(id);
                    return productRepository.save(newProduct);
                });
    }

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable Integer id) {
        productRepository.deleteById(id);
    }
}
