package okestro.mission1.dto.response.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaData {

    private int statusCode;
    private String statusMessage;
}
