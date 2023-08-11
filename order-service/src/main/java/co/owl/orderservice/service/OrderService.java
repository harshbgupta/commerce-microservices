package co.owl.orderservice.service;

import co.owl.orderservice.dto.InventoryResponse;
import co.owl.orderservice.dto.OrderLineItemsDto;
import co.owl.orderservice.dto.OrderRequest;
import co.owl.orderservice.model.Order;
import co.owl.orderservice.model.OrderLineItems;
import co.owl.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsList().stream().map(this::mapToDto).toList();
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .OrderLineItemsList(orderLineItems)
                .build();

        List<String> skuCodes = orderRequest.getOrderLineItemsList().stream().map(OrderLineItemsDto::getSkuCode).toList();
        //Calling Inventory Service using Web Client
        //need to check weather all items are in stock or not
        InventoryResponse[] inventoryResponseArray = webClient.get().uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        assert inventoryResponseArray != null;
        boolean iaAllItemInStock = Arrays.stream(inventoryResponseArray).allMatch(inventoryResponse -> inventoryResponse.isInStock);
        if (iaAllItemInStock) {
            log.info("Order {} is placed", order.getId());
            orderRepository.save(order);
        } else {
            log.info("One or Some products are not in stock");
            throw new IllegalArgumentException("One or Some products are not in stock");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity())
                .skuCode(orderLineItemsDto.getSkuCode())
                .build();
    }
}
