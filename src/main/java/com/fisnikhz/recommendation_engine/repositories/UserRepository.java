package com.fisnikhz.recommendation_engine.repositories;

import com.fisnikhz.recommendation_engine.entities.Users;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NotFound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @NonNull
    Optional<Users> findById(  @NonNull Long  id);

}
