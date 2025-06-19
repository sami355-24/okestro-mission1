package okestro.mission1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.dto.controller.response.FindTagResponseDto;
import okestro.mission1.dto.controller.response.template.MetaData;
import okestro.mission1.dto.controller.response.template.ResponseTemplate;
import okestro.mission1.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
@Tag(name = "Tag", description = "태그관련 CRUD API입니다.")
public class TagController {

    static final String ERROR_TAG_NAME_EMPTY = "태그 명은 공백일 수 없습니다.";

    TagService tagService;

    @GetMapping
    @Operation(summary = "태그 전체조회", description = "저장되어 있는 모든 태그 정보를 조회합니다.")
    public ResponseEntity<ResponseTemplate<List<FindTagResponseDto>>> findAllTag() {
        return ResponseEntity.ok(ResponseTemplate.<List<FindTagResponseDto>>builder()
                .metaData(MetaData.ofSuccess())
                .result(tagService.findAll().stream().map(tag -> new FindTagResponseDto(tag.getId(), tag.getName())).toList())
                .build());
    }

    @PostMapping
    @Operation(summary = "태그 생성", description = "주어진 태그 명을 기반으로 새로운 태그를 생성합니다.")
    public ResponseEntity<ResponseTemplate<Integer>> createTag(@RequestParam @NotBlank(message = ERROR_TAG_NAME_EMPTY) String name) {
        return ResponseEntity.ok(ResponseTemplate.<Integer>builder()
                .metaData(MetaData.ofSuccess())
                .result(tagService.createTagFrom(name))
                .build());
    }

    @DeleteMapping("/{tagId}")
    @Operation(summary = "태그 삭제", description = "주어진 tag id를 기반으로 태그를 삭제합니다.")
    public ResponseEntity<ResponseTemplate<Void>> deleteTag(@PathVariable Integer tagId) {
        tagService.deleteTagFrom(tagId);
        return ResponseEntity.ok(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofSuccess())
                .result(null)
                .build());
    }

    @PutMapping("/{tagId}")
    @Operation(summary = "태그명 수정", description = "주어진 tag id와 tag title을 기반으로 태그명을 수정합니다.")
    public ResponseEntity<ResponseTemplate<Void>> updateTag(@PathVariable Integer tagId, @RequestParam(name = "tag-name") @NotBlank(message = ERROR_TAG_NAME_EMPTY) String tagTitle) {
        tagService.updateTagFrom(tagId, tagTitle);
        return ResponseEntity.ok(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofSuccess())
                .result(null)
                .build());
    }

    @GetMapping("/validate")
    public ResponseEntity<ResponseTemplate<Boolean>> validateTag(@RequestParam @NotBlank(message = ERROR_TAG_NAME_EMPTY) String name) {
        boolean isExist = tagService.isExist(name);
        return ResponseEntity.ok(ResponseTemplate.<Boolean>builder()
                .metaData(MetaData.ofSuccess())
                .result(!isExist)
                .build());
    }
}
