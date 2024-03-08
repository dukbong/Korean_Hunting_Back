package com.hangulhunting.Korean_Hunting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    
    @Column(name = "rt_key")
    private String key;

    @Column(name = "rt_value")
    private String value;

    @Builder
    public RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
