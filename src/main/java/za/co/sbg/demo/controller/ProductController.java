package za.co.sbg.demo.controller;


import za.co.sbg.demo.domain.request.ProductRequest;
import za.co.sbg.demo.persistence.entity.Product;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import za.co.sbg.demo.service.ProductService;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {

    @Inject
    private ProductService productService;

    @GET
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GET
    @Path("/{id}")
    public Product getProductById(@PathParam("id") Long id) {
        return productService.getProductById(id);
    }

    @POST
    public Response addProduct(ProductRequest productRequest) {
        productService.createProduct(productRequest);
        return Response.status(Response.Status.CREATED).entity(productRequest).build();
    }
    @PUT
    @Path("/{id}")
    public  Response updateProduct(ProductRequest productRequest, @PathParam("id") Long id) {
        if(!productService.updateProduct(id,productRequest)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();
    }
    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") Long id) {
        if(!productService.deleteProduct(id)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();
    }
}