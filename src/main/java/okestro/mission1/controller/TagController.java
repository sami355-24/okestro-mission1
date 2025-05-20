package okestro.mission1.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.dto.response.FindAllTagResponse;
import okestro.mission1.dto.response.template.MetaData;
import okestro.mission1.dto.response.template.ResponseTemplate;
import okestro.mission1.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
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
    public ResponseEntity<ResponseTemplate<Void>> createTag(@RequestParam String title) {
        return ResponseEntity.ok(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofSuccess())
                .result(tagService.createTagFrom(title))
                .build());
    }

}
