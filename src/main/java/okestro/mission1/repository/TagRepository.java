package okestro.mission1.repository;

import okestro.mission1.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    Boolean existsByName(String title);

    Optional<Tag> findByName(String title);
}
