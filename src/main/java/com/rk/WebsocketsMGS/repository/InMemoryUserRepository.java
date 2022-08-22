package com.rk.WebsocketsMGS.repository;

import com.rk.WebsocketsMGS.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {

    public static final Logger LOGGER = LoggerFactory.getLogger(InMemoryUserRepository.class);

}