package okestro.mission1.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.entity.Tag;
import okestro.mission1.exception.custom.BlankException;
import okestro.mission1.exception.custom.DuplicateException;
import okestro.mission1.exception.custom.NotExistException;
import okestro.mission1.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Service
@RequiredArgsConstructor(access = PROTECTED)
@FieldDefaults(level = PROTECTED, makeFinal = true)
public class TagService {

    String DUPLICATE_TAG_TITLE_MESSAGE = "이미 존재하는 태그 이름입니다.";
    String NOT_EXIST_TAG_TITLE_MESSAGE = "존재하지 않는 태그 이름입니다.";
    String NOT_EXIST_TAG_ID_MESSAGE = "존재하지 않는 태그 ID입니다.";
    String BLANK_TAG_TITLE_MESSAGE = "태그 이름이 공백입니다.";
    TagRepository tagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Transactional
    public Void deleteTagFrom(int tagId) {
        if(tagRepository.findById(tagId).isEmpty())
            throw new NotExistException(NOT_EXIST_TAG_TITLE_MESSAGE);
        tagRepository.deleteById(tagId);
        return null;
    }

    @Transactional
    public void updateTagFrom(int existingTagId, String newTitle) {
        Tag findTag = tagRepository.findById(existingTagId).orElseThrow(() -> new NotExistException(NOT_EXIST_TAG_ID_MESSAGE));
        validateTagTitle(newTitle);
        findTag.setTitle(newTitle);
    }

    @Transactional
    public Void createTagFrom(String tagTitle) {
        validateTagTitle(tagTitle);
        tagRepository.save(Tag.builder().title(tagTitle).build());
        return null;
    }

    private void validateTagTitle(String tagTitle) {
        checkBlankTagTitle(tagTitle);
        checkDuplicateTagTitle(tagTitle);
    }

    private void checkBlankTagTitle(String tagTitle) {
        if(tagTitle.isBlank())
            throw new BlankException(BLANK_TAG_TITLE_MESSAGE);
    }

    private void checkDuplicateTagTitle(String tagTitle) {
        if(Boolean.TRUE.equals(tagRepository.existsByTitle(tagTitle)))
            throw new DuplicateException(DUPLICATE_TAG_TITLE_MESSAGE);
    }
}
