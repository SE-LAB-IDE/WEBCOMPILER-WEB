package rsa;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
/**
 * Servlet implementation class LoginFormServlet
 */

@WebServlet("/LoginFormServlet")
public class LoginFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 public static final int KEY_SIZE = 2048;
	 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginFormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 try {
             KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
             generator.initialize(KEY_SIZE);
              
             KeyPair keyPair = generator.genKeyPair();
             KeyFactory keyFactory = KeyFactory.getInstance("RSA");
  
             PublicKey publicKey = keyPair.getPublic();
             PrivateKey privateKey = keyPair.getPrivate();
  
             HttpSession session = request.getSession();
             // ���ǿ� ����Ű�� ���ڿ��� Ű���Ͽ� ����Ű�� �����Ѵ�.
             session.setAttribute("__rsaPrivateKey__", privateKey);
  
             // ����Ű�� ���ڿ��� ��ȯ�Ͽ� JavaScript RSA ���̺귯�� �Ѱ��ش�.
             RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
  
             String publicKeyModulus = publicSpec.getModulus().toString(16);
             String publicKeyExponent = publicSpec.getPublicExponent().toString(16);
  
             request.setAttribute("publicKeyModulus", publicKeyModulus);
             request.setAttribute("publicKeyExponent", publicKeyExponent);
             
             request.getRequestDispatcher("loginForm.jsp").forward(request, response);
         } catch (Exception ex) {
             throw new ServletException(ex.getMessage(), ex);
         }
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

}