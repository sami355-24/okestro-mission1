package okestro.mission1.repository;

import static okestro.mission1.entity.QVm.*;
import static okestro.mission1.entity.QVmTag.*;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.dto.repository.FindFilterVmRepository;
import okestro.mission1.dto.repository.SortParam;
import okestro.mission1.entity.Vm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import org.springframework.data.support.PageableExecutionUtils;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VmRepositoryCustomImpl implements VmRepositoryCustom {

    JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Vm> findFilterVm(FindFilterVmRepository filterDto, Pageable pageable) {
        List<Vm> vms = jpaQueryFactory
                .selectFrom(vm)
                .where(generateWherePredicate(filterDto))
                .innerJoin(vm.vmTags, vmTag).fetchJoin()
                .orderBy(generateOrderSpecifier(filterDto.sortParam()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = jpaQueryFactory
                .select(vm.count())
                .from(vm)
                .where(generateWherePredicate(filterDto))
                .fetchOne();

        return PageableExecutionUtils.getPage(vms, pageable, () -> totalCount);
    }

    private Predicate generateWherePredicate(FindFilterVmRepository filterDto) {
        return filterDto.tagIds() != null ? vm.vmTags.any().tag.id.in(filterDto.tagIds()) : null;
    }

    private OrderSpecifier<?> generateOrderSpecifier(SortParam sortParam) {
        return switch (sortParam) {
            case NAME_ASC -> new OrderSpecifier<>(Order.ASC, vm.name);
            case NAME_DESC -> new OrderSpecifier<>(Order.DESC, vm.name);
            case CREATED_AT_ASC -> new OrderSpecifier<>(Order.ASC, vm.createAt);
            case CREATED_AT_DESC -> new OrderSpecifier<>(Order.DESC, vm.createAt);
            case UPDATED_AT_ASC -> new OrderSpecifier<>(Order.ASC, vm.updateAt);
            case UPDATED_AT_DESC -> new OrderSpecifier<>(Order.DESC, vm.updateAt);
        };
    }
}
