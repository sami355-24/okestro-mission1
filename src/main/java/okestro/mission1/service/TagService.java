package okestro.mission1.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.entity.Tag;
import okestro.mission1.exception.custom.DuplicateTagTitleException;
import okestro.mission1.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Service
@RequiredArgsConstructor(access = PROTECTED)
@FieldDefaults(level = PROTECTED, makeFinal = true)
public class TagService {

    String DUPLICATE_TAG_MESSAGE = "이미 존재하는 태그 이름입니다.";
    TagRepository tagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Void createTagFrom(String title) {
        if(isDuplicate(title))
            throw new DuplicateTagTitleException(DUPLICATE_TAG_MESSAGE);
        tagRepository.save(Tag.builder().title(title).build());
        return null;
    }

    private boolean isDuplicate(String tagTitle) {
        return tagRepository.existsByTitle(tagTitle);
    }
}
