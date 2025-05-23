package okestro.mission1.dto.controller.request;

import java.util.List;
import java.util.Map;

public record FindVmFilterRequest(
        int page,
        Size size,
        List<Integer> tagIds,
        Map<String, String> sortParams
) {
    public enum Size {
        FIVE(5),
        TEN(10),
        TWENTY(20);

        private final int value;

        Size(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}