package com.CRS.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CRS.Model.CrimeTypeModel;

@Repository
public interface CrimeTypeRepositories extends JpaRepository<CrimeTypeModel, Long>{

}
