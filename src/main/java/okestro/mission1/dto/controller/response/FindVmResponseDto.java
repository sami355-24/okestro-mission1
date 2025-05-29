package okestro.mission1.dto.controller.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import okestro.mission1.entity.Vm;
import okestro.mission1.entity.VmStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public record FindVmResponseDto(
        int vmId,
        VmStatus vmStatus,
        String description,
        String vmName,
        @Schema(description = "cpu 코어수입니다.")
        int vCpu,
        @Schema(description = "memory 크기입니다. 단위는 GB입니다.")
        int memory,
        @Schema(description = "cpu 사용량 입니다. 단위는 %입니다.")
        int cpuUsage,
        @Schema(description = "memory 사용량 입니다. 단위는 %입니다.")
        int memoryUsage,
        @Schema(description = "storage 크기입니다. 단위는 GB입니다.")
        int storage,
        @JsonProperty("networks")
        List<FindVmResponseNetworkDTO> findVmResponseNetworkDTOList,
        LocalDateTime createAt,
        LocalDateTime updateAt,
        @Schema(description = "가상머신이 가지는 private ip입니다.")
        String privateIp
) {
    private final static Random random = new Random();

    public FindVmResponseDto(Vm vm) {
        this(
                vm.getVmId(),
                vm.getVmStatus(),
                vm.getDescription(),
                vm.getName(),
                vm.getVCpu(),
                vm.getMemory(),
                generateRandomValue(),
                generateRandomValue(),
                vm.getStorage(),
                generateNetworkList(vm.getNetworks()),
                vm.getCreateAt(),
                vm.getUpdateAt(),
                vm.getPrivateIp()
        );
    }

    private static int generateRandomValue() {
        return random.nextInt(80) + 1;
    }

    private static List<FindVmResponseNetworkDTO> generateNetworkList(List<okestro.mission1.entity.Network> originNetworks) {
        return originNetworks
                .stream()
                .map(origin -> new FindVmResponseNetworkDTO(origin.getOpenIp(), origin.getOpenPort())).toList();
    }

    private record FindVmResponseNetworkDTO(
            String openIp,
            int openPort
    ) {
    }
}
