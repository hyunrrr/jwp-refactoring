package kitchenpos.domain.collection;

import java.util.List;
import kitchenpos.domain.entity.Order;

public class Orders {

    private List<Order> elements;

    public Orders(List<Order> elements) {
        this.elements = elements;
    }

    public boolean isAllCompleted() {
        for (Order order : elements) {
            if (!order.isCompleted()) {
                return false;
            }
        }
        return true;
    }
}