package okestro.mission1.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdateVmRequest(
        @NotBlank String name,
        String description,
        @Min(1) Integer vCpu,
        @Min(1) Integer memory,
        List<Integer> networkIds
) {
}
