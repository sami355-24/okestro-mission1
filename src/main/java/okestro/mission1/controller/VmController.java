package okestro.mission1.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.annotation.customannotaion.RequestMember;
import okestro.mission1.dto.request.CreateVmRequest;
import okestro.mission1.dto.response.FindVmResponse;
import okestro.mission1.dto.response.template.MetaData;
import okestro.mission1.dto.response.template.ResponseTemplate;
import okestro.mission1.entity.Member;
import okestro.mission1.entity.Vm;
import okestro.mission1.service.NetworkService;
import okestro.mission1.service.TagService;
import okestro.mission1.service.VmService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


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
        vmFrom.setNetworks(networkService.findAllByNetworkIds(vmRequest.networkIds()));
        vmFrom.setTags(tagService.findAllByTagIds(vmRequest.tagIds()));
        return ResponseEntity.ok(ResponseTemplate.<Integer>builder()
                .metaData(MetaData.ofSuccess())
                .result(vmFrom.getVmId())
                .build());
    }
}
