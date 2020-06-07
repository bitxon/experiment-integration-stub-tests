package com.bitxon.user.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("users")
public class UserDbObject {
    @Id
    private Long id;
    private String tag;
    private String email;
    private LocalDate dateOfBirth;
}
