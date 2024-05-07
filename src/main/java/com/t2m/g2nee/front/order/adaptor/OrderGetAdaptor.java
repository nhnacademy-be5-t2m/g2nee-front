package com.t2m.g2nee.front.order.adaptor;

import com.t2m.g2nee.front.order.dto.response.OrderInfoResponseDto;
import com.t2m.g2nee.front.order.dto.response.OrderListForAdminResponseDto;
import com.t2m.g2nee.front.utils.PageResponse;

public interface OrderGetAdaptor {

    /**
     * 주문 정보 조회(주문ID)
     *
     * @param customerId 회원ID
     * @param orderId 주문Id
     * @return OrderInfoResponseDto
     */
    OrderInfoResponseDto getOrderById(Long customerId, Long orderId);

    /**
     * 주문 정보 조회(주문 번호)
     *
     * @param customerId 고객Id
     * @param orderNumber 주문번호
     * @return OrderInfoResponseDto
     */
    OrderInfoResponseDto getOrderByNumber(Long customerId, String orderNumber);

    /**
     * 회원의 주문 목록 조회
     *
     * @param page
     * @param customerId
     * @return PageResponse<OrderInfoResponseDto
     */
    PageResponse<OrderInfoResponseDto> getOrderListForMembers(int page, Long customerId);

    /**
     * 전체 주문 목록 조회(Admin용)
     *
     * @param page 현재 페이지
     * @return PageResponse<OrderListForAdminResponseDto>
     */
    PageResponse<OrderListForAdminResponseDto> getAllOrderList(int page);

    /**
     * 주문상태별로 정렬한 전체 주문 목록(Admin)
     *
     * @param page 현재 페이지
     * @param orderState 현재 주문 상태
     * @return PageResponse<OrderListForAdminResponseDto>
     */
    PageResponse<OrderListForAdminResponseDto> getAllOrderListByState(int page, String orderState);


}
