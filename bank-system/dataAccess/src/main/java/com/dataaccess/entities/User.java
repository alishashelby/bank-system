package com.dataaccess.entities;

import com.dataaccess.enums.Gender;
import com.dataaccess.enums.HairColor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Public class User represents the entity user.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "hair_color", nullable = false)
    private HairColor hairColor;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Collection<User> friends;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Account> accounts;

    protected User() {
        this.friends = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }
    /**
     * Initializes a new instance of the User class.
     * @param login login of user - String.
     * @param name user's name - String.
     * @param age user's age - int.
     * @param gender user's gender - enum.
     * @param hairColor user's hair color - enum.
     */
    public User(String login, String name, int age, Gender gender, HairColor hairColor) {
        this.login = login;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.hairColor = hairColor;
        this.friends = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

    public UUID getId() { return id; }

    /**
     * Gets user's login.
     * @return String - login.
     */
    public String getLogin() { return this.login; }

    /**
     * Gets user's name.
     * @return String - name.
     */
    public String getName() { return this.name; }

    /**
     * Gets user's age.
     * @return int - age.
     */
    public int getAge() { return this.age; }

    /**
     * Gets user's gender.
     * @return Gender - enum.
     */
    public Gender getGender() { return this.gender; }

    /**
     * Gets user's hair color.
     * @return HairColor - enum.
     */
    public HairColor getHairColor() { return this.hairColor; }

    /**
     * Gets user's friends - entities.
     * @return Collection of entities User.
     */
    @JsonIgnore
    public Collection<User> getFriends() { return this.friends; }

    /**
     * Gets user's friends - logins.
     * @return Collection of String - logins.
     */
    @JsonProperty("friends")
    public Collection<String> getFriendLogins() {
        return friends.stream()
                .map(User::getLogin)
                .collect(Collectors.toList());
    }

    /**
     * Gets user's accounts - entities.
     * @return Collection of entities Account.
     */
    @JsonIgnore
    public Collection<Account> getAccounts() { return this.accounts; }

    /**
     * Gets user's accounts - id.
     * @return Collection of String - id.
     */
    @JsonProperty("accounts")
    public Collection<String> getAccountIds() {
        return accounts.stream()
                .map(account -> String.valueOf(account.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Adds new user's friend to Collection.
     * @param friend entity User.
     */
    public void addFriend(User friend) {
        if (!this.friends.contains(friend)) {
            this.friends.add(friend);
            friend.getFriends().add(this);
        }
    }

    /**
     * Removes friend from the Collection.
     * @param friend entity User.
     */
    public void removeFriend(User friend) {
        if (this.friends.contains(friend)) {
            this.friends.remove(friend);
            friend.getFriends().remove(this);
        }
    }

    /**
     * Boolean method to check is User is friend of us.
     * @param friend entity User to check.
     * @return boolean:
     * true - User is friend,
     * false - User is not friend.
     */
    public boolean isFriend(User friend) {
        for (User friendUser : this.friends) {
            if (friendUser.getId().equals(friend.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds new user's account to Collection.
     * @param account entity Account.
     */
    public void addAccount(Account account) {
        if (!this.accounts.contains(account)) {
            this.accounts.add(account);
            account.setUser(this);
        }
    }
}