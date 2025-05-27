package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@FieldDefaults(level = PRIVATE)
public class Member extends TimestampEntity {

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z0-9]).{8,}$";
    private static final String ERROR_VIOLATE_PASSWORD_RULE = "대문자, 소문자, 특수문자를 각각 최소 1개 이상 포함하고 8자 이상이어야 합니다.";
    private static final String ERROR_EMAIL = "이메일 형식이 아닙니다.";


    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "member_id")
    int memberId;

    @Email(message = ERROR_EMAIL)
    String email;

    @Pattern(regexp = PASSWORD_PATTERN,
            message = ERROR_VIOLATE_PASSWORD_RULE)
    String password;
}
