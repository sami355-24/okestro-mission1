package okestro.mission1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class TimestampEntity {

    @Column(name = "create_at", updatable = false)
    @NotBlank
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @PrePersist
    private void setCreateAt() {
        this.createAt = LocalDateTime.now();
    }

    @PreUpdate
    private void setUpdateAt() {
        this.updateAt = LocalDateTime.now();
    }
}
