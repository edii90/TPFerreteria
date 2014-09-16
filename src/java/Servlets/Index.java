package Servlets;

import Datos.Dusuarios;
import Modelo.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/Index"})
public class Index extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        try {
            RequestDispatcher mw = request.getRequestDispatcher("MarcoWeb");
            mw.include(request, response);
            
            Usuarios user = (Usuarios) session.getAttribute("User");
            
            if (session.getAttribute("Mensaje") != null) {
                out.println(session.getAttribute("Mensaje"));
                session.removeAttribute("Mensaje");
            }
            if (user != null) {
                out.println("Bienvenido " + user.getNombre());
            }
            else{
                    out.println("<div class='DivCentrado'>Ferreteria \"Kwik E MART\"</div>");
            }
            RequestDispatcher mwc = request.getRequestDispatcher("MarcoWebPie");
            mwc.include(request, response);

        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        try {
            if (session.getAttribute("Mensaje") == null) {

                if (session.getAttribute("User") != null) {
                    session.invalidate();
                } else {

                    Dusuarios Dusr = new Dusuarios();

                    String Nombre = (String) request.getParameter("usuario");
                    String Pass = (String) request.getParameter("password");

                    Usuarios User = Dusr.login(Nombre, Pass);

                    if (User != null) {
                        session.setAttribute("User", User);
                        session.removeAttribute("Mensaje");
                        response.sendRedirect("Menu");
                    } else {
                        session.setAttribute("Mensaje", "Error Usuario o Contraseña");
                    }
                }
            }
            processRequest(request, response);
        } catch (Exception ex) {
            session.setAttribute("Mensaje", "Error Usuario o Contraseña");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
