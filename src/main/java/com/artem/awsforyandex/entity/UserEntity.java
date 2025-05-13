package com.artem.awsforyandex.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class  UserEntity {
    @Id
    private Long id;
    private String username;
    private String password;
    private UserRole role;
    private Status status;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Transient
    @ToString.Exclude private List<EventEntity> events;

    @ToString.Include(name = "password")
    private String maskPassword() {
        return "********";
    }
}
