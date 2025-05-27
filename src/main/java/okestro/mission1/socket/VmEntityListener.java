package okestro.mission1.socket;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import okestro.mission1.entity.Vm;
import okestro.mission1.util.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static okestro.mission1.util.Message.*;

@Slf4j
@Component
public class VmEntityListener {

    @PostUpdate
    @PostPersist
    public void postUpdate(Vm vm) {
        if (vm.getPreviousVmStatus() == null) {
            String message = SUCCESS_VM_CREATE.getMessage().formatted(String.valueOf(vm.getVmStatus()), String.valueOf(vm.getVmId()));
            log.info(message);
            sendNotification(vm, message);
            return;
        }

        if (vm.getPreviousVmStatus() != vm.getVmStatus()) {
            String message = SUCCESS_VM_LISTENER.getMessage().formatted(String.valueOf(vm.getPreviousVmStatus()), String.valueOf(vm.getVmStatus()), vm.getVmId());
            log.info(message);
            sendNotification(vm, message);
        }

    }

    private void sendNotification(Vm vm, String messageFormat, Object... args) {
        try {
            String message = String.format(messageFormat, args);
            VmSocketHandler vmSocketHandler = BeanUtils.getBean(VmSocketHandler.class);
            vmSocketHandler.sendMessageToUser(String.valueOf(vm.getMember().getMemberId()), message);
        } catch (IOException e) {
            log.error(ERROR_WEBSOCKET.getMessage(), e);
        }
    }

}
