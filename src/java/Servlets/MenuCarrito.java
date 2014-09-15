package Servlets;

import Modelo.LineaDeCompra;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "MenuCarrito", urlPatterns = {"/MenuCarrito"})
public class MenuCarrito extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Hashtable ListadoCompra = new Hashtable();
        HttpSession session = request.getSession(true);
        Cookie[] cookies = request.getCookies();
        int length = cookies.length;
        String EstadoMenu = "DivCarrito";
        try {

            for (int i = 0; i < length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals("EstadoMenu")) {
                    EstadoMenu = cookie.getValue();
                }
            }

            ListadoCompra = (Hashtable) session.getAttribute("Carrito");
            Enumeration e = ListadoCompra.elements();
            LineaDeCompra aux;
            float total = 0;

            while (e.hasMoreElements()) {
                aux = (LineaDeCompra) e.nextElement();
                total += aux.getCostoUnit() * aux.getCantidad();
            }

            out.println(
                    "<div id='MenuCarrito' class='" + EstadoMenu + "'>\n"
                    + "<span class='TxtTotal'>" + total + " $</span>\n"
                    + "<img class='ImgBotonCarrito' src='imagenes/carrito.png ' onclick='AbrirMenu(MenuCarrito)'>\n"
                    + "<div class='DivMenuCarrito'>"
                    + "<table class='TableMenu'>");

            e = ListadoCompra.elements();
            while (e.hasMoreElements()) {
                aux = (LineaDeCompra) e.nextElement();
                out.println(
                        "<tr>"
                        + "<td>"
                        + " X" + aux.getCantidad()
                        + "</td>"
                        + "<td style='text-center: right;'>"
                        + aux.getNombre()
                        + "</td>"
                        + "<td style='text-align: right;'>"
                        + aux.getCostoUnit() + " $"
                        + "</td>"
                        + "</tr>");
            }

            out.println(
                    "</table>"
                    + "<form action='ConfirmarCompra' method='post'>"       
                        + "<button class='btnCarrito'> COMPRAR </button>"
                    + "</form>"
                    + "</div>\n"
                    + "</div>\n");
                    
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
