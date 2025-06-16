package okestro.mission1.service.NotiStateService.Impl;

import okestro.mission1.entity.VmStatus;
import okestro.mission1.service.NotiStateService.VmStatusNotiState;
import org.springframework.stereotype.Component;

@Component
public class RunningNotiState implements VmStatusNotiState {
    @Override
    public boolean supports(VmStatus prevVmStatus) {
        return prevVmStatus == VmStatus.RUNNING;
    }

    @Override
    public String of(VmStatus currentVmStatus) {
        if (currentVmStatus == VmStatus.PENDING ||
                currentVmStatus == VmStatus.REBOOTING ||
                currentVmStatus == VmStatus.TERMINATING
        ) {
            return "정상";
        }
        return "비정상";
    }
}
