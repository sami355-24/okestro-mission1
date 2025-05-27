package okestro.mission1.dto.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateVmRequestDto(
        @JsonProperty("name") @NotBlank(message = ERROR_VALUE_IS_BLANK) String name,
        @JsonProperty("description") String description,
        @JsonProperty("vCpu") int vCpu,
        @JsonProperty("memory") int memory,
        @JsonProperty("storage") int storage,
        @JsonProperty("networkIds") @NotNull(message = ERROR_VALUE_IS_NULL) List<Integer> networkIds,
        @JsonProperty("tagIds") List<Integer> tagIds
) {
    private static final String ERROR_VALUE_IS_BLANK = "비어 있는 값이 들어왔습니다.";
    private static final String ERROR_VALUE_IS_NULL = "값으로 null이 들어왔습니다.";
}