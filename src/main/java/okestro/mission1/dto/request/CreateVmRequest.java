package okestro.mission1.dto.request;

import java.util.List;

public record CreateVmRequest(
        String name,
        String description,
        int vCpu,
        int memory,
        int storage,
        List<Integer> networkIds,
        List<Integer> tagIds
) {
}
