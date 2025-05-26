package okestro.mission1.controller;

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
@RequestMapping("/tag")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
public class TagController {

    TagService tagService;

    @GetMapping
    public ResponseEntity<ResponseTemplate<List<FindTagResponseDto>>> findAllTag() {
        return ResponseEntity.ok(ResponseTemplate.<List<FindTagResponseDto>>builder()
                .metaData(MetaData.ofSuccess())
                .result(tagService.findAll().stream().map(tag -> new FindTagResponseDto(tag.getId(), tag.getName())).toList())
                .build());
    }

    @PostMapping
    public ResponseEntity<ResponseTemplate<Integer>> createTag(@RequestParam @NotBlank(message = "태그 명은 공백일 수 없습니다.") String title) {
        return ResponseEntity.ok(ResponseTemplate.<Integer>builder()
                .metaData(MetaData.ofSuccess())
                .result(tagService.createTagFrom(title))
                .build());
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<ResponseTemplate<Void>> deleteTag(@PathVariable Integer tagId) {
        return ResponseEntity.ok(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofSuccess())
                .result(tagService.deleteTagFrom(tagId))
                .build());
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<ResponseTemplate<Void>> updateTag(@PathVariable Integer tagId, @RequestParam @NotBlank(message = "태그 명은 공백일 수 없습니다.") String tagTitle) {
        return ResponseEntity.ok(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofSuccess())
                .result(tagService.updateTagFrom(tagId, tagTitle))
                .build());
    }
}
