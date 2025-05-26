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

    String DUPLICATE_TAG_NAME_MESSAGE = "이미 존재하는 태그 이름입니다.";
    String NOT_EXIST_TAG_NAME_MESSAGE = "존재하지 않는 태그 이름입니다.";
    String NOT_EXIST_TAG_ID_MESSAGE = "존재하지 않는 태그 ID입니다.";
    String BLANK_TAG_NAME_MESSAGE = "태그 이름이 공백입니다.";
    TagRepository tagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public List<Tag> findAllByTagIds(List<Integer> tagIds) {
        return tagRepository.findAllById(tagIds);
    }

    @Transactional
    public Void deleteTagFrom(int tagId) {
        if (tagRepository.findById(tagId).isEmpty())
            throw new NotExistException(NOT_EXIST_TAG_NAME_MESSAGE);
        tagRepository.deleteById(tagId);
        return null;
    }

    @Transactional
    public Void updateTagFrom(int existingTagId, String newTitle) {
        Tag findTag = tagRepository.findById(existingTagId).orElseThrow(() -> new NotExistException(NOT_EXIST_TAG_ID_MESSAGE));
        validateTagName(newTitle);
        findTag.setName(newTitle);
        return null;
    }

    public void validateTagIds(List<Integer> ids) {
        int existsAllTagsWithIds = tagRepository.existsAllTagsWithIds(ids);
        if (existsAllTagsWithIds == ids.size()) return;
        throw new NotExistException(NOT_EXIST_TAG_ID_MESSAGE);
    }

    @Transactional
    public Integer createTagFrom(String tagName) {
        validateTagName(tagName);
        return tagRepository.save(Tag.builder().name(tagName).build()).getId();
    }

    private void validateTagName(String tagName) {
        checkBlankTagName(tagName);
        checkDuplicateTagName(tagName);
    }

    private void checkBlankTagName(String tagName) {
        if (tagName.isBlank())
            throw new BlankException(BLANK_TAG_NAME_MESSAGE);
    }

    private void checkDuplicateTagName(String tagName) {
        if (Boolean.TRUE.equals(tagRepository.existsByName(tagName)))
            throw new DuplicateException(DUPLICATE_TAG_NAME_MESSAGE);
    }
}
