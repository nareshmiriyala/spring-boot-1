package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.constants.RoleType;
import com.agility.shopping.cart.dto.ProductRequest;
import com.agility.shopping.cart.exceptions.CustomError;
import com.agility.shopping.cart.mappers.ProductMapper;
import com.agility.shopping.cart.models.Product;
import com.agility.shopping.cart.repositories.ProductRepository;
import com.agility.shopping.cart.services.TokenAuthenticationService;
import com.agility.shopping.cart.services.UserService;
import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.agility.shopping.cart.constants.SecurityConstants.HEADER_STRING;
import static com.agility.shopping.cart.utils.ConvertUtil.convertObjectToJsonBytes;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class test RESTful api for product
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProductMapper productMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(filterChainProxy)
            .build();
    }

    /**
     * Test create product success
     */
    @Test
    public void testCreateProductSuccess() throws Exception {
        // Mock product
        Product product = Product.builder()
            .id(1L)
            .name("clothes")
            .url("localhost://url.com")
            .price(1000000L)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();
        ProductRequest request = productMapper.toProductRequest(product);

        // Generate token have role admin
        String username = "admin";
        Set<String> roles = Sets.newSet(RoleType.ADMIN.getName());
        String token = TokenAuthenticationService.createToken(username, roles);

        // Mock method
        when(productRepository.findByName("clothes")).thenReturn(null);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/products")
            .header(HEADER_STRING, token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("clothes")));
    }

    /**
     * Test create product fail resource exists exception when name product exists
     */
    @Test
    public void testCreateProductFailResourceExistsWhenNameProductExists()
        throws Exception {

        // Generate token have role admin
        String username = "admin";
        Set<String> roles = Sets.newSet(RoleType.ADMIN.getName());
        String token = TokenAuthenticationService.createToken(username, roles);

        // Mock product
        Product product = Product.builder()
            .id(1L)
            .name("clothes")
            .url("localhost://url.com")
            .price(1000000L)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();
        ProductRequest request = productMapper.toProductRequest(product);

        // Mock method
        when(productRepository.findByName("clothes")).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/products")
            .header(HEADER_STRING, token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(request)))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.code",
                is(CustomError.PRODUCT_EXIST.code())));
    }

    /**
     * Test find all product
     */
    @Test
    public void testFindAllProduct()
        throws Exception {

        // Generate token have role admin
        String username = "admin";
        Set<String> roles = Sets.newSet(RoleType.ADMIN.getName());
        String token = TokenAuthenticationService.createToken(username, roles);

        // Mock list product
        Product product1 = Product.builder()
            .id(1L)
            .name("clothes")
            .url("localhost://url.com")
            .price(1000000L)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();
        Product product2 = Product.builder()
            .id(1L)
            .name("dish")
            .url("localhost://url.com")
            .price(1000000L)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();
        List<Product> products = Arrays.asList(product1, product2);

        // Mock method
        when(productRepository.findAll()).thenReturn(products);

        mockMvc.perform(get("/products")
            .header(HEADER_STRING, token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name",
                is(product1.getName())))
            .andExpect(jsonPath("$[1].name",
                is(product2.getName())));
    }

}
