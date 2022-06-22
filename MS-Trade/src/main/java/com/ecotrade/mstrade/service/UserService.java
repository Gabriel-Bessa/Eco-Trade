package com.ecotrade.mstrade.service;

import com.ecotrade.mstrade.config.errors.BusinessException;
import com.ecotrade.mstrade.config.responses.ResponseDTO;
import com.ecotrade.mstrade.config.security.SecurityUtils;
import com.ecotrade.mstrade.domain.User;
import com.ecotrade.mstrade.domain.dto.UserCreateDTO;
import com.ecotrade.mstrade.domain.dto.UserDetailsDTO;
import com.ecotrade.mstrade.domain.dto.UserFullNoPasswordDTO;
import com.ecotrade.mstrade.domain.dto.UserPasswordDTO;
import com.ecotrade.mstrade.domain.dto.UserUpdateDTO;
import com.ecotrade.mstrade.domain.enuns.AdvertisementStatus;
import com.ecotrade.mstrade.repository.UserRepository;
import com.ecotrade.mstrade.repository.predicate.EcopointsPredicate;
import com.ecotrade.mstrade.service.interfaces.BasicService;
import com.ecotrade.mstrade.service.interfaces.ReliabilityBO;
import com.ecotrade.mstrade.service.mapper.UserMapper;
import com.ecotrade.mstrade.service.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService, BasicService, ReliabilityBO {

    @Value("${ecotrade.ecopoints.quantity}")
    private String defaultEcoPoints;
    private final String DEFAULT_USER_IMAGE_URL = "https://eco-trade.s3.amazonaws.com/assets/default.svg";
    private final UserRepository repository;
    private final UserMapper mapper;
    private final BucketService bucketService;
    private final UploadFileService uploadFileService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EntityManager em;

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> getException("user.not.found"));
    }

    public User findByid(Long id) {
        return repository.findById(id).orElseThrow(() -> getException("user.not.found"));
    }

    @Transactional
    public ResponseDTO create(UserCreateDTO dto, MultipartFile file) {
        User user = mapper.toEntity(dto);
        if (repository.existsAppUsersByEmailOrPhone(dto.getEmail(), dto.getPhone())) {
            throw getException("user.email.alredy.exists");
        }
        bindUserPhoto(user, file);
        user.setPassword(dto.getPassword());
        save(user);
        return getSuccess("user.created");
    }

    private void bindUserPhoto(User user, MultipartFile file) {
        if(file != null && !file.isEmpty()) {
            user.setPhotos(Collections.singletonList(bucketService.upload(file)));
            return;
        }
        user.setPhotos(Collections.singletonList(uploadFileService.create(DEFAULT_USER_IMAGE_URL, "default.svg")));
    }

    public UserFullNoPasswordDTO me() {
        UserFullNoPasswordDTO userDTO = mapper.toNoPasswordDTO(findByEmail(SecurityUtils.getAuthEmail()));
        userDTO.setEcopoints(getEcopoints());
        return userDTO;
    }

    public Double getReliabilityOfAuthUser(Long id) {
        return getReliabilityOfUser(id);
    }

    public Double getReliabilityOfUser(Long id) {
        List<Tuple> tuples = repository.reliabilityByUser(id);
        Map<AdvertisementStatus, Integer> reliabilities = new HashMap<>();
        tuples.forEach(it -> reliabilities.put(AdvertisementStatus.valueOf((String) it.get(1)), ((BigInteger) it.get(0)).intValue()));
        return calcReliability(reliabilities);
    }

    private User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public BusinessException getException(String message) {
        return new BusinessException(MessageUtils.USER_EXCEPTION, message);
    }

    @Override
    public ResponseDTO getSuccess(String message) {
        return new ResponseDTO(MessageUtils.USER_SUCCESS, message);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);
        Collection<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return new UserDetailsDTO(user.getId(), user.getEmail(), user.getPassword(), authorities);
    }

    public Collection<SimpleGrantedAuthority> myRoles(String email) {
        User user = findByEmail(email);
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Transactional
    public ResponseDTO changePassword(UserPasswordDTO passwordDTO) {
        if (!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
            throw getException("user.password.not.equals");
        }
        if (passwordDTO.getConfirmPassword().length() < 6) {
            throw getException("user.password.short.size");
        }
        User loggedUser = findByEmail(SecurityUtils.getAuthEmail());
        loggedUser.setPassword(passwordDTO.getNewPassword());
        save(loggedUser);
        return getSuccess("user.updated");
    }

    public Double getEcopoints() {
        Object ecopoints = new EcopointsPredicate(em).getEcopoints(SecurityUtils.getAuthUser().getId(), Double.parseDouble(defaultEcoPoints));
        return Double.parseDouble(ecopoints.toString());
    }

    public User getAuthUser() {
        return findByEmail(SecurityUtils.getAuthEmail());
    }

    public UserDetailsDTO findById(Long id) {
        return mapper.toUserDetailsDto(findByid(id));
    }

    @Transactional
    public ResponseDTO update(UserUpdateDTO user) {
        UserDetailsDTO userById = findById(user.getId());
        if( !SecurityUtils.getAuthEmail().equals(userById.getUsername()) ) {
            throw getException("user.is.not.you");
        }
        repository.save(mapper.toEntity(user));
        return getSuccess("user.updated");
    }
}
