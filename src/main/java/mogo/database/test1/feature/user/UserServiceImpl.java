package mogo.database.test1.feature.user;

import lombok.RequiredArgsConstructor;
import mogo.database.test1.base.BaseFilter;
import mogo.database.test1.domain.User;
import mogo.database.test1.feature.user.dto.UserRequest;
import mogo.database.test1.feature.user.dto.UserResponse;
import mogo.database.test1.feature.user.dto.UserUpdateRequest;
import mogo.database.test1.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.ResponseCache;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BaseFilter<User> userBaseFilter;

    private final MongoTemplate mongoTemplate;

    private final UserMapper userMapper;

    @Override
    public Page<UserResponse> getAllUsers(int pageNumber, int pageSize) {

        Sort sortBy = Sort.by(Sort.Direction.ASC, "name");

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortBy);

        Page<User> userPage = userRepository.findAll(pageRequest);

        return userPage.map(userMapper::toResponse);
    }

    @Override
    public UserResponse getUserByUuid(String uuid) {

        User user =
                userRepository.findByUuid(uuid).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("user = %s has not been found",uuid)));

        return userMapper.toResponse(user);
    }

    @Override
    public void createUser(UserRequest userRequest) {

        User user = userMapper.fromRequest(userRequest);

        user.setUuid(UUID.randomUUID().toString());

        userRepository.save(user);
    }

    @Override
    public UserResponse updateUser(String uuid, UserUpdateRequest userUpdateRequest) {


        User user = userRepository.findByUuid(uuid).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND
                ,String.format("user = %s has not been found",uuid)));

        userMapper.updateUserFromRequest(user,userUpdateRequest);

        userRepository.save(user);

        return  userMapper.toResponse(user);
    }

    @Override
    public void deleteUser(String uuid) {

        User user = userRepository.findByUuid(uuid).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND
                ,String.format("user = %s has not been found",uuid)));

        userRepository.delete(user);

    }

    @Override
    public List<UserResponse> filterUsers(BaseFilter.FilterDto filterDto) {

        Query query = userBaseFilter.buildQuery(filterDto,User.class);

        List<User> users = mongoTemplate.find(query,User.class);

        return users.stream().map(userMapper::toResponse).collect(Collectors.toList());

    }

    private void test(){

        Criteria criteria1 = Criteria.where("name").in("soknem");
        Criteria criteria2 = Criteria.where("age").gt(12);

        Query query = new Query();

        query.addCriteria(criteria1);

        query.addCriteria(criteria2);

        mongoTemplate.find(query,User.class);
    }
}
