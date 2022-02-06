package com.wappstars.wappfood.controller;

import com.wappstars.wappfood.model.Product;
import com.wappstars.wappfood.repository.ProductRepository;
import com.wappstars.wappfood.service.CustomUserDetailsService;
import com.wappstars.wappfood.service.ProductService;
import com.wappstars.wappfood.util.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductController productController;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    ProductService productService;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    JwtUtil jwtUtil;

    @Test
    public void getProducts() throws Exception {

        List<Product> testProducts = new ArrayList<>();

        Product firstProduct = new Product();
        Product secondProduct = new Product();
        Product thirdProduct = new Product();

        testProducts.add(firstProduct);
        testProducts.add(secondProduct);
        testProducts.add(thirdProduct);

        when(productService.getProducts()).thenReturn(testProducts);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/wp-json/wf/v1/products"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getProduct() throws Exception {

        Optional<Product> product = Optional.of(new Product());

        when(productService.getProduct(1)).thenReturn(product);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/wp-json/wf/v1/products/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}