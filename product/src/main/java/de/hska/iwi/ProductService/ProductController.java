package de.hska.iwi.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    @ResponseBody Iterable<Product> getAll(@RequestParam(name = "query") Optional<String> query,
                             @RequestParam(name = "minPrice") Optional<Double> minPrice,
                             @RequestParam(name = "maxPrice") Optional<Double> maxPrice) {
        return () -> StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .filter(product -> {
                    boolean result = true;
                    if (query.isPresent())
                        result = product.getName().contains(query.get()) || product.getDetails().contains(query.get());
                    if (minPrice.isPresent())
                        result &= product.getPrice() >= minPrice.get();
                    if (maxPrice.isPresent())
                        result &= product.getPrice() <= maxPrice.get();
                    return result;
                })
                .iterator();
    }

    @PostMapping("/products")
    @ResponseBody Product addProduct(@RequestBody Product newProduct) {
        return productRepository.save(newProduct);
    }

    @GetMapping("/products/{id}")
    @ResponseBody Product getProduct(@PathVariable Integer id) {
        return productRepository.findById(id)
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
