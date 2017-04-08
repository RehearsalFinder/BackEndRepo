package com.theironyard.services;

import com.theironyard.entities.RehearsalSpace;
import org.springframework.data.repository.CrudRepository;

public interface RehearsalSpaceRepository extends CrudRepository<RehearsalSpace, Integer> {

    RehearsalSpace findFirstByHostEmail(String email);

//    RehearsalSpace find
}
