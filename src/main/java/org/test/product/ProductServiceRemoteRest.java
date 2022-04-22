package org.test.product;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import static java.lang.String.format;

@Service
@Slf4j
public class ProductServiceRemoteRest implements ProductService {


    public final String endpoint_product;

    private final RestTemplate restTemplate;



    public ProductServiceRemoteRest(RestTemplate restTemplate,  @Value("${product.server.url}") String productServerUrl) {
        this.restTemplate = restTemplate;
        this.endpoint_product = productServerUrl + "/product";
    }

    @Override
    public ProductDetailDTO getProductById(String productId) throws ProductNotFoundException {
        String similarids_service_url = endpoint_product + "/" + productId;

        long init = System.currentTimeMillis();

        try {
            var similarProducts = restTemplate.getForObject(similarids_service_url, ProductDetailDTO.class);
            long end = System.currentTimeMillis();
            log.debug("El tiempo que llevo recuperar del producto: {} fue de {} ms)", productId, (end - init));
            return similarProducts;
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ProductNotFoundException(format("Not found resource: %s.", similarids_service_url));
        }

    }


    @Override
    public Set<ProductDetailDTO> getSimilarProducts(String productId) throws ProductNotFoundException {
        String similarids_service_url = endpoint_product + "/" + productId + "/similarids";

        long init = System.currentTimeMillis();

        String[] resulSimilarids;
        try {
            resulSimilarids = restTemplate.getForObject(similarids_service_url, String[].class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ProductNotFoundException(format("Not found resource: %s.", similarids_service_url));
        }

        var similarProducts = Arrays.stream(resulSimilarids).map(this::getProductById).collect(Collectors.toSet());

        long end = System.currentTimeMillis();

        log.debug("El tiempo que llevo recuperar los similares del producto: {} fue de {} ms)", productId, (end - init));

        return similarProducts;

    }




}
