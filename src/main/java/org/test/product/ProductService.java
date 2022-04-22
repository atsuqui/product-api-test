package org.test.product;
import java.util.Set;

public interface ProductService {

    ProductDetailDTO getProductById(String productId) throws ProductNotFoundException;

    Set<ProductDetailDTO> getSimilarProducts(String productId) throws ProductNotFoundException;


}
