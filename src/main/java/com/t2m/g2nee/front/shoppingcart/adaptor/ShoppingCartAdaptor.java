package com.t2m.g2nee.front.shoppingcart.adaptor;

import com.t2m.g2nee.front.shoppingcart.dto.ShoppingCartDto;

public interface ShoppingCartAdaptor {

    ShoppingCartDto.Response getBookForCart(String bookId, int quantity);

}
