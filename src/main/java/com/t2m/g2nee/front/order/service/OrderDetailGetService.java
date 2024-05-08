package com.t2m.g2nee.front.order.service;

import com.t2m.g2nee.front.order.adaptor.OrderGetAdaptor;
import com.t2m.g2nee.front.order.dto.OrderDetailDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderDetailGetService {

    private final OrderGetAdaptor orderGetAdaptor;

    List<OrderDetailDto.Response> getOrderDetailListByOrderId(Long orderId){

        return null;
    }


}
