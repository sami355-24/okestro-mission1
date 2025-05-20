package okestro.mission1.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.dto.response.FindAllTagResponse;
import okestro.mission1.dto.response.template.MetaData;
import okestro.mission1.dto.response.template.ResponseTemplate;
import okestro.mission1.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
