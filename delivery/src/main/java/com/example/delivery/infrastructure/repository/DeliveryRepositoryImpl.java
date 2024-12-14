package com.example.delivery.infrastructure.repository;

import static com.example.delivery.domain.model.entity.QDelivery.*;
import static com.example.delivery.domain.model.entity.QDeliveryStatusHistory.*;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.delivery.domain.model.entity.Delivery;
import com.example.delivery.domain.repository.DeliveryRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class DeliveryRepositoryImpl implements DeliveryRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<Delivery> findByIdWithConditions(UUID deliveryId, UUID userId) {

		Delivery result = queryFactory
				.selectFrom(delivery)
				.leftJoin(delivery.statusHistoryList, deliveryStatusHistory)
				.fetchJoin()
				.where(
					delivery.id.eq(deliveryId),
					(delivery.isDeleted.isFalse()),
					isReceiverOrDeliveryStaff(userId)
				)
				.fetchOne();

		return Optional.ofNullable(result);
	}

	private BooleanExpression isReceiverOrDeliveryStaff(UUID userId) {
		return delivery.receiverId.eq(userId)
			.or(delivery.hubDeliveryStaffId.eq(userId))
			.or(delivery.companyDeliveryStaffId.eq(userId));
	}

}
