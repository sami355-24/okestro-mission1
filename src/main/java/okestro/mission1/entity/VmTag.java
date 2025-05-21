package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.*;

@Entity
@Table(name="vm_tag")
@NoArgsConstructor(access = PROTECTED)
@Getter
@FieldDefaults(level = PRIVATE)
public class VmTag extends TimestampEntity{

    @Id
    @Column(name = "vm_tag_id")
    String vmTagId;

    @ManyToOne
    @JoinColumn(name = "vm_id")
    @NotBlank
    Vm vm;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    @NotBlank
    Tag tag;
}
