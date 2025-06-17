package okestro.mission1.repository;

import static okestro.mission1.entity.QVm.*;
import static okestro.mission1.entity.QVmTag.*;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.dto.repository.FindFilterVmRepositoryDto;
import okestro.mission1.dto.repository.OrderParams;
import okestro.mission1.entity.Vm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import org.springframework.data.support.PageableExecutionUtils;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VmRepositoryCustomImpl implements VmRepositoryCustom {

    JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Vm> findFilterVm(FindFilterVmRepositoryDto filterDto, Pageable pageable) {
        List<Integer> vmIds = jpaQueryFactory
                .select(vm.vmId)
                .from(vm)
                .where(commonWherePredicate(filterDto))
                .orderBy(generateOrderSpecifier(filterDto.orderParam()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Vm> vms = Collections.emptyList();
        if (!vmIds.isEmpty()) {
            vms = jpaQueryFactory
                    .selectFrom(vm)
                    .leftJoin(vm.vmTags, vmTag).fetchJoin()
                    .where(vm.vmId.in(vmIds))
                    .orderBy(generateOrderSpecifier(filterDto.orderParam()))
                    .fetch();
        }

        long totalCount = jpaQueryFactory
                .select(vm.count())
                .from(vm)
                .where(commonWherePredicate(filterDto))
                .fetchOne();

        return PageableExecutionUtils.getPage(vms, pageable, () -> totalCount);
    }

    private Predicate commonWherePredicate(FindFilterVmRepositoryDto filterDto) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(vm.deleted.eq(false));

        if (filterDto.tagIds() != null && !filterDto.tagIds().isEmpty()) {
            builder.and(vm.vmTags.any().tag.id.in(filterDto.tagIds()));
        }

        return builder;
    }

    private OrderSpecifier<?> generateOrderSpecifier(OrderParams orderParam) {
        return switch (orderParam) {
            case NAME_ASC -> new OrderSpecifier<>(Order.ASC, vm.name);
            case NAME_DESC -> new OrderSpecifier<>(Order.DESC, vm.name);
            case CREATED_AT_ASC -> new OrderSpecifier<>(Order.ASC, vm.createAt);
            case CREATED_AT_DESC -> new OrderSpecifier<>(Order.DESC, vm.createAt);
            case UPDATED_AT_ASC -> new OrderSpecifier<>(Order.ASC, vm.updateAt);
            case UPDATED_AT_DESC -> new OrderSpecifier<>(Order.DESC, vm.updateAt);
        };
    }
}