package com.kh.miniproject.dionysus.Dao;


import com.kh.miniproject.dionysus.Utils.Common;
import com.kh.miniproject.dionysus.Dto.MemberDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    // 회원 가입 여부 확인
    public boolean regMemberCheck(String USER_ID) {
        boolean isNotReg = false;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM MEMBER_TB WHERE ID = " + "'" + USER_ID +"'";
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
            String sql = "SELECT * FROM MEMBER_TB WHERE ID = " + "'" + id + "'";
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
    public List<MemberDTO> memberSelect(String getName) {
        List<MemberDTO> list = new ArrayList<>();
        String sql = null;
        System.out.println("NAME : " + getName);
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            if(getName.equals("ALL")) sql = "SELECT * FROM MEMBER_TB";
            else sql = "SELECT * FROM MEMBER_TB WHERE ID = " + "'" + getName + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                String id = rs.getString("ID");
                String pw = rs.getString("PW");
                String name = rs.getString("NAME");
                String jumin = rs.getString("JUMIN");
                String nick = rs.getString("NICK");
                String phone = rs.getString("PHONE");
                String address = rs.getString("ADDRESS");
                Date join = rs.getDate("JOIN");

                MemberDTO dto = new MemberDTO();
                dto.setId(id);
                dto.setPw(pw);
                dto.setName(name);
                dto.setJumin(jumin);
                dto.setNick(nick);
                dto.setPhone(phone);
                dto.setAddress(address);
                dto.setJoin(join);
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
    public boolean memberRegister(String id, String pwd, String name, String mail) {
        int result = 0;
        String sql = "INSERT INTO MEMBER_TB(ID, PWD, NAME, EMAIL, JOIN) VALUES(?, ?, ?, ?, SYSDATE)";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, id);
            pStmt.setString(2, pwd);
            pStmt.setString(3, name);
            pStmt.setString(4, mail);
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

    public boolean memberDelete(String id) {
        int result = 0;
        String sql = "DELETE FROM MEMBER_TB WHERE ID = ?";

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