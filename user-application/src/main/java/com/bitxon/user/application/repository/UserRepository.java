package com.bitxon.user.application.repository;

import com.bitxon.user.application.repository.model.DomainUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<DomainUser, String> {
}
