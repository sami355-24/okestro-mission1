package okestro.mission1.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.dto.controller.request.CreateVmRequestDto;
import okestro.mission1.dto.controller.response.FindFilterVmResponseDto;
import okestro.mission1.dto.repository.FindFilterVmRepositoryDto;
import okestro.mission1.dto.service.vm.FindFilterVmServiceDto;
import okestro.mission1.dto.service.vm.UpdateVmServiceDto;
import okestro.mission1.entity.Member;
import okestro.mission1.entity.Vm;
import okestro.mission1.exception.DuplicateException;
import okestro.mission1.exception.NotExistException;
import okestro.mission1.repository.VmRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static lombok.AccessLevel.PROTECTED;
import static okestro.mission1.util.Message.ERROR_DUPLICATE_VM_NAME;
import static okestro.mission1.util.Message.ERROR_NOT_FOUND_VM_IN_DB;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PROTECTED, makeFinal = true)
public class VmService {

    VmRepository vmRepository;

    public Vm findVm(int vmId) {
        return vmRepository.findById(vmId).orElseThrow(() -> new NotExistException(ERROR_NOT_FOUND_VM_IN_DB.getMessage()));
    }

    public FindFilterVmResponseDto findFilterVms(FindFilterVmServiceDto findFilterVmServiceDto) {
        Pageable pageable = PageRequest.of(findFilterVmServiceDto.page()-1, findFilterVmServiceDto.size().getValue());
        FindFilterVmRepositoryDto findFilterVmRepositoryDto = new FindFilterVmRepositoryDto(findFilterVmServiceDto.tagIds(), findFilterVmServiceDto.orderParam());
        Page<Vm> filterVm = vmRepository.findFilterVm(findFilterVmRepositoryDto, pageable);
        return new FindFilterVmResponseDto(filterVm);
    }

    public boolean isDuplicate(String vmName) {
        return vmRepository.existsByName(vmName);
    }

    public Vm createVmFrom(CreateVmRequestDto vmRequest, Member requestMember) {
        validateVmName(vmRequest.name());
        return vmRepository.save(new Vm(vmRequest, generateRandomIPv4(), requestMember));
    }

    private static String generateRandomIPv4() {
        Random random = new Random();
        return random.nextInt(256) + "." +
                random.nextInt(256) + "." +
                random.nextInt(256) + "." +
                random.nextInt(256);
    }

    private void validateVmName(String vmName) {
        if (vmName != null && isDuplicate(vmName))
            throw new DuplicateException(ERROR_DUPLICATE_VM_NAME.getMessage());
    }

    @Transactional
    public void updateVm(UpdateVmServiceDto updateVmServiceDto) {
        validateVmName(updateVmServiceDto.name());
        Vm findVm = vmRepository.findById(updateVmServiceDto.vmId()).orElseThrow(() -> new NotExistException(ERROR_NOT_FOUND_VM_IN_DB.getMessage()));

        findVm.updateVmFrom(updateVmServiceDto);
    }

    @Transactional
    public void deleteVmFrom(int vmId) {
        Vm findVm = vmRepository.findById(vmId).orElseThrow(() -> new NotExistException(ERROR_NOT_FOUND_VM_IN_DB.getMessage()));
        findVm.detachNetwork();
        vmRepository.delete(findVm);
    }

    @Transactional
    public void changeVmsStatus(Member member) {
        vmRepository.findAllByMember(member).forEach(Vm::updateVmStatus);
    }
}
