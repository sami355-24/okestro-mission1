package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "tag")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@FieldDefaults(level = PRIVATE)
public class Tag extends TimestampEntity {

    private static final String ERROR_TAG_NAME_BLANK = "태그는 공백이 될 수 없습니다.";
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tag_id")
    int id;

    @NotBlank(message = ERROR_TAG_NAME_BLANK)
    @Column(unique = true)
    @Setter(PUBLIC)
    String name;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    List<VmTag> vmTags = new ArrayList<>();
}
