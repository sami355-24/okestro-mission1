package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static jakarta.persistence.GenerationType.*;
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
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "vm_tag_id")
    int vmTagId;

    @ManyToOne
    @JoinColumn(name = "vm_id")
    @NotNull
    @Setter(PUBLIC)
    Vm vm;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    @NotNull
    Tag tag;

    public VmTag(Vm vm, Tag tag) {
        this.vm = vm;
        this.tag = tag;
    }
}
