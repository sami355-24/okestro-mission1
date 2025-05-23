package okestro.mission1.dto.service.vm;

import okestro.mission1.dto.controller.request.Order;
import okestro.mission1.dto.controller.request.PageSize;

import java.util.List;

public record FindFilterVmService(
        int page,
        PageSize size,
        List<Integer> tagIds,
        Order nameOrder,
        Order createAtOrder,
        Order updateAtOrder
) {
}
