package okestro.mission1.dto.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

import java.util.List;

public record UpdateVmRequestDto(
        String name,
        String description,
        @Schema(description = "cpu 코어수입니다.")
        @Min(value = 1, message = ERROR_VALUE_IS_HIGHER_THAN_MiN) Integer vCpu,
        @Schema(description = "memory 크기입니다. 단위는 GB입니다.")
        @Min(value = 1, message = ERROR_VALUE_IS_HIGHER_THAN_MiN) Integer memory,
        List<Integer> networkIds,
        List<Integer> tagIds
) {
    private static final String ERROR_VALUE_IS_HIGHER_THAN_MiN = "값은 1 이상이어야 합니다.";
}
