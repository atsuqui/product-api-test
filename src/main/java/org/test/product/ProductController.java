package org.test.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;

@RestController
@Slf4j
public class ProductController {

    private static int count=0;

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping(value = "/product/{productId}/similar", produces = {"application/json"})
    public ResponseEntity<?> getProductSimilar(@PathVariable("productId") String productId) {

        Set<ProductDetailDTO> similarProducts= Collections.emptySet();

        try {
            similarProducts = productService.getSimilarProducts(productId);
        } catch (ProductNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not found");
        }catch (Throwable th){
            log.error("Error find similars for product: {}. {}", productId, th);
            return ResponseEntity.internalServerError().body("");
        }

        return ResponseEntity.ok(similarProducts);

    }


}
