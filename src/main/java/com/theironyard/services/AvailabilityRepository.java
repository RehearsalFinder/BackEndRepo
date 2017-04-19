package com.theironyard.services;

import com.theironyard.entities.Availability;
import com.theironyard.entities.RehearsalSpace;
import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface AvailabilityRepository extends CrudRepository<Availability, String> {

    Availability findFirstByClaimedBy(User user);

    Availability findFirstByBelongsTo(RehearsalSpace space);

    Availability findAllByDate(String date);

    ArrayList<Availability> findAllByBelongsTo(RehearsalSpace space);

    Availability findFirstById(String id);

    ArrayList<Availability> findAllByClaimedBy(User user);

}
