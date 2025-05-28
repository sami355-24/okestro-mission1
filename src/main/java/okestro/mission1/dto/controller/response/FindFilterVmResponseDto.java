package okestro.mission1.dto.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import okestro.mission1.entity.Vm;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class FindFilterVmResponseDto {
    @Schema(description = "현재 페이지입니다. 1페이지부터 시작합니다.", example = "1")
    private int pageNumber;
    @Schema(description = "마지막 페이지입니다.", example = "10")
    private int totalPages;
    @Schema(description = "현재 페이지에 있는 데이터 내용입니다.")
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
