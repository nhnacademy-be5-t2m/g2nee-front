package com.t2m.g2nee.front.shoppingcart.adaptor;

import com.t2m.g2nee.front.shoppingcart.dto.ShoppingCartDto;
import java.util.List;

public interface ShoppingCartAdaptor {

    ShoppingCartDto.Response getBookForCart(String bookId, int quantity);

    void migrateCartRedisToDB(String memberId, List<ShoppingCartDto.Request> requestList);
}
