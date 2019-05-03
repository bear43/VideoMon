package model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "usr")
public class User
{
    @Id
    @GeneratedValue
    private Long id;

    private String login;

    private String password;

    private boolean active;

    private int authority;

    private String firstname;

    private String secondname;

    private String lastname;

    private int floor;
    @Column(name = "priv")
    private boolean admin;

    public User()
    {}

    public User(String login, String password, boolean active, int authority, String firstname, String secondname, String lastname, int floor, boolean isAdmin)
    {
        this.login = login;
        this.password = password;
        this.active = active;
        this.authority = authority;
        this.firstname = firstname;
        this.secondname = secondname;
        this.lastname = lastname;
        this.floor = floor;
        this.admin = isAdmin;
    }

    public User(String login, String password, String firstname, String secondname, String lastname, int floor, boolean isAdmin)
    {
        this(login, password, true, 0, firstname, secondname, lastname, floor, isAdmin);
    }

    public User(String login, String password, String firstname, String secondname, String lastname, int floor)
    {
        this(login, password, firstname, secondname, lastname, floor, false);
    }

    public User(String firstname, String secondname, String lastname, int floor)
    {
        this("", "", firstname, secondname, lastname, floor);
    }

    public String toString()
    {
        return String.format("%s %s %s", secondname, firstname, lastname);
    }
}
