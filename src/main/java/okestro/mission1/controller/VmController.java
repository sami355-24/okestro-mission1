package okestro.mission1.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.dto.response.FindVmResponse;
import okestro.mission1.dto.response.template.MetaData;
import okestro.mission1.dto.response.template.ResponseTemplate;
import okestro.mission1.entity.Vm;
import okestro.mission1.service.VmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vm")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VmController {

    VmService vmService;

    @GetMapping("/{vmId}")
    public ResponseEntity<ResponseTemplate<FindVmResponse>> findVm(@PathVariable int vmId) {
        Vm findVm = vmService.findVm(vmId);
        return ResponseEntity.ok(ResponseTemplate.<FindVmResponse>builder()
                .metaData(MetaData.ofSuccess())
                .result(new FindVmResponse(findVm))
                .build());
    }
}
