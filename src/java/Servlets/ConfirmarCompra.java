/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Modelo.Compras;
import Modelo.LineaDeCompra;
import Modelo.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author matias
 */
@WebServlet(name = "ConfirmarCompra", urlPatterns = {"/ConfirmarCompra"})
public class ConfirmarCompra extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);

        try {
            RequestDispatcher mwc = request.getRequestDispatcher("MarcoWeb");
            mwc.include(request, response);

            if (session.getAttribute("Mensaje") != null) {
                out.println(
                        "<div class='TxtMsg'>"
                        + session.getAttribute("Mensaje")
                        + "</div>");
            } else {

                Usuarios User = (Usuarios) session.getAttribute("User");
                Hashtable ListadoCompra = (Hashtable) session.getAttribute("Carrito");

                Compras nuevaCompra = new Compras(User, ListadoCompra);

                Enumeration e = ListadoCompra.elements();
                e = ListadoCompra.elements();
                LineaDeCompra aux;

                out.println("Usuario: " + nuevaCompra.getUsr().getNombre() + ", " + nuevaCompra.getUsr().getApellido() + "\n"
                        + "<div>");

                while (e.hasMoreElements()) {
                    aux = (LineaDeCompra) e.nextElement();
                    out.println(
                            "<div>"
                            + "<tr>"
                            + "<td>"
                            + " X" + aux.getCantidad()
                            + "</td>"
                            + "<td style='text-center: right;'>"
                            + aux.getNombre()
                            + "</td>"
                            + "<td style='text-align: right;'>"
                            + aux.getCostoUnit() + " $"
                            + "</td>"
                            + "</tr>"
                            + "</div>");
                }
                out.println("</div>\n"
                        + "\nTotal: " + nuevaCompra.calcularTotal()
                        + "\n<button>Confirmar</button>");
            }

            RequestDispatcher mwp = request.getRequestDispatcher("MarcoWebPie");
            mwp.include(request, response);

        } finally {
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

        try {
            if (session.getAttribute("User") != null) {
                if (session.getAttribute("Carrito") != null) {
                    processRequest(request, response);
                } else {
                    session.setAttribute("Mensaje", "Carrito vacio");
                }
            } else {
                session.setAttribute("Mensaje", "Usted no esta logeado.");
                response.sendRedirect("Index");
            }
        } catch (Exception ex) {
            session.setAttribute("Mensaje", "Error in catch " + ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
