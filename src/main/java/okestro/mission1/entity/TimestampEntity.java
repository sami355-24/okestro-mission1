package okestro.mission1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@MappedSuperclass
@Getter
@FieldDefaults(level = PRIVATE)
public class TimestampEntity {

    @Column(name = "create_at", updatable = false)
    @NotNull
    LocalDateTime createAt;

    @Column(name = "update_at")
    LocalDateTime updateAt;

    @PrePersist
    private void setCreateAt() {
        this.createAt = LocalDateTime.now();
    }

    @PreUpdate
    private void setUpdateAt() {
        this.updateAt = LocalDateTime.now();
    }
}
