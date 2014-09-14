package Servlets;

import Modelo.Usuarios;
import Datos.Dusuarios;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet(urlPatterns = {"/Principal"})
public class Principal extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession(true);
        
        try{
            
            RequestDispatcher mw = request.getRequestDispatcher("MarcoWeb");
            mw.include(request, response);
            
            if(session.getAttribute("usr") == null){
                out.println("Bienvenido!");
            }
            else{
                response.sendRedirect("PaginaUno");
            }
            
            RequestDispatcher mwc = request.getRequestDispatcher("MarcoWebPie");
            mwc.include(request, response);
        }
        finally{
            out.close();
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            
        HttpSession session = request.getSession(true);

        try
        {
            String nombre = request.getParameter("usuario");
            String password = request.getParameter("password");
            
            Dusuarios duser = new Dusuarios();
            Usuarios usr = duser.login(nombre,password);

            if (usr != null)
            {
                session.setAttribute("logeado", true);
                session.setAttribute("usuario", usr);
            }else{
                session.setAttribute("mensaje", "Usuario o Contraseña incorrecto, intente otra vez...");
            }
            
            processRequest(request, response);             
        } catch (Exception ex)
        {
            
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
