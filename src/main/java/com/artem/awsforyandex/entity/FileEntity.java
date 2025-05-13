package com.artem.awsforyandex.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "files")
@Setter
@Getter
public class FileEntity {
    @Id
    private Long id;
    private String location;
    private Status status;
}
