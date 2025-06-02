package okestro.mission1.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import okestro.mission1.entity.Member;
import okestro.mission1.entity.Vm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VmRepository extends JpaRepository<Vm, Integer>, VmRepositoryCustom {

    Optional<Vm> findById(Integer integer);

    boolean existsByName(String title);

    @Query(value = "SELECT * FROM vm WHERE deleted=true;", nativeQuery = true)
    List<Vm> findDeletedVmsWithNativeQuery();

    List<Vm> findAllByMember(Member member);
}
