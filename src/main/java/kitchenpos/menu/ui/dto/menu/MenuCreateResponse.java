package kitchenpos.menu.ui.dto.menu;

import java.util.List;

public class MenuCreateResponse {

    private Long id;
    private String name;
    private long price;
    private Long menuGroupId;
    private List<Long> menuProductIds;

    public MenuCreateResponse() {
    }

    public MenuCreateResponse(Long id, String name, long price, Long menuGroupId,
                              List<Long> menuProductIds) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProductIds = menuProductIds;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public List<Long> getMenuProductIds() {
        return menuProductIds;
    }
}