package okestro.mission1.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.dto.response.FindAllTagResponse;
import okestro.mission1.dto.response.template.MetaData;
import okestro.mission1.dto.response.template.ResponseTemplate;
import okestro.mission1.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
public class TagController {

    TagService tagService;

    @GetMapping
    public ResponseEntity<ResponseTemplate<FindAllTagResponse>> findAllTag() {
        return ResponseEntity.ok(ResponseTemplate.<FindAllTagResponse>builder()
                .metaData(MetaData.ofSuccess())
                .result(new FindAllTagResponse(tagService.findAll()))
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
