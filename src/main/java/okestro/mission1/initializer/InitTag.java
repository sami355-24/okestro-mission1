package okestro.mission1.initializer;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.entity.Tag;
import okestro.mission1.repository.TagRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InitTag {

    TagRepository tagRepository;

    @PostConstruct
    private void init() {
        tagRepository.saveAll(
                List.of(
                        Tag.builder().title("DEV").build(),
                        Tag.builder().title("TEST").build(),
                        Tag.builder().title("BUILD").build(),
                        Tag.builder().title("PROD").build()
                )
        );
    }
}
