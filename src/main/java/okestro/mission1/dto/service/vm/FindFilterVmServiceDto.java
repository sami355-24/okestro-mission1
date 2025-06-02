package okestro.mission1.dto.service.vm;

import okestro.mission1.dto.controller.request.PageSize;
import okestro.mission1.dto.repository.OrderParams;

import java.util.List;

public record FindFilterVmServiceDto(
        int page,
        PageSize size,
        List<Integer> tagIds,
        OrderParams orderParam
) {
}
