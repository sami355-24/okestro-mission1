package okestro.mission1.dto.service.vm;

public record NotificationDto(
        String prevVmState,
        String currentVmState,
        String type,
        int vmId
) {
}
