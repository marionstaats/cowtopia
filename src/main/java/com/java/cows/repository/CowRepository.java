package com.java.cows.repository;

//Able to use all Mongo methods like save(), find() etc without implementing
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import com.java.cows.model.Cow;


public interface CowRepository extends MongoRepository<Cow, String> {
    List<Cow> findByNameContaining(String name);
    List<Cow> findByPublished(Boolean published);
}
