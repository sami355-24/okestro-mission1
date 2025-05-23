package okestro.mission1.repository;

import okestro.mission1.dto.repository.FindFilterVmRepository;
import okestro.mission1.entity.Vm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface VmRepositoryCustom {
    Page<Vm> findFilterVm(FindFilterVmRepository filterDto, Pageable pageable);
}
