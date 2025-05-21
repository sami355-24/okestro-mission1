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

    public static MetaData ofClientFailure(String message) {
        return new MetaData(400, message);
    }

    public static MetaData ofServerFailure(String message) {
        return new MetaData(500, message);
    }
}
