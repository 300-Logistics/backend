package com.example.order.application.service;

import com.example.order.application.dto.OrderDto;
import com.example.order.domain.model.Order;
import com.example.order.domain.repository.OrderRepository;
import com.example.order.infrastructure.client.DeliveryClient;
import com.example.order.libs.exception.CustomException;
import com.example.order.libs.exception.ErrorCode;
import com.example.order.presentation.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryClient deliveryClient;
//    private final CompanyClient companyClient;
//    private final DeliveryClient deliveryClient;

    public UUID create(OrderRequest request) {
        Order order = Order.builder()
                .productId(request.getProductId())
                .count(request.getCount())
                .customerId(request.getCustomerId())
                .supplierId(request.getSupplierId())
                .requests(request.getRequests())
                .build();

        Order savedOrder = orderRepository.save(order);

        // todo: 주문 생성 시 배달도 생성(배달 서버에서 오류가 나서 아직 적용이 안됨)
        /*
        CompanyResponse customerCompany = companyClient.findCompanyById(request.getCustomerId());
        CompanyResponse supplierCompany = companyClient.findCompanyById(request.getSupplierId());

        DeliveryResponse deliveryResponse = deliveryClient.createDelivery(new DeliveryRequest(
                savedOrder.getOrderId(),
                supplierCompany.getHubId(),
                customerCompany.getHubId(),
                UUID.randomUUID(),  // todo: 수령 업체 담당자 UUID로 변경 (로그인한 사용자)
                "test@test.com",    // todo: 수령 업체 담당자 slack email로 변경 (로그인한 사용자)
                customerCompany.getAddress()));

        savedOrder.updateDelivery(deliveryResponse.deliveryId());
        */

        return savedOrder.getOrderId();
    }

    public OrderDto update(UUID orderId, OrderRequest request) {
        Order order = findOrder(orderId);
        order.update(request.getCustomerId(), request.getSupplierId(), request.getProductId(), request.getCount(), request.getRequests(), request.getDeliveryId());

        return toOrderDto(order);
    }

    public void delete(UUID orderId) {
        Order order = findOrder(orderId);

        // todo: 배송도 함께 삭제
        /*
        ResponseEntity<Void> result = deliveryClient.cancelDelivery(order.getDeliveryId());
        if (result.getStatusCode().isError()) {
            throw new CustomException(ErrorCode.DELIVERY_SERVER_ERROR);
        }
        */

        order.setDeleted();
    }

    public OrderDto find(UUID orderId) {
        return toOrderDto(findOrder(orderId));
    }

    public Page<OrderDto> findAll(int page, int size, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Order> orderList = orderRepository.findAllByDeletedAtNull(pageable);
        return orderList.map(this::toOrderDto);
    }

    private Order findOrder(UUID orderId) {
        return orderRepository.findByOrderIdAndDeletedAtNull(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
    }

    private OrderDto toOrderDto(Order order) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .customerId(order.getCustomerId())
                .supplierId(order.getSupplierId())
                .productId(order.getProductId())
                .count(order.getCount())
                .deliveryId(order.getDeliveryId())
                .requests(order.getRequests())
                .build();
    }

}
