package okestro.mission1.service;

import okestro.mission1.entity.Tag;
import okestro.mission1.initializer.InitTag;
import okestro.mission1.repository.TagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class TagServiceTest {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagService tagService;

    @MockBean
    private InitTag initTag;

    @BeforeAll
    void setUp() {
        tagRepository.saveAll(
                List.of(
                        Tag.builder().title("DEV").build(),
                        Tag.builder().title("TEST").build(),
                        Tag.builder().title("BUILD").build(),
                        Tag.builder().title("PROD").build(),
                        Tag.builder().title("DUPLICATE").build()
                )
        );
    }

    @Test
    void 사용자는_가상머신에_붙일_태그를_조회할_수_있다() {
        //when
        List<Tag> tags = tagService.findAll();

        //then
        assertThat(tags).hasSize(5);
    }

    @Nested
    class 중복된_태그명이_주어질_때 {
        //given
        String duplicateTitle = "DUPLICATE";

        @Test
        void 태그_생성시_태그_생성에_실패한다() {
            //when & then
            Assertions.assertThatThrownBy(DuplicateTagTitileException.class ,()-> tagService.createTag(duplicateTitle));
        }
    }

    @Nested
    class 중복되지_않는_태그명이_주어질_때 {
        //given
        String originTitle = "ORIGIN";

        @Test
        void 태그_생성시_태그가_생성에_성공한다() {
            //when
            tagService.createTag(originTitle);

            //then
            Assertions.assertThat(tagRepository.findByTitle(originTitle).isPresent()).isTrue();
        }
    }

}