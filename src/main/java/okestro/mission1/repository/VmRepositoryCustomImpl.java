package okestro.mission1.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.dto.controller.repository.FindVmFilterDto;
import okestro.mission1.dto.controller.response.FindFilterVmResponse;
import okestro.mission1.dto.controller.response.QFindFilterVmResponse;
import okestro.mission1.entity.QTag;
import okestro.mission1.entity.QVm;
import okestro.mission1.entity.QVmTag;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static okestro.mission1.entity.QTag.*;
import static okestro.mission1.entity.QVm.*;
import static okestro.mission1.entity.QVmTag.*;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VmRepositoryCustomImpl implements VmRepositoryCustom {

    JPAQueryFactory queryFactory;

    @Override
    public List<FindFilterVmResponse> findFilterVm(FindVmFilterDto filterDto, Pageable page) {

    }
}
