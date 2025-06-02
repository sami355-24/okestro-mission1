package okestro.mission1.dto.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateVmRequestDto(
        @JsonProperty("name") @NotBlank(message = ERROR_VALUE_IS_BLANK) String name,
        @JsonProperty("description") String description,
        @Schema(description = "cpu 코어수입니다.")
        @JsonProperty("vCpu") int vCpu,
        @Schema(description = "memory 크기입니다. 단위는 GB입니다.")
        @JsonProperty("memory") int memory,
        @Schema(description = "storage 크기입니다. 단위는 GB입니다.")
        @JsonProperty("storage") int storage,
        @JsonProperty("networkIds") @NotNull(message = ERROR_VALUE_IS_NULL) List<Integer> networkIds,
        @JsonProperty("tagIds") List<Integer> tagIds
) {
    private static final String ERROR_VALUE_IS_BLANK = "비어 있는 값이 들어왔습니다.";
    private static final String ERROR_VALUE_IS_NULL = "값으로 null이 들어왔습니다.";
}