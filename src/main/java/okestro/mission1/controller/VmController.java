package okestro.mission1.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.annotation.customannotaion.RequestMember;
import okestro.mission1.dto.controller.request.CreateVmRequestDto;
import okestro.mission1.dto.controller.request.PageSize;
import okestro.mission1.dto.controller.request.UpdateVmRequestDto;
import okestro.mission1.dto.controller.response.FindFilterVmResponseDto;
import okestro.mission1.dto.controller.response.FindVmResponseDto;
import okestro.mission1.dto.controller.response.template.MetaData;
import okestro.mission1.dto.controller.response.template.ResponseTemplate;
import okestro.mission1.dto.repository.SortParam;
import okestro.mission1.dto.service.vm.FindFilterVmServiceDto;
import okestro.mission1.dto.service.vm.UpdateVmServiceDto;
import okestro.mission1.entity.Member;
import okestro.mission1.entity.Vm;
import okestro.mission1.service.NetworkService;
import okestro.mission1.service.TagService;
import okestro.mission1.service.VmService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/vm")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VmController {

    VmService vmService;
    NetworkService networkService;
    TagService tagService;

    @GetMapping("/{vmId}")
    public ResponseEntity<ResponseTemplate<FindVmResponseDto>> findVm(@PathVariable int vmId) {
        Vm findVm = vmService.findVm(vmId);
        return ResponseEntity.ok(ResponseTemplate.<FindVmResponseDto>builder()
                .metaData(MetaData.ofSuccess())
                .result(new FindVmResponseDto(findVm))
                .build());
    }

    @GetMapping("/check-name")
    public ResponseEntity<ResponseTemplate<Boolean>> checkVmName(@RequestParam(name = "vm-name") String vmName) {
        return ResponseEntity.ok(ResponseTemplate.<Boolean>builder()
                .metaData(MetaData.ofSuccess())
                .result(vmService.isDuplicate(vmName))
                .build());
    }

    @GetMapping
    public ResponseEntity<ResponseTemplate<FindFilterVmResponseDto>> findFilterVms(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int pageSize,
            @RequestParam(name = "tagIds", required = false) List<Integer> tagIds,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "create-at", required = false, defaultValue = "create-at-desc") String createAt,
            @RequestParam(name = "update-at", required = false) String updateAt
            ) {
        tagService.validateTagFrom(tagIds);
        FindFilterVmResponseDto filterVmResponses = vmService.findFilterVms(
                new FindFilterVmServiceDto(page, PageSize.convertToPageSize(pageSize), tagIds, SortParam.from(name), SortParam.from(createAt), SortParam.from(updateAt))
        );
        return ResponseEntity.ok(ResponseTemplate.<FindFilterVmResponseDto>builder()
                .metaData(MetaData.ofSuccess())
                .result(filterVmResponses)
                .build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseTemplate<Integer>> createVm(@RequestMember Member member, @RequestBody CreateVmRequestDto vmRequest) {
        networkService.validateNetworkId(vmRequest.networkIds());
        tagService.validateTagFrom(vmRequest.tagIds());

        Vm vmFrom = vmService.createVmFrom(vmRequest, member);
        vmFrom.setNetworksFrom(networkService.findAllByNetworkIds(vmRequest.networkIds()));
        vmFrom.setTagsFrom(tagService.findAllByTagIds(vmRequest.tagIds()));
        return ResponseEntity.ok(ResponseTemplate.<Integer>builder()
                .metaData(MetaData.ofSuccess())
                .result(vmFrom.getVmId())
                .build());
    }

    @PatchMapping("/{vmId}")
    public ResponseEntity<ResponseTemplate<Void>> updateVm(@PathVariable int vmId, @RequestBody @Valid UpdateVmRequestDto updateVmRequestDto) {
        networkService.validateNetworkId(updateVmRequestDto.networkIds());
        tagService.validateTagFrom(updateVmRequestDto.tagIds());

        return ResponseEntity.ok(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofSuccess())
                .result(vmService.updateVm(
                        new UpdateVmServiceDto(
                                vmId,
                                tagService.findAllByTagIds(updateVmRequestDto.tagIds()),
                                networkService.findAllByNetworkIds(updateVmRequestDto.networkIds()),
                                updateVmRequestDto)
                ))
                .build());
    }

    @DeleteMapping("/{vmId}")
    public ResponseEntity<ResponseTemplate<Void>> deleteVm(@PathVariable int vmId) {
        return ResponseEntity.ok(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofSuccess())
                .result(vmService.deleteVmFrom(vmId))
                .build());
    }

    @PatchMapping("/status")
    public ResponseEntity<ResponseTemplate<Void>> changeVmStatus(@RequestMember Member member){
        return ResponseEntity.ok(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofSuccess())
                .result(vmService.changeVmsStatus(member))
                .build());
    }
}
