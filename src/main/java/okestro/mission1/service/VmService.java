package okestro.mission1.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.dto.request.CreateVmRequest;
import okestro.mission1.entity.Member;
import okestro.mission1.entity.Vm;
import okestro.mission1.exception.custom.DuplicateException;
import okestro.mission1.exception.custom.NotExistException;
import okestro.mission1.repository.VmRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

import static lombok.AccessLevel.PROTECTED;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PROTECTED, makeFinal = true)
public class VmService {

    VmRepository vmRepository;

    public Vm findVm(int vmId) {
        return vmRepository.findById(vmId).orElseThrow(() -> new NotExistException("존재하지 않는 VM id입니다."));
    }

    public boolean isDuplicate(String vmName) {
        return vmRepository.existsByName(vmName);
    }

    public Vm createVmFrom(CreateVmRequest vmRequest, Member requestMember) {
        if(isDuplicate(vmRequest.name()))
            throw new DuplicateException("이미 존재한는 VM이름입니다.");
        return vmRepository.save(new Vm(vmRequest, generateRandomIPv4(), requestMember));
    }

    private String generateRandomIPv4() {
        Random random = new Random();
        return random.nextInt(256) + "." +
                random.nextInt(256) + "." +
                random.nextInt(256) + "." +
                random.nextInt(256);
    }

}
