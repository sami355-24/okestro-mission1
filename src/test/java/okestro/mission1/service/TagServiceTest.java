package okestro.mission1.service;

import okestro.mission1.entity.Tag;
import okestro.mission1.exception.custom.BlankException;
import okestro.mission1.exception.custom.NotExistException;
import okestro.mission1.initializer.InitTag;
import okestro.mission1.repository.TagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class TagServiceTest {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagService tagService;

    @MockBean
    private InitTag initTag;

    @BeforeEach
    void setUp() {
        tagRepository.deleteAll();
        tagRepository.saveAll(
                List.of(
                        Tag.builder().title("DEV").build(),
                        Tag.builder().title("TEST").build(),
                        Tag.builder().title("BUILD").build(),
                        Tag.builder().title("PROD").build(),
                        Tag.builder().title("EXISTING").build()
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
        String existingTitle = "EXISTING";

        @Test
        void 태그_생성시_태그_생성에_실패한다() {
            //when & then
            assertThatThrownBy(() -> tagService.createTagFrom(existingTitle)).isInstanceOf(DataIntegrityViolationException.class);
        }

    }

    @Nested
    class 중복되지_않는_태그명이_주어질_때 {
        //given
        String originTitle = "ORIGIN";
        String blankTitle = " ";

        @Test
        void 태그명이_고유할_경우_아니라면_태그가_생성에_성공한다() {
            //when
            tagService.createTagFrom(originTitle);

            //then
            assertThat(tagRepository.findByTitle(originTitle)).isPresent();
        }

        @Test
        void 태그명에_공백일경우_태그_생성에_실패한다(){
            //when & then
            assertThatThrownBy(() -> tagService.createTagFrom(blankTitle)).isInstanceOf(BlankException.class);
        }

    }

    @Test
    void 존재하는_태그_Id가_주어진다면_삭제에_성공한다() {
        //given
        int existingTagId = tagRepository.findByTitle("DEV")
                .map(Tag::getId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 태그명입니다."));

        //when
        tagService.deleteTagFrom(existingTagId);

        //then
        assertThat(tagRepository.findById(existingTagId)).isEmpty();
    }


    @Test
    void 존재하지_않는_태그_Id가_주어진다면_삭제에_실패한다() {
        //given
        int notExistingTagId = -1;

        //when & then
        Assertions.assertThatThrownBy(()->tagService.deleteTagFrom(notExistingTagId)).isInstanceOf(NotExistException.class);
    }


}