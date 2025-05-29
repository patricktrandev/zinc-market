package cloud.tientn.zinc.service.impl;

import cloud.tientn.zinc.config.drools.DroolsThread;
import cloud.tientn.zinc.exception.ResourceNotFoundException;
import cloud.tientn.zinc.model.Customer;
import cloud.tientn.zinc.model.Order;
import cloud.tientn.zinc.repository.CustomerRepository;
import cloud.tientn.zinc.repository.OrderRepository;
import cloud.tientn.zinc.repository.ProductRepository;
import cloud.tientn.zinc.response.OrderDto;
import cloud.tientn.zinc.response.OrderItemByOrderImpl;
import cloud.tientn.zinc.response.OrderItemDto;
import cloud.tientn.zinc.service.OrderService;
import cloud.tientn.zinc.utils.OrderStatusUtils;
import cloud.tientn.zinc.utils.PriorityHelper;
import cloud.tientn.zinc.utils.TimeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final StatelessKieSession kieSession;
    private final DroolsThread droolsThread;

    @Override
    public Long createOrder(OrderDto orderDto, String username) {

        Customer customer=customerRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("Username", username));
        log.info("User id="+String.valueOf(customer.getId()));
        log.info("User id="+String.valueOf(customer.getExpenditure()));
        log.info("User id="+String.valueOf(customer.getNumberTotalOrder()));
        Order current= new Order();
        current.setOrderDate(LocalDateTime.now());
        current.setDiscount(0.0);
        current.setShippingAddress(orderDto.getShippingAddress());
        current.setCustomer(customer);
        current.setStatus(OrderStatusUtils.NEW.name());
        current.setPriority(orderDto.getPriority());

        try{
            kieSession.execute(Arrays.asList(current, customer));

            log.info("==== DROOLS SESSION END ==== ");
        }catch (Exception e){
            e.printStackTrace();}

//        log.info(String.valueOf(current.getDiscount()));
//        log.info(String.valueOf(current.getStatus()));
//        log.info(String.valueOf(current.getCustomer().getNumberTotalOrder()));
//
//        log.info(customer.getMembership());
        Double discountValue= current.getDiscount();
        String orderJson= convertOrderItemsToJson(orderDto.getOrders());
        Long orderId= orderRepository.processOrder(
            customer.getId(),orderDto.getShippingAddress(), current.getDiscount(), current.getPriority(), current.getStatus(),orderJson
        );

        return orderId;
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

    @Override
    public void processBatchOrders() {
        Order savedOrder1=orderRepository.findById(34L).orElseThrow(()-> new ResourceNotFoundException("Order", 34L));
        Order savedOrder2=orderRepository.findById(35L).orElseThrow(()-> new ResourceNotFoundException("Order", 35L));
        Order savedOrder3=orderRepository.findById(36L).orElseThrow(()-> new ResourceNotFoundException("Order", 36L));
        Order savedOrder4=orderRepository.findById(39L).orElseThrow(()-> new ResourceNotFoundException("Order", 39L));
        Order savedOrder5=orderRepository.findById(40L).orElseThrow(()-> new ResourceNotFoundException("Order", 39L));
        Order savedOrder6=orderRepository.findById(41L).orElseThrow(()-> new ResourceNotFoundException("Order", 39L));
        Order savedOrder7=orderRepository.findById(42L).orElseThrow(()-> new ResourceNotFoundException("Order", 39L));

        List<Order> orderQueue = new ArrayList<>();
        orderQueue.add(savedOrder1);
        orderQueue.add(savedOrder2);
        orderQueue.add(savedOrder3);
        orderQueue.add(savedOrder4);
        orderQueue.add(savedOrder5);
        orderQueue.add(savedOrder6);
        orderQueue.add(savedOrder7);
        PriorityHelper.mergeSortOrders(orderQueue);
        KieSession kieSession = droolsThread.getKieSession();
        try{
            droolsThread.start();
            TimeUtils.resetClock();
            droolsThread.addFactToSession(orderQueue);


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            droolsThread.getKieSession().halt();
            droolsThread.getKieSession().dispose();
            droolsThread.interrupt();
        }
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
