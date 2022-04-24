package de.hska.iwi.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    @ResponseBody Iterable<Product> getAll() {
        return productRepository.findAll();
    }

    @PostMapping("/products")
    @ResponseBody Product newEmployee(@RequestBody Product newProduct) {
        return productRepository.save(newProduct);
    }

    @GetMapping("/products/{id}")
    @ResponseBody Product getProduct(@PathVariable Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PutMapping("/products/{id}")
    @ResponseBody Product replaceEmployee(@RequestBody Product newProduct, @PathVariable int id) {

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
    void deleteEmployee(@PathVariable Integer id) {
        productRepository.deleteById(id);
    }
}
