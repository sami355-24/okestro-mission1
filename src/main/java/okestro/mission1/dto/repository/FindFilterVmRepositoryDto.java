package okestro.mission1.dto.repository;

import java.util.List;

public record FindFilterVmRepositoryDto(
        List<Integer> tagIds,
        SortParam sortParam
) {
}