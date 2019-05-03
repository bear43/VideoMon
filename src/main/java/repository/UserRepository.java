package repository;

import model.User;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    List<User> findByFirstnameAndSecondnameAndLastname(String firstname, String secondname, String lastname);
    List<User> findByFloor(int floor);
    User findByLogin(String login);
    List<User> findByAdmin(boolean admin);
}
