package Servlets;

import Modelo.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Menu", urlPatterns = {"/Menu"})
public class Menu extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        try{
            if(session.getAttribute("User") != null){
                RequestDispatcher mwc = request.getRequestDispatcher("MarcoWeb");
                mwc.include(request, response);

                if(session.getAttribute("Mensaje") != null){
                    out.println(session.getAttribute("Mensaje"));
                }else{
                    Usuarios user = (Usuarios)session.getAttribute("User");
                    out.println("<div class='contenedorCarrito'>");
                    if(user.getTipoUsr() == 1){
                        out.println("<a href='ABMUsuarios' class='opcionMenu'>ABM de Usuario</a>");
                    }
                    out.println("<a href='ListaProductos' class='opcionMenu'>Listado de Productos</a>");
                    out.println("<a href='HistoriaCompra' class='opcionMenu'>Historial de Compras</a>");
                    
                    out.println("</div>");
                }

                RequestDispatcher mwp = request.getRequestDispatcher("MarcoWebPie");
                mwp.include(request, response);
            }else{
                session.setAttribute("Mensaje", "Usted no esta logeado.");
                response.sendRedirect("Index");
            }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
