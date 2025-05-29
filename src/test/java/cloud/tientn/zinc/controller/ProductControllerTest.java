package cloud.tientn.zinc.controller;

import cloud.tientn.zinc.client.imageStorage.AzureStorageClientImpl;
import cloud.tientn.zinc.model.Product;
import cloud.tientn.zinc.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ProductService productService;
    @MockitoBean
    private AzureStorageClientImpl azureStorageClient;
    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {



    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    void givenListProduct_whenfindAllProduct_thenReturnListOfProducts() throws Exception {
        List<Product> list= new ArrayList<>();
        Product p1= new Product();
        p1.setId(1L);
        p1.setName("Headphone");
        p1.setQuantity(7);
        p1.setPrice(19.0F);
        p1.setThumbnail("Test thumbnail");

        Product p2= new Product();
        p2.setId(2L);
        p2.setName("Laptop");
        p2.setQuantity(3);
        p2.setPrice(999.0F);
        p2.setThumbnail("Test thumbnail");

        list.add(p1);
        list.add(p2);
        BDDMockito.given(productService.findAllProducts(ArgumentMatchers.isNull())).willReturn(list);
        //when
        ResultActions response= mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products"));

        //then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty());
    }
}