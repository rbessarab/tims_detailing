package edu.greenriver.sdev.myspringproject.db;

import edu.greenriver.sdev.myspringproject.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthorityRepository extends JpaRepository<Authority, Integer> {
}