package edu.greenriver.sdev.myspringproject.db;

import edu.greenriver.sdev.myspringproject.models.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOptionRepository extends JpaRepository<Option, Integer> {
}
