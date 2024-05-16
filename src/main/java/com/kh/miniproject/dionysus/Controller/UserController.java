package com.kh.miniproject.dionysus.Controller;

import com.kh.miniproject.dionysus.Dao.UserDAO;
import com.kh.miniproject.dionysus.Dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@Slf4j //롬복내장 디버깅 어노테이션
public class UserController {

    // GET : 회원 조회
    @GetMapping("/search-user")
    public ResponseEntity<List<UserDTO>> memberList(@RequestParam String name) {
        System.out.println("NAME : " + name);
        UserDAO dao = new UserDAO();
        List<UserDTO> list = dao.memberSelect(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // POST : 로그인
    @PostMapping("/login")
    public ResponseEntity<List<UserDTO>> memberLogin(@RequestBody Map<String, String> loginData) {
        String user = loginData.get("id");
        String pw = loginData.get("pw");
        System.out.println("ID : " + user);
        System.out.println("PD : " + pw);
        UserDAO dao = new UserDAO();
        List<UserDTO> result = dao.regMemberCheck(user, pw);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // GET : 가입 여부 확인
    @GetMapping("/check")
    public ResponseEntity<Boolean> memberCheck(@RequestParam String id) {
        System.out.println("회원 가입 여부 확인 ID : " + id);
        UserDAO dao = new UserDAO();
        boolean isTrue = dao.regMemberCheck(id);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }
    // GET : 이름 주민번호 받아서 아이디 조회
    @GetMapping("/findid")
    public ResponseEntity<String> memberList(@RequestParam String name, @RequestParam String jumin) {
        System.out.println("NAME : " + name);
        System.out.println("JUMIN : " + jumin);
        UserDAO dao = new UserDAO();
        String id = dao.findIDMethod(name, jumin);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
    // GET : 아이디 이름 주민번호 받아서 비밀번호
    @GetMapping("/findpw")
    public ResponseEntity<String> memberList(@RequestParam String id, @RequestParam String name, @RequestParam String jumin) {
        System.out.println("ID : " + id);
        System.out.println("NAME : " + name);
        System.out.println("JUMIN : " + jumin);
        UserDAO dao = new UserDAO();
        String pw= dao.findPWMethod(id, name, jumin);
        return new ResponseEntity<>(pw, HttpStatus.OK);
    }
    // POST : 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<Boolean> memberRegister(@RequestBody UserDTO GenerateUser) {

        UserDAO dao = new UserDAO();
        boolean isTrue = dao.userRegister(GenerateUser);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    // POST : 회원 탈퇴
    @PostMapping("/delete-user")
    public ResponseEntity<Boolean> userDeleteMethod(@RequestBody Map<String, String> delUser) {
        String getId = delUser.get("id");
        UserDAO dao = new UserDAO();
        boolean isTrue = dao.userDeleteMethod(getId);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }
}
