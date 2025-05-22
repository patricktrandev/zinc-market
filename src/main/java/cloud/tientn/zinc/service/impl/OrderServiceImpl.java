package cloud.tientn.zinc.service.impl;

import cloud.tientn.zinc.config.DroolsBeanFactory;
import cloud.tientn.zinc.exception.ResourceNotFoundException;
import cloud.tientn.zinc.model.Customer;
import cloud.tientn.zinc.model.Order;
import cloud.tientn.zinc.repository.CustomerRepository;
import cloud.tientn.zinc.repository.OrderRepository;
import cloud.tientn.zinc.repository.ProductRepository;
import cloud.tientn.zinc.response.OrderDto;
import cloud.tientn.zinc.response.OrderItemByOrderImpl;
import cloud.tientn.zinc.response.OrderItemDto;
import cloud.tientn.zinc.response.converter.OrderMapper;
import cloud.tientn.zinc.service.OrderService;
import cloud.tientn.zinc.utils.OrderStatusUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;
    private final DroolsBeanFactory droolsBeanFactory;

    @Override
    public Long createOrder(OrderDto orderDto, String username) {
        //KieSession kieSession = droolsBeanFactory.getKieSession();
        StatelessKieSession kieSession= droolsBeanFactory.getKieSession();
        Customer customer=customerRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("Username", username));
        log.info("User id="+String.valueOf(customer.getId()));
        Order current= new Order();
        current.setOrderDate(new Date());
        current.setDiscount(0.0);
        current.setShippingAddress(orderDto.getShippingAddress());
        current.setCustomer(customer);
        current.setStatus(OrderStatusUtils.PROCESSING.name());

        //drool xu li discount va statusva processing date
        try{
            kieSession.execute(Arrays.asList(current, customer));
//            kieSession.insert(customer);
//            kieSession.insert(current);
//            kieSession.fireAllRules();
        }catch (Exception e){
            e.printStackTrace();}
//        }finally {
//            kieSession.dispose();
//        }
        log.info(String.valueOf(current.getDiscount()));
        log.info(customer.getMembership());
        Double discountValue= 5.0;
        //String orderJson= convertOrderItemsToJson(orderDto.getOrders());
//        Long orderId= orderRepository.processOrder(
//            customer.getId(),orderDto.getShippingAddress(),discountValue,orderDto.getStatus(),orderJson
//        );
        return 1L;
    }

    @Override
    public List<OrderItemByOrderImpl> getOrderDetailsByOrderId(Long id) {
        List<OrderItemByOrderImpl> orders= orderRepository.queryOrderDetailsById(id);
        Set<Long> ids= new HashSet<>();
        for(var o: orders){
           ids.add(o.getProductId());
        }


        return orders;
    }

    private String convertOrderItemsToJson(Set<OrderItemDto> orderItems){
        try {
                List<Map<String, Object>> items= new ArrayList<>();
                for(OrderItemDto o: orderItems){
                    Map<String, Object> m= new HashMap<>();
                    m.put("productId",o.getProductId());
                    m.put("quantity",o.getQuantity());
                    items.add(m);
                }
            return objectMapper.writeValueAsString(items);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert order items to JSON", e);
        }
    }
}
