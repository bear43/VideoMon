package service;

import lombok.Data;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.List;

@Service
@Data
public class UserService
{
    private final UserRepository userRepository;

    private User currentUser;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public void createNewUser(User user) throws Exception
    {
        User usr = findByLogin(user.getLogin());
        if(usr != null) throw new Exception("User already defined!");
        userRepository.saveAndFlush(user);
    }

    public void deleteUser(User user) throws Exception
    {
        userRepository.delete(user);
    }

    public List<User> findByFSL(String firstname, String secondname, String lastname)
    {
        return userRepository.findByFirstnameAndSecondnameAndLastname(firstname, secondname, lastname);
    }

    public List<User> findByFloor(int floor)
    {
        return userRepository.findByFloor(floor);
    }

    public List<User> findAdmins()
    {
        return userRepository.findByAdmin(true);
    }

    public User findByLogin(String login)
    {
        return userRepository.findByLogin(login);
    }

    public void fillCurrentUser(String login) throws Exception
    {
        User user = findByLogin(login);
        if(user == null) throw new Exception("Cannot fill user with login: " + login + ". No such login.");
        currentUser = user;
    }

    public void saveAndFlush()
    {
        userRepository.saveAndFlush(currentUser);
    }
}
