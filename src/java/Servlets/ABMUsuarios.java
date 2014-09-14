/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Datos.Dusuarios;
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
@WebServlet(name = "ABMUsuarios", urlPatterns = {"/ABMUsuarios"})
public class ABMUsuarios extends HttpServlet {

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

            if (session.getAttribute("User") != null) {
                if (((Usuarios) session.getAttribute("User")).getTipoUsr() == 1) {
                    Hashtable listaUsers;
                    if (session.getAttribute("ListadoUsuarios") == null) {
                        Dusuarios Duser = new Dusuarios();
                        listaUsers = Duser.TraerTodosLosUsuarios();
                        session.setAttribute("ListadoUsuarios", listaUsers);
                    } else {
                        listaUsers = (Hashtable) session.getAttribute("ListadoUsuarios");
                    }

                    Enumeration e = listaUsers.elements();
                    Usuarios aux;

                    out.println("<div class=\"filaAbm\">\n"
                            + "    <div class=\"celdaCabecera\">Nombre de Usuario</div>\n"
                            + "    <div class=\"celdaCabecera\">Contrase&ntilde;a</div>\n"
                            + "    <div class=\"celdaCabecera\">Nombre</div>\n"
                            + "    <div class=\"celdaCabecera\">Apellido</div>\n"
                            + "    <div class=\"celdaCabecera\">DNI</div>\n"
                            + "    <div class=\"celdaCabecera\">Tipo</div>\n"
                            + "    <div class=\"celdaCabecera\"></div>\n"
                            + "  </div>\n");

                    int formid = 1;
                    while (e.hasMoreElements()) {
                        String FormName = "Usuario" + formid;
                        aux = (Usuarios) e.nextElement();
                        out.println("<form name='" + FormName + "' action='ABMUsuariosEditar' method='post'>"
                                + "<div class=\"filaAbm\">"
                                + "    <input type=\"text\" class=\"InputId\" name=\"id\" value=\"" + aux.getId() + "\">"
                                + "    <div class=\"celdaUsuario\"><label name='usuario'>" + aux.getUsuario() + "</label></div>"
                                + "    <div class=\"celdaUsuario\"><label type='password' name='contrasenia'>" + aux.getContrasenia() + "</label></div>"
                                + "    <div class=\"celdaUsuario\"><label name='nombre'>" + aux.getNombre() + "</label></div>"
                                + "    <div class=\"celdaUsuario\"><label name='apellido'>" + aux.getApellido() + "</label></div>"
                                + "    <div class=\"celdaUsuario\"><label name='dni'>" + aux.getDNI() + "</label></div>"
                                + "    <div class=\"celdaUsuario\"><label name='tipo'>" + aux.getTipoUsr() + "</label></div>"
                                + "    <div class=\"celdaUsuario\"><button>Editar</button>"
                                + "</div></form>");
                    }
                    out.println("  </div><form action='ABMUsuarios' method='post'><button class ='botonNuevo'>Crear Nuevo</button></form>");
                    
                    //Hacer lo que el admin y solo el admin pueda hacer
                } else {
                    response.sendRedirect("Index");
                }
                RequestDispatcher mwc = request.getRequestDispatcher("MarcoWebPie");
                mwc.include(request, response);

            } else {
                if (session.getAttribute("Mensaje") != null) {
                    out.println(session.getAttribute("Mensaje"));
                } else {
                    out.println("Logeate Daleee!");
                }

            }

            RequestDispatcher mwc = request.getRequestDispatcher("MarcoWebPie");
            mwc.include(request, response);
        }
    catch (Exception ex) {
            session.setAttribute("Mensaje", "Error in catch" + ex);
    }

    
        finally {
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
        try {
            processRequest(request, response);
        



} catch (Exception ex) {
            Logger.getLogger(ABMUsuarios.class  

.getName()).log(Level.SEVERE, null, ex);
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
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(true);
            if (request.getParameter("Crear Nuevo") != null) {
                
                session.setAttribute("idUsuarioEditar", 0);
                response.sendRedirect("ABMUsuarios");
            }
            processRequest(request, response);
        } catch (Exception ex) {

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
