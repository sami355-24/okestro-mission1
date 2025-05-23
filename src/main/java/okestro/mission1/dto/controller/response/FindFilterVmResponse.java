package okestro.mission1.dto.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FindFilterVmResponse {
    int vmId;
    String vmName;
    List<String> tags;
    String privateIp;
}
