package okestro.mission1.repository;

import okestro.mission1.entity.Vm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VmRepository extends JpaRepository<Vm, Integer> {

    Optional<Vm> findById(Integer integer);

    Optional<Vm> findByName(String vmId);

    boolean existsByName(String title);

    @Query("SELECT v FROM Vm v WHERE v.deleted = true")
    List<Vm> findByDeletedTrue();
}
