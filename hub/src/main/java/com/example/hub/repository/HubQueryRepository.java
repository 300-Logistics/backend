package com.example.hub.repository;

import com.example.hub.domain.model.entity.QHub;
import com.example.hub.dto.response.HubResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HubQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QHub hub = QHub.hub;

    public Page<HubResponse> searchHubs(Pageable pageable, String keyword) {
        BooleanExpression keywordCondition = keyword != null && !keyword.isEmpty() ?
            hub.name.containsIgnoreCase(keyword).or(hub.address.containsIgnoreCase(keyword)) : null;

        List<HubResponse> hubResponseList = jpaQueryFactory
            .select(
                Projections.constructor(
                    HubResponse.class,
                    hub.id,
                    hub.name,
                    hub.address,
                    hub.latitude,
                    hub.longitude
                ))
            .from(hub)
            .where(
                keywordCondition,
                hub.isDeleted.isFalse()
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = jpaQueryFactory
            .select(hub.count())
            .from(hub)
            .where(
                keywordCondition,
                hub.isDeleted.isFalse()
            )
            .fetchOne();

        return new PageImpl<>(hubResponseList, pageable, total != null ? total : 0L);
    }
}
