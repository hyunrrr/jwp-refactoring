package kitchenpos.menu.ui;

import java.net.URI;
import java.util.List;
import kitchenpos.menu.application.MenuService;
import kitchenpos.menu.ui.dto.menu.MenuCreateRequest;
import kitchenpos.menu.ui.dto.menu.MenuCreateResponse;
import kitchenpos.menu.ui.dto.menu.MenuListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuRestControllerJpa {

    private final MenuService menuService;

    public MenuRestControllerJpa(final MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/api/menus")
    public ResponseEntity<MenuCreateResponse> create(@RequestBody final MenuCreateRequest menuCreateRequest) {
        MenuCreateResponse menuCreateResponse = menuService.create(menuCreateRequest);
        final URI uri = URI.create("/api/menus/" + menuCreateResponse.getId());
        return ResponseEntity.created(uri).body(menuCreateResponse);
    }

    @GetMapping("/api/menus")
    public ResponseEntity<List<MenuListResponse>> list() {
        return ResponseEntity.ok().body(menuService.list());
    }
}