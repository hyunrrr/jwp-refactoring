package kitchenpos.application.jpa;

import java.util.List;
import kitchenpos.domain.collection.OrderTables;
import kitchenpos.domain.collection.Orders;
import kitchenpos.domain.entity.TableGroup;
import kitchenpos.repository.OrderTableRepository;
import kitchenpos.repository.TableGroupRepository;
import kitchenpos.ui.jpa.dto.tablegroup.TableGroupCreateRequest;
import kitchenpos.ui.jpa.dto.tablegroup.TableGroupCreateResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TableGroupServiceJpa {

    private TableGroupRepository tableGroupRepository;
    private OrderTableRepository orderTableRepository;
    private TableServiceAssistant tableServiceAssistant;
    private OrderServiceJpa orderService;

    public TableGroupServiceJpa(TableGroupRepository tableGroupRepository,
                                OrderTableRepository orderTableRepository,
                                TableServiceAssistant tableServiceAssistant,
                                OrderServiceJpa orderService) {
        this.tableGroupRepository = tableGroupRepository;
        this.orderTableRepository = orderTableRepository;
        this.tableServiceAssistant = tableServiceAssistant;
        this.orderService = orderService;
    }

    @Transactional
    public TableGroupCreateResponse create(TableGroupCreateRequest tableGroupCreateRequest) {
        OrderTables orderTables = new OrderTables(tableGroupCreateRequest.getOrderTables());
        List<Long> orderTableIds = orderTables.getOrderTableIds();

        OrderTables savedOrderTables = new OrderTables(orderTableRepository.findAllById(orderTableIds));

        if (!savedOrderTables.isReadyToGroup(orderTables)) {
            throw new IllegalArgumentException();
        }

        TableGroup tableGroup = new TableGroup(savedOrderTables.getElements());
        tableGroupRepository.save(tableGroup);
        savedOrderTables.group(tableGroup);


        return new TableGroupCreateResponse(tableGroup.getId(), tableGroup.getCreatedDate(), tableGroup.getOrderTables());
    }

    @Transactional
    public void ungroup(Long tableGroupId) {
        OrderTables orderTables = tableServiceAssistant.findTablesInGroup(tableGroupId);
        Orders orders = orderService.findOrdersInOrderTables(orderTables);
        if (!orders.isAllCompleted()) {
            throw new IllegalStateException("모든 주문이 완료되지 않았습니다.");
        }

        orderTables.ungroup();
    }
}