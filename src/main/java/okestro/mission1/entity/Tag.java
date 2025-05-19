package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "tag")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Tag extends TimestampEntity{

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "tag_id")
    private String id;

    @NotBlank(message = "태그를 입력해주세요")
    private String title;
}
