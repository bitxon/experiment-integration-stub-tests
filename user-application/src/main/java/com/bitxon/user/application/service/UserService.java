package com.bitxon.user.application.service;

import com.bitxon.user.api.User;
import com.bitxon.user.application.client.tag.TagServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private TagServiceClient tagServiceClient;

    public List<User> search() {
        return List.of(
                User.builder().id(1L).email("first@mail.com").dateOfBirth(LocalDate.of(1991, 1,1)).build(),
                User.builder().id(2L).email("second@mail.com").dateOfBirth(LocalDate.of(1992, 2,2)).build(),
                User.builder().id(3L).email("third@mail.com").dateOfBirth(LocalDate.of(1993, 3,3)).build()
        );
    }

    public User save(User userToSave) {
        userToSave.setId(17L);
        userToSave.setTag(tagServiceClient.getTag());
        return userToSave;
    }
}
