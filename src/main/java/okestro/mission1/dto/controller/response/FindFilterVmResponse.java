package okestro.mission1.dto.controller.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.List;

@Data
public class FindFilterVmResponse {
    int vmId;
    String vmName;
    List<String> tags;
    String privateIp;

    @QueryProjection
    public FindFilterVmResponse(int vmId, String vmName, List<String> tags, String privateIp) {
        this.vmId = vmId;
        this.vmName = vmName;
        this.tags = tags;
        this.privateIp = privateIp;
    }
}
