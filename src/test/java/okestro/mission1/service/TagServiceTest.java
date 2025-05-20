package okestro.mission1.service;

import okestro.mission1.entity.Tag;
import okestro.mission1.repository.TagRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagServiceTest {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagService tagService;


    @BeforeAll
    void setUp() {
        tagRepository.saveAll(
                List.of(
                        Tag.builder().title("DEV").build(),
                        Tag.builder().title("TEST").build(),
                        Tag.builder().title("BUILD").build(),
                        Tag.builder().title("PROD").build()
                )
        );
    }

    @Test
    void 사용자는_가상머신에_붙일_태그를_조회할_수_있다() {
        //when
        List<Tag> tags = tagService.findAll();

        //then
        assertThat(tags).hasSize(4);
    }

}