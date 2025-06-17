package okestro.mission1.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.dto.controller.response.FindNetworkResponse;
import okestro.mission1.dto.controller.response.FindTagResponseDto;
import okestro.mission1.dto.controller.response.template.MetaData;
import okestro.mission1.dto.controller.response.template.ResponseTemplate;
import okestro.mission1.entity.Network;
import okestro.mission1.service.NetworkService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/networks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
@Tag(name = "Network", description = "네트워크관련 API입니다.")
public class NetworkController {

    NetworkService networkService;

    @GetMapping
    ResponseEntity<ResponseTemplate<List<FindNetworkResponse>>> findUsableNetworks() {
        return ResponseEntity.ok(ResponseTemplate.<List<FindNetworkResponse>>builder()
                .metaData(MetaData.ofSuccess())
                .result(networkService.findByVmIdIsNull().stream().map(network -> new FindNetworkResponse(network.getNetworkId(), network.getOpenIp(), network.getOpenPort())).toList())
                .build());
    }

}
