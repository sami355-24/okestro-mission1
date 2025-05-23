package okestro.mission1.dto.controller.response;

import java.util.List;

public record FindFilterVmResponse(
        int vmId,
        String vmName,
        List<String> tags,
        String privateIp
) {
}
