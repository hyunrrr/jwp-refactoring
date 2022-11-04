package kitchenpos.menu.ui;

import java.net.URI;
import java.util.List;
import kitchenpos.menu.application.MenuGroupService;
import kitchenpos.menu.ui.dto.menugroup.MenuGroupCreateRequest;
import kitchenpos.menu.ui.dto.menugroup.MenuGroupCreateResponse;
import kitchenpos.menu.ui.dto.menugroup.MenuGroupListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuGroupRestControllerJpa {

    private final MenuGroupService menuGroupService;

    public MenuGroupRestControllerJpa(MenuGroupService menuGroupService) {
        this.menuGroupService = menuGroupService;
    }

    @PostMapping("/api/menu-groups")
    public ResponseEntity<MenuGroupCreateResponse> create(@RequestBody final MenuGroupCreateRequest menuGroupCreateRequest) {
        final MenuGroupCreateResponse menuGroupCreateResponse = menuGroupService.create(menuGroupCreateRequest);
        final URI uri = URI.create("/api/menu-groups/" + menuGroupCreateResponse.getId());
        return ResponseEntity.created(uri).body(menuGroupCreateResponse);
    }

    @GetMapping("/api/menu-groups")
    public ResponseEntity<List<MenuGroupListResponse>> list() {
        return ResponseEntity.ok().body(menuGroupService.list());
    }
}