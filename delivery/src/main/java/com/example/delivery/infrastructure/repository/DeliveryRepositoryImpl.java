package com.example.delivery.infrastructure.repository;

import static com.example.delivery.domain.model.entity.QDelivery.*;
import static com.example.delivery.domain.model.entity.QDeliveryStatusHistory.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

	@Override
	public Page<Delivery> findAllWithConditions(
		UUID userId, Boolean isDeleted, Boolean isCompleted, Pageable pageable) {

		List<Delivery> resultList = queryFactory
			.selectFrom(delivery)
			.where(
				isDeletedCondition(isDeleted),
				isCompletedCondition(isCompleted),
				userCondition(userId)
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long totalCountResult = queryFactory
			.select(delivery.count())
			.from(delivery)
			.where(
				isDeletedCondition(isDeleted),
				isCompletedCondition(isCompleted),
				userCondition(userId)
			)
			.fetchOne();

		long totalCount = Optional.ofNullable(totalCountResult).orElse(0L);

		return new PageImpl<>(resultList, pageable, totalCount);
	}

	private BooleanExpression isDeletedCondition(Boolean isDeleted) {
		if (isDeleted == null) {
			return null;
		}
		return delivery.isDeleted.eq(isDeleted);
	}

	private BooleanExpression isCompletedCondition(Boolean isCompleted) {
		return isCompleted != null ? delivery.isCompleted.eq(isCompleted) : null;
	}

	private BooleanExpression userCondition(UUID userId) {
		return userId != null ? isReceiverOrDeliveryStaff(userId) : null;
	}

	private BooleanExpression isReceiverOrDeliveryStaff(UUID userId) {
		return delivery.receiverId.eq(userId)
			.or(delivery.hubDeliveryStaffId.eq(userId))
			.or(delivery.companyDeliveryStaffId.eq(userId));
	}
}
