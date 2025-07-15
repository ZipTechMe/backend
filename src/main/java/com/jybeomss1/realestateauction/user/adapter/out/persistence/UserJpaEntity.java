package com.jybeomss1.realestateauction.user.adapter.out.persistence;

import com.jybeomss1.realestateauction.common.util.BaseTimeEntity;
import com.jybeomss1.realestateauction.user.domain.User;
import com.jybeomss1.realestateauction.user.domain.UserGrade;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "\"user\"")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "social_type")
    private String socialType;

    @Column(name = "social_id")
    private String socialId;

    @Column(name = "user_grade", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserGrade userGrade;

    @Column(nullable = false)
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        this.deleted = false;
        if (this.userGrade == null) {
            this.userGrade = UserGrade.LITE;
        }
    }

    public User toDto() {
        return new User(this);
    }

    public UserJpaEntity(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.userGrade = UserGrade.LITE;
    }

    public void upgradeGrade(UserGrade newGrade) {
        this.userGrade = newGrade;
    }
}
