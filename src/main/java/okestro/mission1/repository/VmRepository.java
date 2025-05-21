package okestro.mission1.repository;

import okestro.mission1.entity.Vm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VmRepository extends JpaRepository<Vm, Integer> {

    Optional<Vm> findByTitle(String vmId);
}
