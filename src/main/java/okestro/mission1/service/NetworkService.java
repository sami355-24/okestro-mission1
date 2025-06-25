package okestro.mission1.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.entity.Network;
import okestro.mission1.entity.Vm;
import okestro.mission1.exception.InvalidDataException;
import okestro.mission1.exception.NotExistException;
import okestro.mission1.repository.NetworkRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;
import static okestro.mission1.util.Message.ERROR_NOT_FOUND_NETWORK_IN_DB;

@Service
@RequiredArgsConstructor(access = PROTECTED)
@FieldDefaults(level = PROTECTED, makeFinal = true)
public class NetworkService {

    NetworkRepository networkRepository;

    public void validateNetworkId(List<Integer> networkIds, List<Network> networkListWithVm) {
        List<Network> validNetworkList = new ArrayList<>(networkListWithVm);
        validNetworkList.addAll(networkRepository.findByVmIsNull());
        List<Integer> validNetworkIdList = validNetworkList.stream().map(network -> network.getNetworkId()).toList();
        if(!validNetworkIdList.containsAll(networkIds)) throw new InvalidDataException("네트워크 Id가 올바르지 않습니다.");
    }

    public void validateNetworkId(List<Integer> networkIds) {
        if(networkIds == null) return;
        int matchedIdCount = networkRepository.countAllByNetworkIdInAndVmIsNull(networkIds);
        if(matchedIdCount == networkIds.size()) return;
        throw new NotExistException(ERROR_NOT_FOUND_NETWORK_IN_DB.getMessage());
    }

    public List<Network> findAllByNetworkIds(List<Integer> networkIds) {
        if(networkIds == null) return null;
        return networkRepository.findAllById(networkIds);
    }

    public List<Network> findByVmIdIsNull() {
        return networkRepository.findByVmIsNull();
    }
}
