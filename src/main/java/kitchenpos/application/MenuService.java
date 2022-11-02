package kitchenpos.application;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.domain.collection.MenuProducts;
import kitchenpos.domain.entity.Menu;
import kitchenpos.domain.entity.MenuGroup;
import kitchenpos.domain.entity.Price;
import kitchenpos.domain.entity.Product;
import kitchenpos.repository.MenuRepository;
import kitchenpos.ui.jpa.dto.menu.MenuCreateRequest;
import kitchenpos.ui.jpa.dto.menu.MenuCreateResponse;
import kitchenpos.ui.jpa.dto.menu.MenuListResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuGroupService menuGroupService;
    private final ProductService productService;

    public MenuService(MenuRepository menuRepository,
                       MenuGroupService menuGroupService,
                       ProductService productService) {
        this.menuRepository = menuRepository;
        this.menuGroupService = menuGroupService;
        this.productService = productService;
    }

    @Transactional
    public MenuCreateResponse create(MenuCreateRequest menuCreateRequest) {
        MenuGroup menuGroup = menuGroupService.findMenuGroup(menuCreateRequest.getMenuGroupId());

        MenuProducts menuProducts = new MenuProducts(menuCreateRequest.getMenuProducts());
        final List<Product> products = productService.findProducts(menuProducts.getProductIds());
        long sum = menuProducts.sumPrice(products);

        if (menuCreateRequest.getPrice() > sum) {
            throw new IllegalArgumentException();
        }

        Menu menu = new Menu(menuCreateRequest.getName(), menuGroup, menuProducts.getElements(), new Price(menuCreateRequest.getPrice()));
        menuRepository.save(menu);

        return new MenuCreateResponse(menu.getId(), menuCreateRequest.getName(), menuCreateRequest.getPrice(),
                menuCreateRequest.getMenuGroupId(), menu.getMenuProducts());
    }

    public List<MenuListResponse> list() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream()
                .map(menu -> new MenuListResponse(menu.getId(), menu.getName(), menu.getPrice().getValue(), menu.getMenuGroup().getId(), menu.getMenuProducts()))
                .collect(Collectors.toList());
    }
}