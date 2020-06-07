package com.bitxon.user.application.service;

import com.bitxon.user.api.User;
import com.bitxon.user.application.client.tag.TagServiceClient;
import com.bitxon.user.application.repository.UserRepository;
import com.bitxon.user.application.repository.model.DomainUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TagServiceClient tagServiceClient;

    public List<DomainUser> search() {
        return userRepository.findAll();
//        return List.of(
//                DomainUser.builder().id(1L).email("first@mail.com").dateOfBirth(LocalDate.of(1991, 1,1)).build(),
//                DomainUser.builder().id(2L).email("second@mail.com").dateOfBirth(LocalDate.of(1992, 2,2)).build(),
//                DomainUser.builder().id(3L).email("third@mail.com").dateOfBirth(LocalDate.of(1993, 3,3)).build()
//        );
    }

    public DomainUser save(DomainUser userToSave) {
        userToSave.setId(17L);
        userToSave.setTag(tagServiceClient.getTag());
        return userRepository.save(userToSave);
    }
}
