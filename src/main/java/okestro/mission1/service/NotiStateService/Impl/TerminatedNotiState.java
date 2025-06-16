package okestro.mission1.service.NotiStateService.Impl;

import okestro.mission1.entity.VmStatus;
import okestro.mission1.service.NotiStateService.VmStatusNotiState;
import org.springframework.stereotype.Component;

@Component
public class TerminatedNotiState implements VmStatusNotiState {

    @Override
    public boolean supports(VmStatus prevVmStatus) {
        return prevVmStatus == VmStatus.TERMINATED;
    }

    @Override
    public String of(VmStatus currentVmStatus) {
        return "비정상";
    }
}
