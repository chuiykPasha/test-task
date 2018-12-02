package test.task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.task.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
}
