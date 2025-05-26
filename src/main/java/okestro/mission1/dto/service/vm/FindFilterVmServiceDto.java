package okestro.mission1.dto.service.vm;

import okestro.mission1.dto.controller.request.PageSize;
import okestro.mission1.dto.repository.SortParam;

import java.util.List;

public record FindFilterVmServiceDto(
        int page,
        PageSize size,
        List<Integer> tagIds,
        SortParam name,
        SortParam createAt,
        SortParam updateAt
) {

    public SortParam getSortParam() {
        if (name != null) return name;
        if (createAt != null) return createAt;
        if (updateAt != null) return updateAt;
        return null;
    }
}
