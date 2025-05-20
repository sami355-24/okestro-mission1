package okestro.mission1.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.entity.Tag;
import okestro.mission1.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Service
@RequiredArgsConstructor(access = PROTECTED)
@FieldDefaults(level = PROTECTED, makeFinal = true)
public class TagService {

    TagRepository tagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    private boolean isDuplicate(String tagTitle) {
        return tagRepository.existsByTitle(tagTitle);
    }
}
