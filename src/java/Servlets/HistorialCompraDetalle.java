/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Datos.Dcompras;
import Modelo.LineaDeCompra;
import Modelo.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Edii
 */
@WebServlet(name = "HistorialCompraDetalle", urlPatterns = {"/HistorialCompraDetalle"})
public class HistorialCompraDetalle extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);

        try {
            RequestDispatcher mw = request.getRequestDispatcher("MarcoWeb");
            mw.include(request, response);
            if (session.getAttribute("Mensaje") != null) {
                out.println("<div class='TxtMsgError'>" + session.getAttribute("Mensaje") + "</div>");
                session.removeAttribute("Mensaje");
            }
            if (session.getAttribute("User") != null) {
                Hashtable ListaCompras;
                if (session.getAttribute("ListadoDetalleCompras") == null) {
                    Dcompras dcom = new Dcompras();
                    ListaCompras = dcom.TraerLineasComprasPorIdCabecera((Integer) session.getAttribute("idCabecera"));
                    session.setAttribute("ListadoDetalleCompras", ListaCompras);
                } else {
                    ListaCompras = (Hashtable) session.getAttribute("ListadoDetalleCompras");
                }

                Enumeration e = ListaCompras.elements();
                LineaDeCompra aux;

                out.println("<div class='tablaHistorial'>"
                        + "<div class='filaHistorialCabecera'>"
                        + "	<div class='celdaCabeceraHistorial'>Id</div>"
                        + "	<div class='celdaCabeceraHistorial'>Nombre</div>"
                        + "	<div class='celdaCabeceraHistorial'>Cantidad</div>"
                        + "	<div class='celdaCabeceraHistorial'>Costo Unitario</div>"
                        + "	<div class='celdaCabeceraHistorial'>Costo Total</div>"
                        + "</div>");

                while (e.hasMoreElements()) {
                    aux = (LineaDeCompra) e.nextElement();
                    out.println("<div class='filaHistorial'>"
                            + "    <input type='text' class='InputId' name='id' value='" + aux.getId() + "'>"
                            + "	<div class='celdaHistorial'>" + aux.getId() + "</div>"
                            + "	<div class='celdaHistorial'>" + aux.getNombre() + "</div>"
                            + "	<div class='celdaHistorial'>" + aux.getCantidad() + "</div>"
                            + "	<div class='celdaHistorial'>$ " + aux.getCostoUnit() + "</div>"
                            + "	<div class='celdaHistorial'>$ " + aux.getCostoUnit() * aux.getCantidad() + "</div>"
                            + "</div>");
                }
                        out.println("<form action='HistoriaCompra' method='post'>"
                                + "<button>Volver</button></form>");
            }

            RequestDispatcher mwc = request.getRequestDispatcher("MarcoWebPie");
            mwc.include(request, response);
        } catch (Exception ex) {
            out.println(ex.getMessage());
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(true);
            int id = Integer.parseInt(request.getParameter("id"));
            session.setAttribute("idCabecera", id);
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(HistorialCompraDetalle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
