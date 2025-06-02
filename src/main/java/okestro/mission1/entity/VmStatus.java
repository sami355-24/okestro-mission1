package okestro.mission1.entity;

import java.util.Random;

public enum VmStatus {
    STARTING, RUNNING, PENDING, REBOOTING, TERMINATING, TERMINATED;

    public static VmStatus getRandomStatus() {
        Random random = new Random();
        VmStatus[] statuses = VmStatus.values();
        int index = random.nextInt(statuses.length);
        return statuses[index];
    }
}
