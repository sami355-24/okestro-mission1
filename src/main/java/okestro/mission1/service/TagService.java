package okestro.mission1.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import okestro.mission1.entity.Tag;
import okestro.mission1.exception.custom.BlankException;
import okestro.mission1.exception.custom.DuplicateException;
import okestro.mission1.exception.custom.NotExistException;
import okestro.mission1.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;
import static okestro.mission1.util.Message.*;

@Service
@RequiredArgsConstructor(access = PROTECTED)
@FieldDefaults(level = PROTECTED, makeFinal = true)
@Slf4j
public class TagService {

    TagRepository tagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public List<Tag> findAllByTagIds(List<Integer> tagIds) {
        if (tagIds == null) return List.of();
        return tagRepository.findAllById(tagIds);
    }

    @Transactional
    public Void deleteTagFrom(int tagId) {
        if (tagRepository.findById(tagId).isEmpty())
            throw new NotExistException(ERROR_NOT_EXIST_TAG_IN_DB.getMessage());
        tagRepository.deleteById(tagId);
        return null;
    }

    @Transactional
    public Void updateTagFrom(int existingTagId, String newTitle) {
        Tag findTag = tagRepository.findById(existingTagId).orElseThrow(() -> new NotExistException(ERROR_NOT_EXIST_TAG_IN_DB.getMessage()));
        validateTagName(newTitle);
        findTag.setName(newTitle);
        return null;
    }

    public void validateTagFrom(List<Integer> ids) {
        if (ids == null) {
            log.warn(ERROR_TAG_ID_IS_NULL.getMessage());
            return;
        }

        int existsAllTagsWithIds = tagRepository.existsAllTagsWithIds(ids);
        if (existsAllTagsWithIds == ids.size()) return;
        throw new NotExistException(ERROR_NOT_EXIST_TAG_IN_DB.getMessage());
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
            throw new BlankException(ERROR_BLANK_TAG_NAME.getMessage());
    }

    private void checkDuplicateTagName(String tagName) {
        if (Boolean.TRUE.equals(tagRepository.existsByName(tagName)))
            throw new DuplicateException(ERROR_DUPLICATE_TAG_NAME.getMessage());
    }
}
