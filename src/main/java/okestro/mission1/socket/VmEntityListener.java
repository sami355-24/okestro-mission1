package okestro.mission1.socket;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import okestro.mission1.dto.service.vm.NotificationDto;
import okestro.mission1.entity.Vm;
import okestro.mission1.service.NotiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static okestro.mission1.util.Message.*;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VmEntityListener {

    private static NotiService notiService;

    @Autowired
    public void setNotiService(NotiService notiService) {
        VmEntityListener.notiService = notiService;
    }

    @PostPersist
    public void postPersist(Vm vm) {
        sendNotification(vm);
    }

    @PostUpdate
    public void postUpdate(Vm vm) {
        if (vm.getPreviousVmStatus() == null || vm.getPreviousVmStatus() == vm.getVmStatus()) return;
        sendNotification(vm);
    }


    private void sendNotification(Vm vm) {
        try {
            notiService.sendNotificationToMember(
                    String.valueOf(vm.getMember().getMemberId()),
                    new NotificationDto(
                            vm.getPreviousVmStatus() == null ? "" : vm.getPreviousVmStatus().toString(),
                            vm.getVmStatus().toString(),
                            notiService.generateNotiState(vm.getPreviousVmStatus(), vm.getVmStatus()),
                            vm.getVmId()
                    ));
        } catch (Exception e) {
            log.error(ERROR_WEBSOCKET.getMessage(), e);
        }
    }
}
