package okestro.mission1.service.NotiStateService;

import okestro.mission1.entity.VmStatus;

public interface VmStatusNotiState {

    boolean supports(VmStatus prevVmStatus);

    String of(VmStatus currentVmStatus);
}
