package okestro.mission1.dto.response.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseTemplate<T> {

    private MetaData metaData;
    private T result;
}
