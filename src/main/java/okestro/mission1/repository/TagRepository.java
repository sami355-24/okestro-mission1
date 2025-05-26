package okestro.mission1.repository;

import okestro.mission1.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    Boolean existsByName(String title);

    Optional<Tag> findByName(String title);

    @Query("""
            SELECT COUNT(t)
            FROM Tag t
            WHERE t.id IN :tagIds
            """)
    int existsAllTagsWithIds(@Param("tagIds") List<Integer> tagIds);

}
