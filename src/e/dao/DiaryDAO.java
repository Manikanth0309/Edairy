
package e.dao;

import ECUtils.BaseDAO;
import e.bean.Diary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
/**
 *
 *
 */

public class DiaryDAO extends BaseDAO{
    @SuppressWarnings("CallToPrintStackTrace")
        public static void insertEntry(Diary d) {
        Connection con = null;
        try {
            con = getCon();
            // uid , edate , descrip 
            String sql = " insert into diary ( uid , edate , descrip ) "
                       + " values ( ? , ? , ? ) ";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, d.getUid());
            st.setDate(i++, d.getEdate());
            st.setString(i++, d.getDescrip());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
    }
       @SuppressWarnings("CallToPrintStackTrace")
        public static void updateEntry(Diary d, Diary old) {
        Connection con = null;
        try {
            con = getCon();
            // uid , edate , descrip 
            String sql = " update diary set edate = ?, descrip = ? ";
             sql = sql + " where uid like ? and edate = ? and descrip like ? ";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setDate(i++, d.getEdate());
            st.setString(i++, d.getDescrip());
            st.setString(i++, old.getUid());
            st.setDate(i++, old.getEdate());
            st.setString(i++, old.getDescrip());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
    }
    @SuppressWarnings("CallToPrintStackTrace")
    public static LinkedList<Diary> search(String col, String srch) {
        LinkedList<Diary> res = new LinkedList<>();
        Connection con = null;
        try {
            con = getCon();
            // uid , edate , descrip 
            String sql = "select * from diary ";
//            if(!col.equalsIgnoreCase("DATE")) {
//                sql = sql + col + " like %" + srch + "% ";
//
//            }
            sql = sql +" where descrip like ?";
            if(!srch.isEmpty()){
                sql = sql +" or edate=?";
            }


            PreparedStatement st = con.prepareStatement(sql);
            int i = 2;
            st.setString(1, "%"+srch+"%");
            if(!srch.isEmpty())
                st.setString(2,srch);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Diary d = new Diary();
                d.setUid(rs.getString("uid"));
                d.setEdate(rs.getDate("edate"));
                d.setDescrip(rs.getString("descrip"));
                res.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
        return res;
    }
        @SuppressWarnings("CallToPrintStackTrace")
        public static LinkedList<String> getColumns() {
        LinkedList<String> res = new LinkedList();
        res.addFirst("ALL");
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
        public static void removeEntry(Diary d) {
        Connection con = null;
        try {
            con = getCon();
            // uid , edate , descrip 
            String sql = " delete from diary"
                    + " where uid like ? and descrip like ? ";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, d.getUid());
            st.setString(i++, d.getDescrip());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
    }
        public static LocalDate getDate( String uid, String Desc){
            LocalDate res = null;
            Connection con = null;
            try {
            con = getCon();
            // uid , edate , descrip 
            String sql = " select edate from diary ";
             sql = sql + " where uid like ? and descrip like ? ";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, uid);
            st.setString(i++, Desc);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                res = rs.getDate("edate").toLocalDate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
            return res;
        }
       
    
        
        
}