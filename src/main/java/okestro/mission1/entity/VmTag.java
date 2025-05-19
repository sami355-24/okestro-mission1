package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Entity
@Table(name="vm_tag")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class VmTag extends TimestampEntity{

    @Id
    @Column(name = "vm_tag_id")
    private String vmTagId;

    @ManyToOne
    @JoinColumn(name = "vm_id")
    @NotBlank
    private Vm vm;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    @NotBlank
    private Tag tag;
}
