package okestro.mission1.dto.controller.request;

import jakarta.validation.constraints.Min;

import java.util.List;

public record UpdateVmRequestDto(
        String name,
        String description,
        @Min(1) Integer vCpu,
        @Min(1) Integer memory,
        List<Integer> networkIds,
        List<Integer> tagIds
) {
}
