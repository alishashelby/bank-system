package com.businesslogic.services.user;

import com.businesslogic.dto.kafka.KafkaEvent;
import com.businesslogic.dto.kafka.UpdateDTO;
import com.businesslogic.exceptions.BLException;
import com.businesslogic.exceptions.user.*;
import com.businesslogic.services.KafkaProducer;
import com.dataaccess.entities.User;
import com.dataaccess.enums.Gender;
import com.dataaccess.enums.HairColor;
import com.dataaccess.enums.KafkaEventType;
import com.dataaccess.enums.UserOperation;
import com.dataaccess.repositories.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Public class UserService represents service to work with users.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final KafkaProducer kafkaProducer;

    /**
     * Initializes a new instance of the UserService class.
     * @param userRepository the in-memory repository from DAL.
     */
    @Autowired
    public UserService(UserRepository userRepository,
                       KafkaProducer kafkaProducer) {
        this.userRepository = userRepository;
        this.kafkaProducer = kafkaProducer;
    }

    /**
     * Void method to create user.
     * @param login login of user - String.
     * @param name user's name - String.
     * @param age user's age - int.
     * @param gender user's gender - String.
     * @param hairColor user's hair color - String.
     * @throws BLException UserExistsException
     */
    @Transactional
    public void createUser(String login, String name, int age,
                           String gender, String hairColor) throws BLException {
        if (userRepository.findByLogin(login).isPresent()) {
            throw new UserExistsException(login);
        }

        var user = new User(login, name, age, Gender.valueOf(gender), HairColor.valueOf(hairColor));
        userRepository.save(user);

        kafkaProducer.sendClientMessage(new KafkaEvent(user.getLogin(),
                KafkaEventType.USER_CREATED.toString(), user));
    }

    /**
     * Gets entity User information.
     * @param login login of user - String.
     * @return String - string value of json.
     * @throws Exception UserNotFoundException or JsonProcessingException.
     */
    public User getUserInfo(String login) throws Exception {
        var optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(login);
        }

        return optionalUser.get();
    }

    /**
     * Void method to make friends.
     * @param userLogin login of user - String.
     * @param friendLogin login of friend - String.
     * @throws BLException maybe thrown:
     * MyselfFriendException
     * UserNotFoundException
     * AlreadyFriendsException
     */
    @Transactional
    public void makeFriends(String userLogin, String friendLogin) throws BLException {
        if (userLogin.equals(friendLogin)) {
            throw new MyselfFriendException(userLogin);
        }
        var optionalUser = userRepository.findByLogin(userLogin);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(userLogin);
        }
        var user = optionalUser.get();

        var optionalFriend = userRepository.findByLogin(friendLogin);
        if (optionalFriend.isEmpty()) {
            throw new UserNotFoundException(friendLogin);
        }
        var friend = optionalFriend.get();

        if (user.isFriend(friend)) {
            throw new AlreadyFriendsException(friendLogin);
        }

        user.addFriend(friend);
        friend.addFriend(user);
        userRepository.save(user);
        userRepository.save(friend);

        kafkaProducer.sendClientMessage(new KafkaEvent(user.getLogin(),
                KafkaEventType.USER_UPDATED.toString(),
                new UpdateDTO(UserOperation.ADD_FRIEND.toString(), friend.getLogin())));

        kafkaProducer.sendClientMessage(new KafkaEvent(friend.getLogin(),
                KafkaEventType.USER_UPDATED.toString(),
                new UpdateDTO(UserOperation.ADD_FRIEND.toString(), user.getLogin())));
    }

    /**
     * Void method to unmake friends.
     * @param userLogin login of user - String.
     * @param friendLogin login of friend - String.
     * @throws BLException maybe thrown:
     * MyselfFriendException
     * UserNotFoundException
     * AlreadyUnfriendsException
     */
    @Transactional
    public void unmakeFriends(String userLogin, String friendLogin) throws BLException {
        if (userLogin.equals(friendLogin)) {
            throw new MyselfFriendException(userLogin);
        }
        var optionalUser = userRepository.findByLogin(userLogin);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(userLogin);
        }
        var user = optionalUser.get();

        var optionalFriend = userRepository.findByLogin(friendLogin);
        if (optionalFriend.isEmpty()) {
            throw new UserNotFoundException(friendLogin);
        }
        var friend = optionalFriend.get();

        if (!user.isFriend(friend)) {
            throw new AlreadyUnfriendsException(friendLogin);
        }

        user.removeFriend(friend);
        friend.removeFriend(user);
        userRepository.save(user);
        userRepository.save(friend);

        kafkaProducer.sendClientMessage(new KafkaEvent(user.getLogin(),
                KafkaEventType.USER_UPDATED.toString(),
                new UpdateDTO(UserOperation.REMOVE_FRIEND.toString(), friend.getLogin().toString())));

        kafkaProducer.sendClientMessage(new KafkaEvent(friend.getLogin(),
                KafkaEventType.USER_UPDATED.toString(),
                new UpdateDTO(UserOperation.REMOVE_FRIEND.toString(), user.getLogin())));
    }

    public Collection<String> getAllFriendsByUserId(UUID userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(userId.toString());
        }

        return optionalUser.get().getFriendLogins();
    }

    public Collection<String> getUsersFiltered(HairColor hairColor, Gender gender) {
        return userRepository.findByHairColorAndGender(hairColor, gender);
    }
}