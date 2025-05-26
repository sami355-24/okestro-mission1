package okestro.mission1.dto.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import okestro.mission1.entity.Vm;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class FindFilterVmResponseDto {
    private int pageNumber;
    private int totalPages;
    private List<PageContent> pageContents;

    public FindFilterVmResponseDto(Page<Vm> filterVms){
        this.pageNumber = filterVms.getNumber()+1;
        this.totalPages = filterVms.getTotalPages();
        this.pageContents = filterVms.stream().map(
                vm -> new PageContent(
                        vm.getVmId(),
                        vm.getName(),
                        vm.getVmTags().stream().map(vmTag -> vmTag.getTag().getName()).toList(),
                        vm.getPrivateIp())
        ).toList();
    }

    @Data
    @AllArgsConstructor
    private class PageContent {
        int vmId;
        String vmName;
        List<String> tags;
        String privateIp;
    }
}
