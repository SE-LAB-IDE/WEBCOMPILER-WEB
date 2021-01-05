package bulletinBoard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DBconnection.*;
import java.util.Collections;

public class bulletinBoardDAO {

   public boolean insertPost(bulletinBoardVO vo) { // �Խñ� ����
      boolean result = false;
      Connection conn = null;
      PreparedStatement pstmt = null;

      try {
         conn = DBconnection.getConnection();

            String sql = "INSERT INTO board (id, Name, text) VALUES (?,?,?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, vo.getId());
            pstmt.setString(2, vo.getName());
            pstmt.setString(3, vo.getText());

            pstmt.executeUpdate();

            result = true;
         
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (conn != null)
               conn.close();
            if (pstmt != null)
               pstmt.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      return result;
   }
   
   public ArrayList<forPostList> getPostList() { // name,id,postNum,time ������ ��ü List�� ��ȯ
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      ArrayList<forPostList> postList = new ArrayList<forPostList>();
      
      try {
         conn = DBconnection.getConnection();
         
         String sql = "select id, name, postNum, time from board";
            pstmt= conn.prepareStatement(sql);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()){
               forPostList tmp = new forPostList();
               tmp.setId(rs.getString("id"));
               tmp.setName(rs.getString("Name"));
               tmp.setNum(Integer.parseInt(rs.getString("postNum")));
               tmp.setTime(rs.getString("time"));
               postList.add(tmp);
               }
         
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (conn != null)
               conn.close();
            if (pstmt != null)
               pstmt.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      
      Collections.reverse(postList);
      return postList;
   }
   public ArrayList<forPostList> getPostList(String id, int n) { 
      // id�� ���� �˻������� ��� , / int n �� �׳� �������̵带 ����  / ��ü List�� ��ȯ
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      ArrayList<forPostList> postList = new ArrayList<forPostList>();
      
      try {
         conn = DBconnection.getConnection();

         String sql = "select * from board where id=?";
            pstmt= conn.prepareStatement(sql);
            
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            
            while(rs.next()){
               forPostList tmp = new forPostList();
               tmp.setId(rs.getString("id"));
               tmp.setName(rs.getString("Name"));
               tmp.setNum(Integer.parseInt(rs.getString("postNum")));
               tmp.setTime(rs.getString("time"));
               postList.add(tmp);
               }
         
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (conn != null)
               conn.close();
            if (pstmt != null)
               pstmt.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      Collections.reverse(postList);
      return postList;
   }
   public ArrayList<forPostList> getPostList(String title) { // ������ ���� �˻� ������ ���/ ��ü List�� ��ȯ
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      ArrayList<forPostList> postList = new ArrayList<forPostList>();
      
      try {
         conn = DBconnection.getConnection();

         String sql = "select * from board where name=?";
            pstmt= conn.prepareStatement(sql);
            
            pstmt.setString(1, title);
            rs = pstmt.executeQuery();
            
            while(rs.next()){
               forPostList tmp = new forPostList();
               tmp.setId(rs.getString("id"));
               tmp.setName(rs.getString("Name"));
               tmp.setNum(Integer.parseInt(rs.getString("postNum")));
               tmp.setTime(rs.getString("time"));
               postList.add(tmp);
               }
         
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (conn != null)
               conn.close();
            if (pstmt != null)
               pstmt.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      Collections.reverse(postList);
      return postList;
   }
   public bulletinBoardVO getPost(int num) { // �� ��ȣ�� �޾� ���� ��������� ��ȯ
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      bulletinBoardVO vo = new bulletinBoardVO();
      
      try {
         conn = DBconnection.getConnection();

         String sql = "select id, name, text, star, postNum from board where postNum=?";
            
         pstmt= conn.prepareStatement(sql);
         pstmt.setInt(1, num);
         
          rs = pstmt.executeQuery();
            
            while(rs.next()){
               vo.setId(rs.getString("id"));
               vo.setName(rs.getString("name"));
               vo.setText(rs.getString("text"));
               vo.setNum(rs.getInt("postNum"));
               vo.setStar(rs.getInt("star"));
               }
         
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (conn != null)
               conn.close();
            if (pstmt != null)
               pstmt.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      return vo;
   }
   
   public boolean deletePost(int postNum) { // ���� ��ȣ�� �޾� �Խñ��� ����
      boolean result = false;
      Connection conn = null;
      PreparedStatement pstmt = null;

      try {
         conn = DBconnection.getConnection();

            String sql = "delete from board where postNum=? ";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, postNum);

            pstmt.executeUpdate();
            result = true;

         
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (conn != null)
               conn.close();
            if (pstmt != null)
               pstmt.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      return result;
   }
   
   public boolean updatePost(int postNum, String text) { // ���� ��ȣ�� ������ �޾� ���� ������ ����
      boolean result = false;
      Connection conn = null;
      PreparedStatement pstmt = null;

      try {
         conn = DBconnection.getConnection();

            String sql = "UPDATE board SET text=? WHERE postNum=?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, text);
            pstmt.setInt(2, postNum);

            pstmt.executeUpdate();
            result = true;

         
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (conn != null)
               conn.close();
            if (pstmt != null)
               pstmt.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      return result;
   }
   
   public int checkStar(int postNum, String id) { // �Խñ��� ��õ�� ������ �޼ҵ�
      int result = 0;
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      try {
         conn = DBconnection.getConnection();

            String sql = "select id from star where postNum=? and id=?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, postNum);
            pstmt.setString(2, id);
            
            rs = pstmt.executeQuery();
            
            if(rs.next()==false)//�ش� id�� ��õ�� �ȴ��� ���ִٸ�  -1�� ��ȯ
               result = -1;
            
            else//�ش� id�� ��õ�� �����ٸ�  1�� ��ȯ
               result = 1;
            
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (conn != null)
               conn.close();
            if (pstmt != null)
               pstmt.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      return result;
   }
   
   public int changeStar(int postNum, String id) { // �Խñ��� ��õ�� �����ϴ� �޼ҵ�
      int result = 0;
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      try {
         conn = DBconnection.getConnection();

         int check = checkStar(postNum,id);
         String sql="";
         
            if(check==-1){//�ش� id�� ��õ�� �ȴ��� ���ִٸ� DB�� id�� ����ϰ� 1�� ��ȯ
               sql = "insert into star(id, postNum) values(?,?)";
               result = 1;
            }
            else{//�ش� id�� ��õ�� �����ٸ� id�� DB���� �����ϰ� -1�� ��ȯ
               sql = "delete from star where id=? and postNum=?";
               result = -1;
            }
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, id);
            pstmt.setInt(2, postNum);
            
            pstmt.executeUpdate();
         
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (conn != null)
               conn.close();
            if (pstmt != null)
               pstmt.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      return result;
   }
   
   public int star(int postNum, String id) {// �Խñ��� ��õ���� �����Ͽ� DB�� �����ϴ� �޼ҵ�
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      int result= 0;
      try {
         conn = DBconnection.getConnection();

            String sql = "select star from board where postNum=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, postNum);
            
            rs = pstmt.executeQuery();
            rs.next();
            int star = rs.getInt("star"); // �ش� �Խñ��� ��õ���� �޾ƿ´�.
            int checkStar=changeStar(postNum, id);//checkStar�� �̿��Ͽ� �ش� id�� ��õ�� �ߴ��� ���ߴ��� Ȯ��
            result = checkStar;
            star +=checkStar;
            //check�� ���� return ���� ���� ���� DB�� �ٽ� �־���
            sql = "update board set star=? where Postnum=?";
            
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, star);
            pstmt.setInt(2, postNum);
            pstmt.executeUpdate();
         
            
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (conn != null)
               conn.close();
            if (pstmt != null)
               pstmt.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      return result;
   }
}
