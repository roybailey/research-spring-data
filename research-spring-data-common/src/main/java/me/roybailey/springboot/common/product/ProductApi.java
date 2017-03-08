package me.roybailey.springboot.common.product;

import feign.RequestLine;

import java.util.List;

/**
 * Open Feign API definition
 */
public interface ProductApi {

    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<ProductDto> getAllProducts();

}
