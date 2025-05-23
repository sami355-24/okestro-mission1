package okestro.mission1.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.annotation.customannotaion.RequestMember;
import okestro.mission1.dto.controller.request.CreateVmRequest;
import okestro.mission1.dto.controller.request.UpdateVmRequest;
import okestro.mission1.dto.controller.response.FindVmResponse;
import okestro.mission1.dto.controller.response.template.MetaData;
import okestro.mission1.dto.controller.response.template.ResponseTemplate;
import okestro.mission1.dto.service.vm.VmServiceUpdateDto;
import okestro.mission1.entity.Member;
import okestro.mission1.entity.Network;
import okestro.mission1.entity.Tag;
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
    public ResponseEntity<ResponseTemplate<FindVmResponse>> findVm(@PathVariable int vmId) {
        Vm findVm = vmService.findVm(vmId);
        return ResponseEntity.ok(ResponseTemplate.<FindVmResponse>builder()
                .metaData(MetaData.ofSuccess())
                .result(new FindVmResponse(findVm))
                .build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseTemplate<Integer>> createVm(@RequestMember Member member, @RequestBody CreateVmRequest vmRequest) {
        networkService.validateNetworkId(vmRequest.networkIds());
        tagService.validateTagIds(vmRequest.tagIds());

        Vm vmFrom = vmService.createVmFrom(vmRequest, member);
        vmFrom.setNetworksFrom(networkService.findAllByNetworkIds(vmRequest.networkIds()));
        vmFrom.setTagsFrom(tagService.findAllByTagIds(vmRequest.tagIds()));
        return ResponseEntity.ok(ResponseTemplate.<Integer>builder()
                .metaData(MetaData.ofSuccess())
                .result(vmFrom.getVmId())
                .build());
    }

    @PatchMapping("/{vmId}")
    public ResponseEntity<ResponseTemplate<Void>> updateVm(@PathVariable int vmId, @RequestBody @Valid UpdateVmRequest updateVmRequest) {
        networkService.validateNetworkId(updateVmRequest.networkIds());
        tagService.validateTagIds(updateVmRequest.tagIds());

        return ResponseEntity.ok(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofSuccess())
                .result(vmService.updateVm(
                        new VmServiceUpdateDto(
                                vmId,
                                tagService.findAllByTagIds(updateVmRequest.tagIds()),
                                networkService.findAllByNetworkIds(updateVmRequest.networkIds()),
                                updateVmRequest)
                ))
                .build());
    }

    @DeleteMapping("/{vmId}")
    public ResponseEntity<ResponseTemplate<Void>> deleteVm(@PathVariable int vmId) {
        vmService.deleteVmFrom(vmId);
        return ResponseEntity.ok(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofSuccess())
                .result(null)
                .build());
    }
}
