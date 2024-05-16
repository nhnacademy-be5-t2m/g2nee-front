package com.t2m.g2nee.front.order.service;

import com.t2m.g2nee.front.order.adaptor.OrderGetAdaptor;
import com.t2m.g2nee.front.order.dto.OrderDetailDto;
import com.t2m.g2nee.front.order.dto.response.OrderForPaymentDto;
import com.t2m.g2nee.front.order.dto.response.OrderInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 주문 조회 서비스
 *
 * @author : 박재희
 * @since: 1.0
 */
@Service
@Transactional
public class OrderGetService {
    private final OrderGetAdaptor orderGetAdaptor;

    public OrderGetService(OrderGetAdaptor orderGetAdaptor) {
        this.orderGetAdaptor = orderGetAdaptor;
    }

    /**
     * 주문 상세 조회(주문Id)
     *
     * @param orderId 주문Id
     * @return List<OrderDetailDto.Response>
     */
    public List<OrderDetailDto.Response> getOrderDetailListByOrderId(Long orderId) {
        return orderGetAdaptor.getOrderDetailListByOrderId(orderId);
    }

    /**
     * 주문 정보 조회(주문Id)
     *
     * @param orderId 주문Id
     * @return OrderInfoDto.ListResponse
     */
    public OrderInfoDto.Response getOrderById(Long orderId) {

        return orderGetAdaptor.getOrderById(orderId);
    }

    /**
     * 주문 정보 조회(주문 번호)
     *
     * @param orderNumber 주문번호
     * @return OrderInfoResponseDto
     */
    public OrderInfoDto.Response getOrderByNumber(String orderNumber) {
        return orderGetAdaptor.getOrderByNumber(orderNumber);
    }

    /**
     * 회원의 주문 목록 조회
     *
     * @param page     페이지
     * @param memberId 회원Id
     * @return PageResponse<OrderInfoResponseDto
     */
    public PageResponse<OrderForPaymentDto> getOrderListForMembers(Long memberId, int page) {
        return orderGetAdaptor.getOrderListForMembers(memberId, page);
    }

    /**
     * 전체 주문 목록 조회(Admin용)
     *
     * @param page 현재 페이지
     * @return PageResponse<OrderListForAdminResponseDto>
     */
    public PageResponse<OrderInfoDto.ListResponse> getAllOrderList(int page) {
        return orderGetAdaptor.getAllOrderList(page);
    }

    /**
     * 주문상태별로 정렬한 전체 주문 목록(Admin)
     *
     * @param page       현재 페이지
     * @param orderState 현재 주문 상태
     * @return PageResponse<OrderListForAdminResponseDto>
     */
    PageResponse<OrderInfoDto> getAllOrderListByState(int page, String orderState) {
        return null;
    }

    public void changeOrderStatus(Long orderId, OrderInfoDto.OrderStateRequest stateRequest) {
        orderGetAdaptor.changeOrderStatus(orderId, stateRequest);
    }

    public String getOrderName(Long orderId) {
        return orderGetAdaptor.getOrderName(orderId);
    }
    public Boolean existsOrder(String orderNumber) {
        return orderGetAdaptor.existsOrder(orderNumber);
    }

}
