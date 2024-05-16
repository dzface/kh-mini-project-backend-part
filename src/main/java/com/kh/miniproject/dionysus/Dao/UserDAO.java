package com.kh.miniproject.dionysus.Dao;


import com.kh.miniproject.dionysus.Utils.Common;
import com.kh.miniproject.dionysus.Dto.UserDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    // 회원 가입 여부 확인
    public boolean regMemberCheck(String id) {
        boolean isNotReg = false;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM MEMBER_TB WHERE USER_ID = " + "'" + id +"'";
            rs = stmt.executeQuery(sql);
            if(rs.next()) isNotReg = false;
            else isNotReg = true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return isNotReg; // 가입 되어 있으면 false, 가입이 안되어 있으면 true
    }
    // 로그인 체크
    public boolean loginCheck(String id, String pw) {
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement(); // Statement 객체 얻기
            String sql = "SELECT * FROM MEMBER_TB WHERE USER_ID = " + "'" + id + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) { // 읽은 데이타가 있으면 true
                String sqlId = rs.getString("ID"); // 쿼리문 수행 결과에서 ID값을 가져 옴
                String sqlPw = rs.getString("PW");
                System.out.println("ID : " + sqlId);
                System.out.println("PWD : " + sqlPw);
                if(id.equals(sqlId) && pw.equals(sqlPw)) {
                    Common.close(rs);
                    Common.close(stmt);
                    Common.close(conn);
                    return true;
                }
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 회원정보 조회
    public List<UserDTO> memberSelect(String getName) {
        List<UserDTO> list = new ArrayList<>();
        String sql = null;
        System.out.println("NAME : " + getName);
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            if(getName.equals("ALL")) sql = "SELECT * FROM MEMBER_TB";
            else sql = "SELECT * FROM MEMBER_TB WHERE USER_NAME = " + "'" + getName + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                String id = rs.getString("USER_ID");
                String pw = rs.getString("USER_PW");
                String name = rs.getString("USER_NAME");
                String jumin = rs.getString("USER_JUMIN");
                String nick = rs.getString("USER_NICK");
                String phone = rs.getString("USER_PHONE");
                String address = rs.getString("USER_ADDRESS");
                UserDTO dto = new UserDTO();
                dto.setId(id);
                dto.setPw(pw);
                dto.setName(name);
                dto.setJumin(jumin);
                dto.setNick(nick);
                dto.setPhone(phone);
                dto.setAddress(address);
                list.add(dto);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    //아이디 찾기
    public List<UserDTO> findIDMethod(String getName, String getJumin) {
        List<UserDTO> list = new ArrayList<>();
        String sql = null;
        System.out.println("NAME : " + getName);
        System.out.println("JUMIN : " + getJumin);
        try {
            conn = Common.getConnection();
            if(getName.equals("ALL")) sql = "SELECT * FROM MEMBER_TB";
            else {
                sql = "SELECT USER_ID FROM MEMBER_TB WHERE USER_NAME = ? AND USER_JUMIN=?";
                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, getName);
                pStmt.setString(2, getJumin);
                rs = pStmt.executeQuery();
            }

            while(rs.next()) {
                String id = rs.getString("USER_ID");
                UserDTO dto = new UserDTO();
                dto.setId(id);
                list.add(dto);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // 비밀번호 찾기
    public List<UserDTO> findPWMethod(String getId, String getName, String getJumin) {
        List<UserDTO> list = new ArrayList<>();
        String sql = null;
        System.out.println("ID : "+ getId);
        System.out.println("NAME : " + getName);
        System.out.println("JUMIN : " + getJumin);
        try {
            conn = Common.getConnection();

            if(getName.equals("ALL")) {
                sql = "SELECT * FROM MEMBER_TB";
                pStmt = conn.prepareStatement(sql);
            }
            else {
                sql = "SELECT USER_ID FROM MEMBER_TB WHERE USER_ID = ? AND USER_NAME = ? AND USER_JUMIN=?";
                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, getId);
                pStmt.setString(2, getName);
                pStmt.setString(3, getJumin);
                rs = pStmt.executeQuery();
            }
            while(rs.next()) {
                String pw = rs.getString("USER_PW");
                UserDTO dto = new UserDTO();
                dto.setPw(pw);
                list.add(dto);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // 회원 가입
    public boolean userRegister(UserDTO user) {
        int result = 0;
        String sql = "INSERT INTO MEMBER_TB(USER_ID, USER_PW, USER_NAME, USER_JUMIN, USER_NICK, USER_PHONE, USER_ADDRESS) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, user.getId());
            pStmt.setString(2, user.getPw());
            pStmt.setString(3, user.getName());
            pStmt.setString(4, user.getJumin());
            pStmt.setString(5, user.getNick());
            pStmt.setString(6, user.getPhone());
            pStmt.setString(7, user.getAddress());
            result = pStmt.executeUpdate();
            System.out.println("회원 가입 DB 결과 확인 : " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);

        if(result == 1) return true;
        else return false;
    }

    public boolean userDeleteMethod(String id) {
        int result = 0;
        String sql = "DELETE FROM MEMBER_TB WHERE USER_ID = ?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, id);
            result = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }
}