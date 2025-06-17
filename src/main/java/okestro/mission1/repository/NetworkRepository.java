package okestro.mission1.repository;

import okestro.mission1.entity.Network;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NetworkRepository extends JpaRepository<Network, Integer> {

    int countAllByNetworkIdInAndVmIsNull(List<Integer> networkIds);

    List<Network> findByVmIsNull();
}
