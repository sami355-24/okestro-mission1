package okestro.mission1.dto.controller.response.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import static okestro.mission1.util.Message.SUCCESS;

@Data
@Schema(description = "로직 수행에 따른 메타데이터입니다.")
public class MetaData {

    @Schema(description = "로직 수행이후 반환하는 상태 코드입니다.", example = "200")
    private int statusCode;
    @Schema(description = "로직 수행이후 반환하는 상태 메시지입니다.", example = "Success")
    private String statusMessage;

    private MetaData(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public static MetaData ofSuccess() {
        return new MetaData(200, SUCCESS.getMessage());
    }

    public static MetaData ofClientFailure(String message) {
        return new MetaData(400, message);
    }

    public static MetaData ofServerFailure(String message) {
        return new MetaData(500, message);
    }
}
