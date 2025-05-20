package okestro.mission1.dto.response.template;

import lombok.Data;

@Data
public class MetaData {

    private int statusCode;
    private String statusMessage;

    private MetaData(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public static MetaData ofSuccess() {
        return new MetaData(200, "Success");
    }
}
