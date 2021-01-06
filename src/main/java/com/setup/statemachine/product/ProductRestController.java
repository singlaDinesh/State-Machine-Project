package com.setup.statemachine.product;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        super();
        this.productService = productService;
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductInfo getProduct(@RequestParam int productId) {
        return productService.getProduct(productId);
    }

    @GetMapping(value = "/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductInfo> getProducts() {
        return productService.getAllProducts();
    }

}
