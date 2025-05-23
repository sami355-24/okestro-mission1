package okestro.mission1.dto.service.vm;

import okestro.mission1.dto.controller.request.UpdateVmRequest;
import okestro.mission1.entity.Network;
import okestro.mission1.entity.Tag;

import java.util.List;

public record VmServiceUpdateDto(
        int vmId,
        List<Tag> tags,
        List<Network> networks,
        String name,
        String description,
        Integer vCpu,
        Integer memory
) {
    public VmServiceUpdateDto(int vmId, List<Tag> findTags, List<Network> findNetworks, UpdateVmRequest request) {
        this(vmId, findTags, findNetworks, request.name(), request.description(), request.vCpu(), request.memory());
    }
}
