package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.*;

@Entity
@Table(name = "vm_tag")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@FieldDefaults(level = PRIVATE)
public class VmTag extends TimestampEntity {

    @Id
    @Column(name = "vm_tag_id")
    int vmTagId;

    @ManyToOne
    @JoinColumn(name = "vm_id")
    @NotNull
    Vm vm;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    @NotNull
    Tag tag;
}
