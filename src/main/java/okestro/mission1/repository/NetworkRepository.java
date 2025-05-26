package okestro.mission1.repository;

import okestro.mission1.entity.Network;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NetworkRepository extends JpaRepository<Network, Integer> {

    @Query(value = """
            SELECT COUNT(n)
            FROM Network n
            WHERE n.networkId IN :networkIds
            AND n.vm IS NULL
            """)
    int countMatchingIdsWithoutVm(@Param("networkIds") List<Integer> networkIds);

}
