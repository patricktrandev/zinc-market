package cloud.tientn.zinc.controller;

import cloud.tientn.zinc.repository.OrderRepository;
import cloud.tientn.zinc.response.*;
import cloud.tientn.zinc.response.converter.ProductMapper;
import cloud.tientn.zinc.service.OrderService;
import cloud.tientn.zinc.utils.StatusCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<Response> createProduct(@Valid @RequestBody OrderDto orderDto){
        var securityContext= SecurityContextHolder.getContext();
        String name=securityContext.getAuthentication().getName();
        Long id= orderService.createOrder(orderDto,name);
        return new ResponseEntity<>(new Response(true, StatusCode.CREATED,"Create Product successfully", id), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getOrderDetailsByOrder(@PathVariable Long id){
        List<OrderItemByOrderImpl> orders = orderService.getOrderDetailsByOrderId(id);
        return new ResponseEntity<>(new Response(true, StatusCode.SUCCESS,"Create Product successfully", orders), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Response> processNewOrders(){

        orderService.processBatchOrders();
        return new ResponseEntity<>(new Response(true, StatusCode.CREATED,"Successfully Process Orders"), HttpStatus.CREATED);
    }
}
