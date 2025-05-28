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

    @PostPersist
    public void postPersist(Vm vm) {
        String message = SUCCESS_VM_CREATE.getMessage().formatted(String.valueOf(vm.getVmStatus()), String.valueOf(vm.getVmId()));
        sendNotification(vm, message);
    }

    @PostUpdate
    public void postUpdate(Vm vm) {
        if (vm.getPreviousVmStatus() == vm.getVmStatus()) return;
        String message = SUCCESS_VM_LISTENER.getMessage().formatted(String.valueOf(vm.getPreviousVmStatus()), String.valueOf(vm.getVmStatus()), vm.getVmId());
        sendNotification(vm, message);
    }


    private void sendNotification(Vm vm, String message) {
        log.info(message);
        try {
            VmSocketHandler vmSocketHandler = BeanUtils.getBean(VmSocketHandler.class);
            vmSocketHandler.sendMessageToUser(String.valueOf(vm.getMember().getMemberId()), message);
        } catch (IOException e) {
            log.error(ERROR_WEBSOCKET.getMessage(), e);
        }
    }

}
