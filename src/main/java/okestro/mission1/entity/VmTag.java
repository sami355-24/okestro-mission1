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

    static final String ERROR_VM_IS_NULL = "VM 연관관계가 NULL입니다.";
    static final String ERROR_TAG_IS_NULL = "TAG 연관관계가 NULL입니다.";

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "vm_tag_id")
    int vmTagId;

    @ManyToOne
    @JoinColumn(name = "vm_id")
    @NotNull(message = ERROR_VM_IS_NULL)
    @Setter(PUBLIC)
    Vm vm;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    @NotNull(message = ERROR_TAG_IS_NULL)
    Tag tag;

    public VmTag(Vm vm, Tag tag) {
        this.vm = vm;
        this.tag = tag;
    }
}
