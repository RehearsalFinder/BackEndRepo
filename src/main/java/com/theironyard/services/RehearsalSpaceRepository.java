package com.theironyard.services;

import com.theironyard.entities.RehearsalSpace;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface RehearsalSpaceRepository extends CrudRepository<RehearsalSpace, Integer> {

    RehearsalSpace findFirstByHostEmail(String email);

    ArrayList<RehearsalSpace> findAllByIsFeaturedIsTrue();
}
