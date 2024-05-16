package com.t2m.g2nee.front.order.adaptor;

import com.t2m.g2nee.front.order.dto.OrderDetailDto;
import com.t2m.g2nee.front.order.dto.response.OrderForPaymentDto;
import com.t2m.g2nee.front.order.dto.response.OrderInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;

public interface OrderGetAdaptor {
    /**
     * 상세 주문 목록 조회
     *
     * @param orderId 주문Id
     * @return OrderDetailDto
     */
    List<OrderDetailDto.Response> getOrderDetailListByOrderId(Long orderId);

    /**
     * 주문 정보 조회(주문ID)
     *
     * @param orderId 주문Id
     * @return OrderInfoResponseDto
     */
    OrderInfoDto.Response getOrderById(Long orderId);

    /**
     * 주문 정보 조회(주문 번호)
     *
     * @param orderNumber 주문번호
     * @return OrderInfoResponseDto
     */
    OrderInfoDto.Response getOrderByNumber(String orderNumber);

    /**
     * 회원의 주문 목록 조회
     *
     * @param page       페이지
     * @param customerId 회원Id
     * @return PageResponse<OrderInfoResponseDto
     */
    PageResponse<OrderForPaymentDto> getOrderListForMembers(Long customerId, int page);

    /**
     * 전체 주문 목록 조회(Admin용)
     *
     * @param page 현재 페이지
     * @return PageResponse<OrderListForAdminResponseDto>
     */
    PageResponse<OrderInfoDto.ListResponse> getAllOrderList(int page);

    /**
     * 주문상태별로 정렬한 전체 주문 목록(Admin)
     *
     * @param page       현재 페이지
     * @param orderState 현재 주문 상태
     * @return PageResponse<OrderListForAdminResponseDto>
     */
    PageResponse<OrderInfoDto> getAllOrderListByState(int page, String orderState);

    void changeOrderStatus(Long orderId, OrderInfoDto.OrderState stateRequest);

    String getOrderName(Long orderId);

    Boolean existsOrder(String orderNumber);
}
