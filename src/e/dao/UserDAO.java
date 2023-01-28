
package e.dao;

import ECUtils.BaseDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 *
 */
public class UserDAO extends BaseDAO{
        @SuppressWarnings("CallToPrintStackTrace")
        public static int validate(String name, String passwd) {
        int res = -1;
        Connection con = null;
        try {
            con = getCon();
            String sql = " select uid from users where uname = ? and pass = ? ";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, name);
            st.setString(i++, passwd);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
               res = rs.getInt("uid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
        return res;
    }
        @SuppressWarnings("CallToPrintStackTrace")
        public static LinkedList<String> getUnames() {
        LinkedList<String> res = new LinkedList();
        res.addFirst("New/Existing User");
        Connection con = null;
        try {
            con = getCon();
            String sql = " select uname from users ";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
               res.add(rs.getString("uname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
        return res;
    } 
       @SuppressWarnings("CallToPrintStackTrace")
        public static void registerUser(String uname, String passwd) {
        Connection con = null;
        try {
            con = getCon();
            String sql = " insert into users ( uname, pass ) "
                                  + " values (  ?   ,  ?   ) ";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, uname);
            st.setString(i++, passwd);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
    }
        
}
