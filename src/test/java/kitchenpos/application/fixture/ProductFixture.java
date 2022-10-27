package kitchenpos.application.fixture;

import kitchenpos.domain.Product;

public class ProductFixture {

    public static final Long PRODUCT_ID_ONE = 1L;
    public static final Long PRODUCT_ID_TWO = 2L;
    public static final Long PRODUCT_ID_THREE = 3L;
    public static final Long INVALID_PRODUCT_ID = -1L;
    public static final String PRODUCT_NAME = "맛잇는 빵";
    public static final long PRODUCT_PRICE = 1000L;
    public static final long INVALID_PRODUCT_PRICE = -1L;
    public static final Product UNSAVED_PRODUCT = new Product(PRODUCT_NAME, PRODUCT_PRICE);
}
