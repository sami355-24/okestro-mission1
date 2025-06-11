package okestro.mission1.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import okestro.mission1.dto.service.vm.NotificationDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotiService {

    SimpMessagingTemplate messagingTemplate;

    public void sendNotificationToMember(String memberId, NotificationDto notificationDto) {
        try {
            log.info("Sending notification to member {}: {}", memberId, notificationDto.message());
            messagingTemplate.convertAndSendToUser(
                    memberId,
                    "/queue/notifications",
                    notificationDto
            );
        } catch (Exception e) {
            log.error("Failed to send notification to member {}: {}", memberId, e.getMessage(), e);
        }
    }
}
