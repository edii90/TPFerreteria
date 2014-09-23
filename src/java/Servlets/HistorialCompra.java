/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Datos.Dcompras;
import Modelo.Compras;
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
@WebServlet(name = "HistoriaCompra", urlPatterns = {"/HistoriaCompra"})
public class HistorialCompra extends HttpServlet {

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

                if (session.getAttribute("ListadoCompras") == null) {

                    if (((Usuarios) session.getAttribute("User")).getTipoUsr() == 1) {
                        Dcompras dcom = new Dcompras();
                        ListaCompras = dcom.TraerTodasCabacerasCompras();
                        session.setAttribute("ListadoCompras", ListaCompras);
                    } else {

                        Dcompras dcom = new Dcompras();
                        ListaCompras = dcom.TraerComprasXusr((Usuarios) session.getAttribute("User"));
                        session.setAttribute("ListadoCompras", ListaCompras);
                    }
                } else {
                    ListaCompras = (Hashtable) session.getAttribute("ListadoCompras");
                }

                Enumeration e = ListaCompras.elements();
                Compras aux;

                out.println("<div class='tablaHistorial'>\n"
                        + "<div class='filaHistorialCabecera'>\n"
                        + "	<div class='celdaCabeceraHistorial'>Usuario</div>\n"
                        + "	<div class='celdaCabeceraHistorial'>N° Pedido</div>\n"
                        + "	<div class='celdaCabeceraHistorial'>Fecha</div>\n"
                        + "	<div class='celdaCabeceraHistorial'>Total</div>\n"
                        + "	<div class='celdaCabeceraHistorial'></div>\n"
                        + "</div>\n");

                int formid = 1;
                while (e.hasMoreElements()) {
                    String FormName = "Compra" + formid;
                    aux = (Compras) e.nextElement();
                    out.println("<form name='" + FormName + "' action='HistorialCompraDetalle' method='post'>"
                            + "<div class='filaHistorial'>\n"
                            + "    <input type='text' class='InputId' name='id' value='" + aux.getId() + "'>"
                            + "	<div class='celdaHistorial'>" + aux.getUsr().getNombre() + "</div>"
                            + "	<div class='celdaHistorial'>" + aux.getId() + "</div>"
                            + "	<div class='celdaHistorial'>" + aux.getFecha() + "</div>"
                            + "	<div class='celdaHistorial'>$ " + aux.getTotal() + "</div>"
                            + "	<div class='celdaHistorial'><button class='botonVerHistorial'>ver</button></div>"
                            + "</div></form>");
                    formid++;
                }
            }
            RequestDispatcher mwc = request.getRequestDispatcher("MarcoWebPie");
            mwc.include(request, response);

        } catch (Exception ex) {
            session.setAttribute("Mensaje", "Error in catch " + ex.getMessage());
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(HistorialCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(HistorialCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
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
