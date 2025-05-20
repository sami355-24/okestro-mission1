package okestro.mission1.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.entity.Tag;
import okestro.mission1.exception.custom.DuplicateTagTitleException;
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

    String DUPLICATE_TAG_MESSAGE = "이미 존재하는 태그 이름입니다.";
    String NOT_EXIST_TAG_MESSAGE = "존재하지 않는 태그 이름입니다.";
    TagRepository tagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Void createTagFrom(String tagTitle) {
        if(Boolean.TRUE.equals(tagRepository.existsByTitle(tagTitle)))
            throw new DuplicateTagTitleException(DUPLICATE_TAG_MESSAGE);
        tagRepository.save(Tag.builder().title(tagTitle).build());
        return null;
    }

    @Transactional
    public Void deleteTagFrom(int tagId) {
        if(tagRepository.findById(tagId).isEmpty())
            throw new NotExistException(NOT_EXIST_TAG_MESSAGE);
        tagRepository.deleteById(tagId);
        return null;
    }
}
