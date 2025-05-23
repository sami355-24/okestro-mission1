package okestro.mission1.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.dto.controller.request.CreateVmRequest;
import okestro.mission1.dto.controller.request.FindVmFilterRequest;
import okestro.mission1.dto.controller.response.FindFilterVmResponse;
import okestro.mission1.dto.service.vm.FindFilterVmService;
import okestro.mission1.dto.service.vm.UpdateVmService;
import okestro.mission1.entity.Member;
import okestro.mission1.entity.Vm;
import okestro.mission1.exception.custom.DuplicateException;
import okestro.mission1.exception.custom.NotExistException;
import okestro.mission1.repository.VmRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<FindFilterVmResponse> findFilterVms(FindFilterVmService findFilterVmService) {

        return null;
    }

    public boolean isDuplicate(String vmName) {
        return vmRepository.existsByName(vmName);
    }

    public Vm createVmFrom(CreateVmRequest vmRequest, Member requestMember) {
        validateVmName(vmRequest.name());
        return vmRepository.save(new Vm(vmRequest, generateRandomIPv4(), requestMember));
    }

    private String generateRandomIPv4() {
        Random random = new Random();
        return random.nextInt(256) + "." +
                random.nextInt(256) + "." +
                random.nextInt(256) + "." +
                random.nextInt(256);
    }

    private void validateVmName(String vmName) {
        if (vmName != null && isDuplicate(vmName))
            throw new DuplicateException("이미 존재한는 VM이름입니다.");
    }

    @Transactional
    public Void updateVm(UpdateVmService updateVmService) {
        validateVmName(updateVmService.name());
        Vm findVm = vmRepository.findById(updateVmService.vmId()).orElseThrow(() -> new NotExistException("vm이 존재하지 않습니다."));

        findVm.updateVmFrom(updateVmService);
        return null;
    }

    @Transactional
    public Void deleteVmFrom(int vmId) {
        vmRepository.delete(vmRepository.findById(vmId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 VM ID입니다.")));
        return null;
    }
}
