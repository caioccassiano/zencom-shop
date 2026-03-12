package com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products;

import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products.dtos.request.ChangeStatusRequest;
import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products.dtos.request.CreateProductRequest;
import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products.dtos.request.UpdateProductRequest;
import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products.dtos.response.ProductResponse;
import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products.mappers.ProductHttpRequestMapper;
import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products.mappers.ProductHttpResponseMapper;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.product.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final ChangeProductStatusUseCase changeProductStatusUseCase;
    private final ListAllProductsUseCase listAllProductsUseCase;
    private final ListAllActiveProductsUseCase listAllActiveProductsUseCase;


    /*ADMIN ROUTES*/
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<ProductResponse> create (@RequestBody @Valid CreateProductRequest request) {
        var cmd = ProductHttpRequestMapper.toCreateCommand(request);
        var result = createProductUseCase.create(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductHttpResponseMapper.toResponse(result));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateProductRequest request
            ){
        var cmd = ProductHttpRequestMapper.toUpdateCommand(id, request);
        var result = updateProductUseCase.update(cmd);
        return ResponseEntity.status(HttpStatus.OK).body(
                ProductHttpResponseMapper.toResponse(result)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(
            @PathVariable UUID id,
            @RequestBody @Valid ChangeStatusRequest request
    ){
        var cmd = ProductHttpRequestMapper.toChangeStatusCommand(id, request);
        changeProductStatusUseCase.execute(cmd);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<List<ProductResponse>> get(){
        var result = listAllProductsUseCase.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                ProductHttpResponseMapper.toResponseList(result)
        );
    }

    /*PUBLIC ROUTES*/
    @GetMapping("/all")
    public ResponseEntity<List<ProductResponse>> getAll(){
        var result = listAllActiveProductsUseCase.getAllActiveProducts();
        return ResponseEntity.status(HttpStatus.OK).body(
                ProductHttpResponseMapper.toResponseList(result)
        );
    }






}

