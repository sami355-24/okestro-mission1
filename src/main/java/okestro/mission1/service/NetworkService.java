package okestro.mission1.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.entity.Network;
import okestro.mission1.exception.custom.NotExistException;
import okestro.mission1.repository.NetworkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Service
@RequiredArgsConstructor(access = PROTECTED)
@FieldDefaults(level = PROTECTED, makeFinal = true)
public class NetworkService {

    NetworkRepository networkRepository;

    public void validateNetworkId(List<Integer> networkIds) {
        int matchedIdCount = networkRepository.countMatchingIdsWithoutVm(networkIds);
        if(matchedIdCount == networkIds.size()) return;
        throw new NotExistException("존재하지 않는 network id입니다");
    }

    public List<Network> findAllByNetworkIds(List<Integer> networkIds) {
        return networkRepository.findAllById(networkIds);
    }
}
