package okestro.mission1.dto.controller.repository;

import okestro.mission1.dto.controller.request.PageSize;

import java.util.List;
import java.util.Map;

public record FindVmFilterDto(
        List<Integer> tagIds,
        Map<String, String> sortParams
) {
}