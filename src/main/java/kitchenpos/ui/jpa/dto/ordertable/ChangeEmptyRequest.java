package kitchenpos.ui.jpa.dto.ordertable;

public class ChangeEmptyRequest {

    private boolean empty;

    public ChangeEmptyRequest() {
    }

    public ChangeEmptyRequest(boolean empty) {
        this.empty = empty;
    }

    public boolean isEmpty() {
        return empty;
    }
}