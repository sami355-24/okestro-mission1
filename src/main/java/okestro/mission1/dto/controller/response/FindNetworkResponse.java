package okestro.mission1.dto.controller.response;

public record FindNetworkResponse(
        int networkId,
        String openIp,
        int openPort
) {
}
