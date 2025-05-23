package okestro.mission1.repository;

import okestro.mission1.dto.controller.repository.FindVmFilterDto;
import okestro.mission1.dto.controller.response.FindFilterVmResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VmRepositoryCustom {
    List<FindFilterVmResponse> findFilterVm(FindVmFilterDto filterDto, Pageable page);
}
