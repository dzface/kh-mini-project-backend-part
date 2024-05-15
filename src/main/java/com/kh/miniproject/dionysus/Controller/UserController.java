package com.kh.miniproject.dionysus.Controller;

import com.kh.miniproject.dionysus.Dao.UserDAO;
import com.kh.miniproject.dionysus.Dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")

public class UserController {

    // GET : 회원 조회
    @GetMapping("/member")
    public ResponseEntity<List<UserDTO>> memberList(@RequestParam String name) {
        System.out.println("NAME : " + name);
        UserDAO dao = new UserDAO();
        List<UserDTO> list = dao.memberSelect(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // POST : 로그인
    @PostMapping("/login")
    public ResponseEntity<Boolean> memberLogin(@RequestBody Map<String, String> loginData) {
        String user = loginData.get("id");
        String pwd = loginData.get("pwd");
        System.out.println("ID : " + user);
        System.out.println("PWD : " + pwd);
        UserDAO dao = new UserDAO();
        boolean result = dao.loginCheck(user, pwd);
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

    // POST : 회원 가입
    @PostMapping("/new")
    public ResponseEntity<Boolean> memberRegister(@RequestBody Map<String, String> regData) {
        String getId = regData.get("id");
        String getPwd = regData.get("pwd");
        String getName = regData.get("name");
        String getjumin = regData.get("jumin");
        String getnick = regData.get("nick");
        String getphone = regData.get("phone");
        String getaddress = regData.get("address");
        UserDAO dao = new UserDAO();
        boolean isTrue = dao.userRegister(getId, getPwd, getName, getjumin, getnick, getphone, getaddress);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    // POST : 회원 탈퇴
    @PostMapping("/del")
    public ResponseEntity<Boolean> memberDelete(@RequestBody Map<String, String> delData) {
        String getId = delData.get("id");
        UserDAO dao = new UserDAO();
        boolean isTrue = dao.memberDelete(getId);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }
}
