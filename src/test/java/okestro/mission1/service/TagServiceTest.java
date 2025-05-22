package okestro.mission1.service;

import okestro.mission1.entity.Tag;
import okestro.mission1.exception.custom.BlankException;
import okestro.mission1.exception.custom.DuplicateException;
import okestro.mission1.exception.custom.NotExistException;
import okestro.mission1.repository.TagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
class TagServiceTest {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagService tagService;


    @BeforeEach
    void setUp() {
        tagRepository.deleteAll();
        tagRepository.saveAll(
                List.of(
                        Tag.builder().name("DEV").build(),
                        Tag.builder().name("TEST").build(),
                        Tag.builder().name("BUILD").build(),
                        Tag.builder().name("PROD").build(),
                        Tag.builder().name("EXISTING").build()
                )
        );
    }

    @Test
    void 태그를_조회할_수_있다() {
        //when
        List<Tag> tags = tagService.findAll();

        //then
        assertThat(tags).hasSize(5);
    }

    @Nested
    class 태그를_생성시도시 {
        @Test
        void 중복된_태그명일경우_태그_생성시_태그_생성에_실패한다() {
            //given
            String existingName = "EXISTING";

            //when & then
            assertThatThrownBy(() -> tagService.createTagFrom(existingName)).isInstanceOf(DataIntegrityViolationException.class);
        }

        @Test
        void 공백일경우_태그_생성에_실패한다() {
            //given
            String blankName = " ";

            //when & then
            assertThatThrownBy(() -> tagService.createTagFrom(blankName)).isInstanceOf(BlankException.class);
        }

        @Test
        void 고유한_태그명일경우_태그가_생성에_성공한다() {
            //given
            String originName = "ORIGIN";

            //when
            tagService.createTagFrom(originName);

            //then
            assertThat(tagRepository.findByName(originName)).isPresent();
        }
    }


    @Nested
    class 태그를_삭제시도시 {
        @Test
        void 존재하는_태그_Id가_주어진다면_삭제에_성공한다() {
            //given
            int existingTagId = tagRepository.findByName("DEV")
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
            Assertions.assertThatThrownBy(() -> tagService.deleteTagFrom(notExistingTagId)).isInstanceOf(NotExistException.class);
        }
    }

    @Nested
    class 태그명을_수정_시도시 {
        @Test
        void 중복된_태그_명이_주어진다면_수정에_실패한다() {
            //given
            Tag findTag = tagRepository.findByName("PROD").orElseThrow(() -> new IllegalArgumentException("테스트 인자가 잘못되었습니다."));
            String existingTitle = "PROD";

            //when & then
            Assertions.assertThatThrownBy(() -> tagService.updateTagFrom(findTag.getId(), existingTitle)).isInstanceOf(DuplicateException.class);
        }

        @Test
        void 공백으로_태그_명이_주어진다면_수정에_실패한다() {
            //given
            Tag findTag = tagRepository.findByName("PROD").orElseThrow(() -> new IllegalArgumentException("테스트 인자가 잘못되었습니다."));
            String blankTitle = " ";

            //when & then
            Assertions.assertThatThrownBy(() -> tagService.updateTagFrom(findTag.getId(), blankTitle)).isInstanceOf(BlankException.class);
        }

        @Test
        void 중복되지_않으며_공백이_아닌_태그_명이_주어진다면_수정에_성공한다() {
            //given
            Tag findTag = tagRepository.findByName("EXISTING").orElseThrow(() -> new IllegalArgumentException("테스트 인자가 잘못되었습니다."));
            String newTitle = "NEW_TITLE";

            //when
            tagService.updateTagFrom(findTag.getId(), newTitle);

            //then
            Assertions.assertThat(tagRepository.findByName(newTitle)).isPresent();
        }
    }
}