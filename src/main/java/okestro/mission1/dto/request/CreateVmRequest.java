package okestro.mission1.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateVmRequest(
        @JsonProperty("name") @NotNull String name,
        @JsonProperty("description") @NotNull String description,
        @JsonProperty("vCpu") int vCpu,
        @JsonProperty("memory") int memory,
        @JsonProperty("storage") int storage,
        @JsonProperty("networkIds") @NotNull List<Integer> networkIds,
        @JsonProperty("tagIds") List<Integer> tagIds
) {
}