package com.theironyard.services;
import org.springframework.data.repository.CrudRepository;

import com.theironyard.entities.PhotoPost;

public interface PhotoPostRepository extends CrudRepository<PhotoPost, String> { }
