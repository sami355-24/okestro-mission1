package okestro.mission1.dto.repository;

import java.util.List;

public record FindFilterVmRepository(
        List<Integer> tagIds,
        SortParam sortParam
) {
}