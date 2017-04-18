package com.theironyard.services;
import com.theironyard.entities.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<Photo, String> {

    Photo findFirstByUserId(String id);
}
