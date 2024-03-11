package com.hangulhunting.Korean_Hunting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
@SequenceGenerator(name = "refresh_seq_gen", sequenceName = "refresh_seq", initialValue = 1, allocationSize = 50)
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(generator = "refresh_seq_gen")
    private Long id;
    
    @Column(name = "rt_value")
    private String value;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    UserEntity userEntity;

    @Builder
    public RefreshToken(String value, UserEntity userEntity) {
        this.value = value;
        this.userEntity = userEntity;
    }

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
