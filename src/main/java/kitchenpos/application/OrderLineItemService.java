package kitchenpos.application;

import kitchenpos.domain.collection.OrderLineItems;
import kitchenpos.repository.OrderLineItemRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderLineItemService {

    private OrderLineItemRepository orderLineItemRepository;

    public OrderLineItemService(OrderLineItemRepository orderLineItemRepository) {
        this.orderLineItemRepository = orderLineItemRepository;
    }

    public void saveOrderLineItems(OrderLineItems orderLineItems) {
        orderLineItemRepository.saveAll(orderLineItems.getElements());
    }
}