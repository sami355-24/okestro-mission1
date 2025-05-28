package okestro.mission1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.Map;


@RestController
@RequestMapping("/vm")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "VM", description = "가상머신관련 CRUD API입니다.")
public class VmController {

    VmService vmService;
    NetworkService networkService;
    TagService tagService;

    @GetMapping("/{vmId}")
    @Operation(summary = "가상머신 단일 조회", description = "vm id를 기반으로 가상머신을 단일 조회합니다.")
    public ResponseEntity<ResponseTemplate<FindVmResponseDto>> findVm(@PathVariable int vmId) {
        Vm findVm = vmService.findVm(vmId);
        return ResponseEntity.ok(ResponseTemplate.<FindVmResponseDto>builder()
                .metaData(MetaData.ofSuccess())
                .result(new FindVmResponseDto(findVm))
                .build());
    }

    @GetMapping("/check-name")
    @Operation(summary = "가상머신 이름 중복 체크", description = "vm name 기반으로 vm 이름 중복 체크를 수행합니다.")
    public ResponseEntity<ResponseTemplate<Map<String, Boolean>>> checkVmName(@RequestParam(name = "vm-name") String vmName) {
        return ResponseEntity.ok(ResponseTemplate.<Map<String, Boolean>>builder()
                .metaData(MetaData.ofSuccess())
                .result(Map.of("IsDuplicate",vmService.isDuplicate(vmName)))
                .build());
    }

    @GetMapping
    @Operation(summary = "가상머신 목록조회", description = "page, size, tag id로 필터링 기능을 지원하고 이름, 생성 시간, 수정 시간으로 정렬해 조회합니다.")
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
    @Operation(summary = "가상머신 생성", description = "body에 담긴 값을 바탕으로 vm을 생성합니다.")
    @Parameter(name = "memberId", description = "멤버 id값입니다.", required = true, in = ParameterIn.HEADER, example = "1")
    public ResponseEntity<ResponseTemplate<Integer>> createVm(@Parameter(hidden = true) @RequestMember Member member, @RequestBody CreateVmRequestDto vmRequest) {
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
    @Operation(summary = "가상머신 수정", description = "body에 담긴 값을 바탕으로 vm을 수정합니다.")
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
    @Operation(summary = "가상머신 삭제", description = "vm id를 기반으로 vm 삭제를 수행합니다.")
    public ResponseEntity<ResponseTemplate<Void>> deleteVm(@PathVariable int vmId) {
        return ResponseEntity.ok(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofSuccess())
                .result(vmService.deleteVmFrom(vmId))
                .build());
    }

    @PatchMapping("/status")
    @Parameter(name = "memberId", description = "멤버 id값입니다.", required = true, in = ParameterIn.HEADER, example = "1")
    @Operation(summary = "가상머신의 상태를 수정합니다.", description = "가상머신 상태를 랜덤으로 수정합니다. 해당 API 호출하기 이전 socket 연결을 먼저 해주세요. 자세한 내용은 컨플루언스를 확인해주세요.")
    public ResponseEntity<ResponseTemplate<Void>> changeVmStatus(@Parameter(hidden = true) @RequestMember Member member){
        return ResponseEntity.ok(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofSuccess())
                .result(vmService.changeVmsStatus(member))
                .build());
    }
}
