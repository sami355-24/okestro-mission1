package okestro.mission1.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import okestro.mission1.dto.service.vm.NotificationDto;
import okestro.mission1.entity.VmStatus;
import okestro.mission1.service.NotiStateService.VmStatusNotiState;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotiService {

    SimpMessagingTemplate messagingTemplate;
    List<VmStatusNotiState> vmStatusNotiStates;

    public void sendNotificationToMember(String memberId, NotificationDto notificationDto) {
        try {
            log.info("Sending notification to member {}: {}", memberId, notificationDto.toString());
            messagingTemplate.convertAndSendToUser(
                    memberId,
                    "/queue/notifications",
                    notificationDto
            );
        } catch (Exception e) {
            log.error("Failed to send notification to member {}: {}", memberId, e.getMessage(), e);
        }
    }

    public String generateNotiState(VmStatus previousStatus, VmStatus currentStatus) {
        for (VmStatusNotiState vmStatusNotiState : vmStatusNotiStates) {
            if (vmStatusNotiState.supports(previousStatus)) {
                return vmStatusNotiState.of(currentStatus);
            }
        }
        throw new IllegalStateException("No noti state found");
    }
}
