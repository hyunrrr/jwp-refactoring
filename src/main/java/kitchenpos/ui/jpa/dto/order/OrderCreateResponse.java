package kitchenpos.ui.jpa.dto.order;

import java.time.LocalDateTime;
import java.util.List;
import kitchenpos.domain.entity.OrderLineItem;
import kitchenpos.domain.entity.OrderStatus;

public class OrderCreateResponse {

    private Long id;
    private Long orderTableId;
    private String orderStatus;
    private LocalDateTime orderedTime;
    private List<OrderLineItem> orderLineItems;

    public OrderCreateResponse() {
    }

    public OrderCreateResponse(Long id, Long orderTableId, String orderStatus, LocalDateTime orderedTime,
                               List<OrderLineItem> orderLineItems) {
        this.id = id;
        this.orderTableId = orderTableId;
        this.orderStatus = orderStatus;
        this.orderedTime = orderedTime;
        this.orderLineItems = orderLineItems;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderTableId() {
        return orderTableId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }
}