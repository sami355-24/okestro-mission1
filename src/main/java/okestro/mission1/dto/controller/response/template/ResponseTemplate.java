package okestro.mission1.dto.controller.response.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "응답 템플릿입니다.")
public class ResponseTemplate<T> {

    private MetaData metaData;
    private T result;
}
