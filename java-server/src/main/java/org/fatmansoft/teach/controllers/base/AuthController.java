package org.fatmansoft.teach.controllers.base;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.fatmansoft.teach.models.base.EUserType;
import org.fatmansoft.teach.models.base.User;
import org.fatmansoft.teach.models.base.UserType;
import org.fatmansoft.teach.models.information.Student;
import org.fatmansoft.teach.models.information.StudentInf;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.base.UserTypeRepository;
import org.fatmansoft.teach.repository.information.StudentInfRepository;
import org.fatmansoft.teach.repository.information.StudentRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.fatmansoft.teach.payload.request.LoginRequest;
import org.fatmansoft.teach.payload.request.SignupRequest;
import org.fatmansoft.teach.payload.response.JwtResponse;
import org.fatmansoft.teach.payload.response.MessageResponse;
import org.fatmansoft.teach.repository.base.UserRepository;
import org.fatmansoft.teach.security.jwt.JwtUtils;
import org.fatmansoft.teach.security.services.UserDetailsImpl;
import org.yaml.snakeyaml.Yaml;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTypeRepository userTypeRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentInfRepository studentInfRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private ResourceLoader resourceLoader;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles.get(0)));
    }

    private Integer getId(Integer id){
        return id == null ? 1 : id + 1;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        User user = new User();
        user.setUserId(getId(userRepository.getMaxId("userId")));
        user.setUserName(signUpRequest.getUsername());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setUserType(userTypeRepository.findByName(EUserType.ROLE_USER));
        Student student = new Student();
        student.setStudentId(getId(studentRepository.getMaxId("studentId")));
        student.setStudentName(signUpRequest.getStudentName());
        student.setStudentNum(signUpRequest.getStudentNum());
        student.setUser(user);
        StudentInf studentInf = new StudentInf();
        studentInf.setStudentInfId(getId(studentInfRepository.getMaxId("studentInfId")));
        studentInf.setStudent(student);

        userRepository.save(user);
        studentRepository.save(student);
        studentInfRepository.save(studentInf);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    @PostMapping("/getUimsConfig")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse getUimsConfig(@Valid @RequestBody DataRequest dataRequest) {
        Map data = new HashMap();
        InputStream in = null;
        try {
            Yaml yaml = new Yaml();
            Resource resource = resourceLoader.getResource("classpath:uims.yml");
            in = resource.getInputStream();
            Map map = yaml.load(in);
            Map ui = (Map) map.get("uims");
            data = (Map) ui.get(dataRequest.getString("role"));
        }catch(Exception e){
            System.out.println("Load yaml error!");
        }
        return CommonMethod.getReturnData(data);
    }

}
