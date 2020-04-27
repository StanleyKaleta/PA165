package cz.fi.muni.pa165.rest.controllers;

import cz.fi.muni.pa165.dto.NewPriceDTO;
import cz.fi.muni.pa165.dto.ProductCreateDTO;
import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.rest.ApiUris;
import cz.fi.muni.pa165.rest.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import cz.fi.muni.pa165.facade.ProductFacade;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.util.List;

/**
 * REST Controller for Products
 * */
@RestController
@RequestMapping(ApiUris.ROOT_URI_PRODUCTS)
public class ProductsController {

    final static Logger logger = LoggerFactory.getLogger(ProductsController.class);

    @Inject
    private ProductFacade productFacade;

    /**
     * Get list of Products
     *
     * curl -i -X GET http://localhost:8080/eshop-rest/products
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ProductDTO> getProducts() {
       return productFacade.getAllProducts();
    }

    /**
     *
     * Get Product by identifier id
     *
     * curl -i -X GET http://localhost:8080/eshop-rest/products/1
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ProductDTO getProduct(@PathVariable long id) throws Exception {
        try {
            return productFacade.getProductWithId(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Delete one product by id
     *
     * curl -i -X DELETE http://localhost:8080/eshop-rest/products/1
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteProduct(@PathVariable long id) throws Exception {
        productFacade.deleteProduct(id);
    }

    /**
     * Create a new product by POST method
     *
     * curl -X POST -i -H "Content-Type: application/json" --data '{"name":"test","description":"test","color":"UNDEFINED","price":"200", "currency":"CZK", "categoryId":"1"}' http://localhost:8080/eshop-rest/products/create
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ProductDTO createProduct(@RequestBody ProductCreateDTO productCreateDTO) throws Exception {
        Long id = productFacade.createProduct(productCreateDTO);
        return productFacade.getProductWithId(id);
    }

    /**
     * Update the price for one product by PUT method
     *
     * curl -X PUT -i -H "Content-Type: application/json" --data '{"value":"16.33","currency":"CZK"}' http://localhost:8080/eshop-rest/products/4
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void changePrice(@PathVariable long id, @RequestBody NewPriceDTO newPriceDTO) throws Exception {
        newPriceDTO.setProductId(id);
        productFacade.changePrice(newPriceDTO);
    }
}
