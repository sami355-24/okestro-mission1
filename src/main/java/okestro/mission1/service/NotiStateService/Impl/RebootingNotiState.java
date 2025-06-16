package okestro.mission1.service.NotiStateService.Impl;

import okestro.mission1.entity.VmStatus;
import okestro.mission1.service.NotiStateService.VmStatusNotiState;
import org.springframework.stereotype.Component;

@Component
public class RebootingNotiState implements VmStatusNotiState {
    @Override
    public boolean supports(VmStatus prevVmStatus) {
        return prevVmStatus == VmStatus.REBOOTING;
    }

    @Override
    public String of(VmStatus currentVmStatus) {
        if (currentVmStatus == VmStatus.STARTING ||
                currentVmStatus == VmStatus.RUNNING ||
                currentVmStatus == VmStatus.PENDING ||
                currentVmStatus == VmStatus.TERMINATING
        ) {
            return "정상";
        }
        return "비정상";
    }
}
