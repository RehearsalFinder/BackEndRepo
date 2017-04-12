package com.theironyard.services;

import com.theironyard.entities.RehearsalSpace;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface RehearsalSpaceRepository extends CrudRepository<RehearsalSpace, String> {

    RehearsalSpace findFirstByHostEmail(String email);

    RehearsalSpace findFirstById(String id);

    //ArrayList<RehearsalSpace> findAllByFeaturedEquals(String yes);
}
