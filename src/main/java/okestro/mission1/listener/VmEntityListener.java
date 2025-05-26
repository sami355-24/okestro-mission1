package okestro.mission1.listener;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import okestro.mission1.component.VmSocketHandler;
import okestro.mission1.entity.Vm;
import okestro.mission1.util.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class VmEntityListener {

    @PostUpdate
    @PostPersist
    public void postUpdate(Vm vm) {
        if (vm.getPreviousVmStatus() == null) {
            log.info("새로운 VM 생성: 상태 = {}, VM ID: {}", vm.getVmStatus(), vm.getVmId());
            sendNotification(vm, "새로운 VM 생성: 상태 = %s, VM ID: %s", vm.getVmStatus(), vm.getVmId());
        }

        else if (vm.getPreviousVmStatus() != vm.getVmStatus()) {
            log.info("VM 상태 변경 감지: {} -> {}, VM ID: {}", vm.getPreviousVmStatus(), vm.getVmStatus(), vm.getVmId());
            sendNotification(vm, "VM 상태 변경: %s → %s, VM ID: %s", vm.getPreviousVmStatus(), vm.getVmStatus(), vm.getVmId());
        }

    }

    private void sendNotification(Vm vm, String messageFormat, Object... args) {
        try {
            String message = String.format(messageFormat, args);
            VmSocketHandler vmSocketHandler = BeanUtils.getBean(VmSocketHandler.class);
            vmSocketHandler.sendMessageToUser(String.valueOf(vm.getMember().getMemberId()), message);
        } catch (IOException e) {
            log.error("웹소켓 메시지 전송 중 오류 발생", e);
        }
    }

}
