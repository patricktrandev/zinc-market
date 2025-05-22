package cloud.tientn.zinc.controller;

import cloud.tientn.zinc.client.imageStorage.AzureStorageClientImpl;
import cloud.tientn.zinc.response.ProductDto;
import cloud.tientn.zinc.response.Response;
import cloud.tientn.zinc.response.converter.ProductMapper;
import cloud.tientn.zinc.service.ProductService;
import cloud.tientn.zinc.utils.StatusCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final AzureStorageClientImpl azureStorageClient;

    @PostMapping
    public ResponseEntity<Response> createProduct(@RequestBody ProductDto productDto){

        ProductDto product= ProductMapper.convertToDto(productService.createProduct(productDto));
        return new ResponseEntity<>(new Response(true, StatusCode.CREATED,"Create Product successfully", product), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Response> findAllProduct(@RequestParam(value = "category", required = false)String name){
        List<ProductDto>productDtos= productService.findAllProducts(name).stream().map(ProductMapper::convertToDto).toList();
        return new ResponseEntity<>(new Response(true, StatusCode.SUCCESS,"Find all Product successfully",productDtos), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> findProductById(@PathVariable Long id){
        ProductDto product= ProductMapper.convertToDto(productService.findProductById(id));
        return new ResponseEntity<>(new Response(true, StatusCode.SUCCESS,"Find one Product successfully", product), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteProductById(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(new Response(true, StatusCode.SUCCESS,"Delete Product successfully"), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateProductById(@PathVariable Long id,@Valid @RequestBody ProductDto productDto){
        ProductDto udpated= ProductMapper.convertToDto(productService.updateCategory(id, productDto));
        return new ResponseEntity<>(new Response(true, StatusCode.SUCCESS,"Update Product successfully", udpated), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<Response> uploadImageProduct(@RequestParam String containerName,@RequestParam MultipartFile file){
        try(InputStream inputStream= file.getInputStream()){
            String imageUrl=azureStorageClient.uploadImage(containerName, file.getOriginalFilename(),inputStream, file.getSize());
            Map<String, Object> data=new HashMap<>();
            data.put("url", imageUrl);
            return new ResponseEntity<>(new Response(true, StatusCode.CREATED,"Create Product successfully", data), HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(new Response(false, StatusCode.INTERNAL_SERVER_ERROR,"Error upload file",e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getProxyImage(@PathVariable String id){
        String externalUrl= "https://zincshop.blob.core.windows.net/zinc-product-container/"+id;
        byte[] imageBytes = new RestTemplate().getForObject(externalUrl, byte[].class);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageBytes);
    }


}
