package okestro.mission1.dto.controller.request;

import java.util.List;
import java.util.Map;

public record FindVmFilterRequestDto(
        int page,
        PageSize pageSize,
        List<Integer> tagIds,
        Map<String, String> sortParams
) {
}