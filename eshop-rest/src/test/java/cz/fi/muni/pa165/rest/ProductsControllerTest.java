package cz.fi.muni.pa165.rest;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cz.fi.muni.pa165.rest.exceptions.ResourceNotFoundException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import cz.fi.muni.pa165.RootWebContext;
import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.fi.muni.pa165.dto.Color;
import cz.fi.muni.pa165.dto.PriceDTO;
import cz.fi.muni.pa165.dto.ProductCreateDTO;
import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.enums.Currency;
import cz.fi.muni.pa165.facade.ProductFacade;
import cz.fi.muni.pa165.rest.controllers.ProductsController;
import org.springframework.web.context.WebApplicationContext;
import org.testng.asserts.Assertion;

@WebAppConfiguration
@ContextConfiguration(classes = {RootWebContext.class})
public class ProductsControllerTest extends AbstractTestNGSpringContextTests {

    @Mock
    private ProductFacade productFacade;

    @Autowired
    @InjectMocks
    private ProductsController productsController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(productsController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    /**
     * This is not a real test, but it is here to show the usage of mockMvc to
     * perform a GET request and also how both request and response can be
     * printed out
     */
    @Test
    public void debugTest() throws Exception {
        doReturn(Collections.unmodifiableList(this.createProducts())).when(
                productFacade).getAllProducts();
        mockMvc.perform(get("/products")).andDo(print());
    }

    /**
     *
     * To do this, you can use
     * org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get()
     * org.springframework.test.web.servlet.result.MockMvcResultMatchers.content()
     * org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath()
     * org.springframework.test.web.servlet.result.MockMvcResultMatchers.status()
     *
     * You can read about Jsonpath notation from the documentation
     * https://github.com/jayway/JsonPath to define the expression in jsonPath()
     * method so that you will have similar statement as
     * JsonPath("<expression>").value("Raspberry PI")
     */
    @Test
    public void getAllProducts() throws Exception {

        doReturn(Collections.unmodifiableList(this.createProducts())).when(productFacade).getAllProducts();

        mockMvc.perform(get(ApiUris.ROOT_URI_PRODUCTS))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Raspberry PI"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Arduino"));
    }

    /**
     * mockMvc to:
     *
     * 1. perform a POST 2. Set the content type to APPLICATION_JSON 3. convert
     * the ProductCreateDTO instance to JSON with the helper method
     * convertObjectToJsonBytes() and pass it with content() in mockMvc
     * perform() 4. test also that the status is 200 OK
     */
    @Test
    public void createProduct() throws Exception {

        ProductCreateDTO productCreateDTO = new ProductCreateDTO();
        productCreateDTO.setName("Raspberry PI");

        doReturn(1l).when(productFacade).createProduct(
                any(ProductCreateDTO.class));

        String json = this.convertObjectToJsonBytes(productCreateDTO);

        mockMvc.perform(post(ApiUris.ROOT_URI_PRODUCTS+"/create").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void addCategory() throws Exception {
        List<ProductDTO> products = this.createProducts();

        doReturn(products.get(0)).when(productFacade).getProductWithId(10l);
        doReturn(products.get(1)).when(productFacade).getProductWithId(20l);

        CategoryDTO category = new CategoryDTO();
        category.setId(1l);

        String json = this.convertObjectToJsonBytes(category);

        mockMvc.perform(post(ApiUris.ROOT_URI_PRODUCTS+"/"+10l+"/categories").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    /**
     * Let's test for a non-existing product
     * 
     * 1. use mockito to throw an exception when a specific productid is not available
     * (Note: we would return here null but see comment in the RestController
     * implementation)
     * 2. test that the return code is 404 or in 4xx HTTP range
     */
    @Test
    public void getNonExistingProduct() throws Exception {

        doThrow(ResourceNotFoundException.class).when(productFacade).getProductWithId(666l);

        mockMvc.perform(get(ApiUris.ROOT_URI_PRODUCTS+"/"+666l))
                .andExpect(status().is4xxClientError());

    }

    /**
     * Just an utility method to create products for testing
     */
    private List<ProductDTO> createProducts() {
        ProductDTO productOne = new ProductDTO();
        productOne.setId(10L);
        productOne.setName("Raspberry PI");
        PriceDTO currentPrice = new PriceDTO();
        currentPrice.setCurrency(Currency.EUR);
        currentPrice.setValue(new BigDecimal("34"));
        productOne.setCurrentPrice(currentPrice);
        productOne.setColor(Color.BLACK);

        ProductDTO productTwo = new ProductDTO();
        productTwo.setId(20L);
        productTwo.setName("Arduino");
        PriceDTO price = new PriceDTO();
        price.setCurrency(Currency.EUR);
        price.setValue(new BigDecimal("44"));
        productTwo.setCurrentPrice(price);
        productTwo.setColor(Color.WHITE);

        return Arrays.asList(productOne, productTwo);
    }

    /**
     * Just an utility method to convert Objects to bytes to check JSON format
     */
    private static String convertObjectToJsonBytes(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }
}
