package kitchenpos.order.ui;

import java.net.URI;
import java.util.List;
import kitchenpos.order.application.OrderService;
import kitchenpos.order.ui.dto.order.ChangeOrderStatusRequest;
import kitchenpos.order.ui.dto.order.ChangeOrderStatusResponse;
import kitchenpos.order.ui.dto.order.OrderCreateRequest;
import kitchenpos.order.ui.dto.order.OrderCreateResponse;
import kitchenpos.order.ui.dto.order.OrderListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestControllerJpa {

    private final OrderService orderService;

    public OrderRestControllerJpa(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/orders")
    public ResponseEntity<OrderCreateResponse> create(@RequestBody final OrderCreateRequest orderCreateRequest) {
        OrderCreateResponse orderCreateResponse = orderService.create(orderCreateRequest);
        final URI uri = URI.create("/api/orders/" + orderCreateResponse.getId());
        return ResponseEntity.created(uri).body(orderCreateResponse);
    }

    @GetMapping("/api/orders")
    public ResponseEntity<List<OrderListResponse>> list() {
        return ResponseEntity.ok()
                .body(orderService.list())
                ;
    }

    @PutMapping("/api/orders/{orderId}/order-status")
    public ResponseEntity<ChangeOrderStatusResponse> changeOrderStatus(@PathVariable final Long orderId,
                                                                       @RequestBody final ChangeOrderStatusRequest changeOrderStatusRequest) {
        return ResponseEntity.ok(orderService.changeOrderStatus(orderId, changeOrderStatusRequest.getOrderStatus()));
    }
}