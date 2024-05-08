package com.t2m.g2nee.front.order.adaptor;

import com.t2m.g2nee.front.order.dto.OrderDetailDto;
import com.t2m.g2nee.front.order.dto.response.OrderInfoResponseDto;
import com.t2m.g2nee.front.order.dto.response.OrderInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;

public interface OrderGetAdaptor {
    /**
     * 상세 주문 목록 조회
     *
     * @param orderId
     * @return OrderDetailDto
     */
    List<OrderDetailDto.Response> getOrderDetailListByOrderId(Long orderId);

    /**
     * 주문 정보 조회(주문ID)
     *
     * @param memberId 회원ID
     * @param orderId  주문Id
     * @return OrderInfoResponseDto
     */
    OrderInfoDto.Response getOrderById(Long memberId, Long orderId);

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
    PageResponse<OrderInfoDto.Response> getAllOrderList(int page);

    /**
     * 주문상태별로 정렬한 전체 주문 목록(Admin)
     *
     * @param page 현재 페이지
     * @param orderState 현재 주문 상태
     * @return PageResponse<OrderListForAdminResponseDto>
     */
    PageResponse<OrderInfoDto> getAllOrderListByState(int page, String orderState);


}
