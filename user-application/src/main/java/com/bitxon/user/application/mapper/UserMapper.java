package com.bitxon.user.application.mapper;

import com.bitxon.user.api.User;
import com.bitxon.user.application.repository.model.DomainUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToApiModel(DomainUser source) {
        if (source == null) {
            return null;
        }
        return User.builder()
                .id(source.getId())
                .tag(source.getTag())
                .dateOfBirth(source.getDateOfBirth())
                .email(source.getEmail())
                .build();

    }

    public DomainUser mapToDomainModel(User source) {
        if (source == null) {
            return null;
        }
        return DomainUser.builder()
                .id(source.getId())
                .tag(source.getTag())
                .dateOfBirth(source.getDateOfBirth())
                .email(source.getEmail())
                .build();
    }
}
