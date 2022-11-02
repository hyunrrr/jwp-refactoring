package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.domain.entity.Order;
import kitchenpos.domain.entity.OrderStatus;
import kitchenpos.domain.entity.OrderTable;
import kitchenpos.repository.OrderRepository;
import kitchenpos.repository.OrderTableRepository;
import kitchenpos.ui.jpa.dto.ordertable.ChangeEmptyRequest;
import kitchenpos.ui.jpa.dto.ordertable.ChangeNumberOfGuestsRequest;
import kitchenpos.ui.jpa.dto.ordertable.OrderTableCreateRequest;
import kitchenpos.ui.jpa.dto.ordertable.OrderTableCreateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TableServiceTest extends ServiceTest {

    @Autowired
    private TableService tableService;

    @Autowired
    private OrderTableRepository orderTableRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("orderTable을 생성한다.")
    @Test
    void create() {
        OrderTableCreateRequest orderTableCreateRequest = new OrderTableCreateRequest(10, false);

        OrderTableCreateResponse orderTableCreateResponse = tableService.create(orderTableCreateRequest);

        assertThat(orderTableCreateResponse.getId()).isNotNull();
    }

    @DisplayName("orderTable을 모두 조회한다.")
    @Test
    void list() {
        int numberOfTableBeforeCreate = tableService.list().size();
        tableService.create(new OrderTableCreateRequest(10, false));

        int numberOfTable = tableService.list().size();

        assertThat(numberOfTableBeforeCreate + 1).isEqualTo(numberOfTable);
    }

    @DisplayName("테이블을 empty로 바꾼다.")
    @Test
    void changeEmpty() {
        OrderTableCreateResponse orderTableCreateResponse = tableService.create(
                new OrderTableCreateRequest(10, false));
        OrderTable orderTable = orderTableRepository.findById(orderTableCreateResponse.getId()).get();
        orderRepository.save(new Order(orderTable, OrderStatus.COMPLETION));

        orderTableRepository.findById(orderTableCreateResponse.getId()).get();
        ChangeEmptyRequest changeEmptyRequest = new ChangeEmptyRequest(true);

        tableService.changeEmpty(orderTableCreateResponse.getId(), changeEmptyRequest);
        orderTable = orderTableRepository.findById(orderTableCreateResponse.getId()).get();

        assertThat(orderTable.isEmpty()).isEqualTo(changeEmptyRequest.isEmpty());
    }

    @DisplayName("테이블의 order가 모두 끝나지 않았으면 테이블을 empty로 바꿀 수 없다.")
    @Test
    void changeEmpty_Exception_Order_Not_Complete() {
        OrderTableCreateResponse orderTableCreateResponse = tableService.create(
                new OrderTableCreateRequest(10, false));
        OrderTable orderTable = orderTableRepository.findById(orderTableCreateResponse.getId()).get();
        orderRepository.save(new Order(orderTable, OrderStatus.COOKING));

        orderTableRepository.findById(orderTableCreateResponse.getId()).get();
        ChangeEmptyRequest changeEmptyRequest = new ChangeEmptyRequest(true);

        assertThatThrownBy(() -> tableService.changeEmpty(orderTableCreateResponse.getId(), changeEmptyRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("테이블의 손님 수를 바꾼다.")
    @Test
    void changeNumberOfGuests() {
        OrderTableCreateResponse orderTableCreateResponse = tableService.create(
                new OrderTableCreateRequest(10, false));

        ChangeNumberOfGuestsRequest changeNumberOfGuestsRequest = new ChangeNumberOfGuestsRequest(7);
        tableService.changeNumberOfGuests(orderTableCreateResponse.getId(), changeNumberOfGuestsRequest);

        OrderTable orderTable = orderTableRepository.findById(orderTableCreateResponse.getId()).get();
        assertThat(orderTable.getNumberOfGuests()).isEqualTo(changeNumberOfGuestsRequest.getNumberOfGuests());
    }

    @DisplayName("테이블이 EMPTY이면 손님 수를 바꿀 수 없다.")
    @Test
    void changeNumberOfGuests_Exception_Empty_Table() {
        OrderTableCreateResponse orderTableCreateResponse = tableService.create(
                new OrderTableCreateRequest(10, true));

        ChangeNumberOfGuestsRequest changeNumberOfGuestsRequest = new ChangeNumberOfGuestsRequest(7);
        assertThatThrownBy(() -> tableService.changeNumberOfGuests(orderTableCreateResponse.getId(), changeNumberOfGuestsRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("테이블 손님 수를 음수로 바꿀 수 없다.")
    @Test
    void changeNumberOfGuests_Exception_Invalid_Number() {
        OrderTableCreateResponse orderTableCreateResponse = tableService.create(
                new OrderTableCreateRequest(10, false));

        ChangeNumberOfGuestsRequest changeNumberOfGuestsRequest = new ChangeNumberOfGuestsRequest(-1);
        assertThatThrownBy(() -> tableService.changeNumberOfGuests(orderTableCreateResponse.getId(), changeNumberOfGuestsRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }
}