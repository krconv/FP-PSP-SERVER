package py.org.fundacionparaguaya.pspserver.security.services.impl;

import com.google.common.collect.ImmutableMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import py.org.fundacionparaguaya.pspserver.common.exceptions.CustomParameterizedException;
import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDTO;
import py.org.fundacionparaguaya.pspserver.security.entities.UserEntity;
import py.org.fundacionparaguaya.pspserver.security.mapper.UserMapper;
import py.org.fundacionparaguaya.pspserver.security.repositories.UserRepository;
import py.org.fundacionparaguaya.pspserver.security.services.UserService;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class UserServiceImpl implements UserService {

	private Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserRepository userRepository;

	private final UserMapper userMapper;

	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

	
	@Override
	public UserDTO addUser(UserDTO userDTO) {
        userRepository.findOneByUsername(userDTO.getUsername())
                .ifPresent((user) -> {
                    throw new CustomParameterizedException(
                            "User already exists.",
							new ImmutableMultimap.Builder<String, String>().
                                    put("username", user.getUsername()).
                                    build().asMap()
                    );
                });
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(userDTO, user);
        UserEntity newUser = userRepository.save(user);
	    return userMapper.entityToDto(newUser);
	}
	
	
	@Override
	public UserDTO getUserById(Long userId) {
		checkArgument(userId > 0, "Argument was %s but expected nonnegative", userId);

        return Optional.ofNullable(userRepository.findOne(userId))
                .map(userMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("User does not exist"));
	}

	
	@Override
	public List<UserDTO> getAllUsers() {
		List<UserEntity> users = userRepository.findAll();
		return userMapper.entityListToDtoList(users);
	}

	
	@Override
	public void deleteUser(Long userId) {
        checkArgument(userId > 0, "Argument was %s but expected nonnegative", userId);

        Optional.ofNullable(userRepository.findOne(userId))
                .ifPresent(user -> {
                    userRepository.delete(user);
                    LOG.debug("Deleted User: {}", user);
                });
    }

	
	@Override
	public UserDTO updateUser(Long userId, UserDTO userDTO) {
		checkArgument(userId > 0, "Argument was %s but expected nonnegative", userId);

		return Optional.ofNullable(userRepository.findOne(userId))
                .map(user -> {
                    BeanUtils.copyProperties(userDTO, user);
                    LOG.debug("Changed Information for User: {}", user);
                    return user;
                })
                .map(userMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("User does not exist"));
    }

	
}
