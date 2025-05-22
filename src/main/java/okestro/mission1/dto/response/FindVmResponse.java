package okestro.mission1.dto.response;


import okestro.mission1.entity.Vm;
import okestro.mission1.entity.VmStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public record FindVmResponse(
        int vmId,
        VmStatus vmStatus,
        String description,
        String vmTitle,
        int vCpu,
        int memory,
        int cpuUsage,
        int memoryUsage,
        int storage,
        List<FindVmResponseNetworkDTO> findVmResponseNetworkDTOList,
        LocalDateTime createAt,
        LocalDateTime updateAt,
        String privateIp
) {
    private final static Random random = new Random();

    public FindVmResponse(Vm vm) {
        this(
                vm.getVmId(),
                vm.getVmStatus(),
                vm.getDescription(),
                vm.getTitle(),
                vm.getVCpu(),
                vm.getMemory(),
                generateRandomValue(),
                generateRandomValue(),
                vm.getStorage(),
                generateNetworkList(vm.getNetwork()),
                vm.getCreateAt(),
                vm.getUpdateAt(),
                vm.getPrivateIp()
        );
    }

    private static int generateRandomValue() {
        return random.nextInt(80) + 1; // 0~79 사이의 값 + 1 = 1~80
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
