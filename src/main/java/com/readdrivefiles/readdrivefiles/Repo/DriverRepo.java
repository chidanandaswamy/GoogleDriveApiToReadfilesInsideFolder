package com.readdrivefiles.readdrivefiles.Repo;

import com.readdrivefiles.readdrivefiles.DTO.Drive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepo extends JpaRepository<Drive, Integer> {
}
