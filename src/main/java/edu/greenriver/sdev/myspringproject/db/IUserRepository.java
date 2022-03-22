package edu.greenriver.sdev.myspringproject.db;

import edu.greenriver.sdev.myspringproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
