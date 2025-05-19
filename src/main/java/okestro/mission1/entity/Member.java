package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Member extends TimestampEntity{

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "member_id")
    private int memberId;

    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z0-9]).{8,}$",
            message = "대문자, 소문자, 특수문자를 각각 최소 1개 이상 포함하고 8자 이상이어야 합니다.")
    private String password;
}
