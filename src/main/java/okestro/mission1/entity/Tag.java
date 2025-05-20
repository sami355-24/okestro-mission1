package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "tag")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@FieldDefaults(level = PRIVATE)
public class Tag extends TimestampEntity{

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "tag_id")
    int id;

    @NotBlank(message = "태그를 입력해주세요")
    @Column(unique = true)
    String title;
}
