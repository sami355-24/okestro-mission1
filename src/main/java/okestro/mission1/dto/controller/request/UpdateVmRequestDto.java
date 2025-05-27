package okestro.mission1.dto.controller.request;

import jakarta.validation.constraints.Min;

import java.util.List;

public record UpdateVmRequestDto(
        String name,
        String description,
        @Min(value = 1, message = ERROR_VALUE_IS_HIGHER_THAN_MiN) Integer vCpu,
        @Min(value = 1, message = ERROR_VALUE_IS_HIGHER_THAN_MiN) Integer memory,
        List<Integer> networkIds,
        List<Integer> tagIds
) {
    private static final String ERROR_VALUE_IS_HIGHER_THAN_MiN = "값은 1 이상이어야 합니다.";
}
