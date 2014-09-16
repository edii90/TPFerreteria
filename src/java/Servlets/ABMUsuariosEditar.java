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
 * @author Edii
 */
@WebServlet(name = "ABMUsuariosEditar", urlPatterns = {"/ABMUsuariosEditar"})
public class ABMUsuariosEditar extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);

        try {
            RequestDispatcher mw = request.getRequestDispatcher("MarcoWeb");
            mw.include(request, response);

            if (session.getAttribute("User") != null) {
                if (((Usuarios) session.getAttribute("User")).getTipoUsr() == 1) {
                    if (session.getAttribute("Mensaje") != null) {
                        out.println("<div class='TxtMsgError'>" + session.getAttribute("Mensaje") + "</div>");
                        session.removeAttribute("Mensaje");
                    }
                    Usuarios user = (Usuarios) session.getAttribute("UsuarioModificar");
                    out.println("<form class='formABM' name='DatosUsuario' action='ABMUsuariosEditar' method='post'>"
                            + "<div class='contenedor'>"
                            + "	<div class='etiqueta'><label name='id'>Id: </label></div>"
                            + "	<div class='campo'><label name='textoId'>" + user.getId() + "</label></div>"
                            + "</div>"
                            + "<div class='contenedor'>"
                            + "	<div class='etiqueta'><label name='usuario'>Nombre de Usuario: </label></div>"
                            + "	<div class='campo'><input class='InputABM' type='text' name ='Usuario' value='" + user.getUsuario() + "' /></div>"
                            + "</div>"
                            + "<div class='contenedor'>"
                            + "	<div class='etiqueta'<label name='pass'>Contraseña: </label></div>"
                            + "	<div class='campo'><input class='InputABM' type='password' name ='Pass' value='" + user.getContrasenia() + "' /></div>"
                            + "</div>"
                            + "<div class='contenedor'>"
                            + "	<div class='etiqueta'><label name='nombre'>Nombre: </label></div>"
                            + "	<div class='campo'><input class='InputABM' type='text' name ='Nombre' value='" + user.getNombre() + "' /></div>"
                            + "</div>"
                            + "<div class='contenedor'>\n"
                            + "	<div class='etiqueta'><label name='apellido'>Apellido: </label></div>"
                            + "	<div class='campo'><input class='InputABM' type='text' name ='Apellido' value='" + user.getApellido() + "' /></div>"
                            + "</div>"
                            + "<div class='contenedor'>"
                            + "	<div class='etiqueta'><label name='dni'>DNI: </label></div>"
                            + "	<div class='campo'><input class='InputABM' type='text' name ='DNI' value='" + user.getDNI() + "' /></div>"
                            + "</div>"
                            + "<div class='contenedor'>"
                            + "	<div class='etiqueta'><label name='tipo'>Tipo de Usuario: </label></div>"
                            + "	<div class='campo'><input class='InputABM' type='text' name ='TipoUsr' value='" + user.getTipoUsr() + "' /></div>"
                            + "</div>"
                            + "	<div class='DivBoton'><button name='Guardar' class='botonABM'>Guardar</button></div>");
                    if (user.getId() != 0) {
                        out.println("	<div class='DivBoton'><button name='Eliminar' class='botonABM'>Eliminar</button></div>");
                    }
                    out.println("	<div class='DivBoton'><button name='Cancelar' class='botonABM'>Cancelar</button></div>"
                            + "</form>");
                    RequestDispatcher mwc = request.getRequestDispatcher("MarcoWebPie");
                    mwc.include(request, response);

                } else {
                    out.println("<div class='DivCentrado'>Usted no tiene permiso de estar aca</div>");
                }

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
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(true);

            int id;

            if (session.getAttribute("idUsuarioEditar") == null) {
                if (request.getParameter("id") != null) {
                    id = Integer.parseInt(request.getParameter("id"));
                    session.setAttribute("idUsuarioEditar", id);
                } else {
                    id = 0;
                }
            } else {
                id = (Integer) session.getAttribute("idUsuarioEditar");

            }
            if (id != 0) {
                Hashtable ListadoUsuarios = (Hashtable) session.getAttribute("ListadoUsuarios");
                Usuarios user = (Usuarios) ListadoUsuarios.get(id);
                session.setAttribute("UsuarioModificar", user);
            } else {
                Usuarios user = new Usuarios("", "", 0, "", "", 2);
                session.setAttribute("UsuarioModificar", user);
            }

            if (request.getParameter("Eliminar") != null) {
                Dusuarios duser = new Dusuarios();
                duser.EliminarUsr(id);

                session.removeAttribute("idUsuarioEditar");
                session.removeAttribute("UsuarioModificar");
                session.removeAttribute("ListadoUsuarios");

                response.sendRedirect("ABMUsuarios");
            }

            if (request.getParameter("Guardar") != null) {
                Dusuarios duser = new Dusuarios();

                String nombre = (request.getParameter("Nombre"));
                String apellido = (request.getParameter("Apellido"));
                String pass = (request.getParameter("Pass"));
                String usr = (request.getParameter("Usuario"));
                int tipo = Integer.parseInt(request.getParameter("TipoUsr"));
                int doc = Integer.parseInt(request.getParameter("DNI"));

                if (id != 0) {
                    Usuarios aux = new Usuarios(id, usr, pass, doc, nombre, apellido, tipo);
                    duser.ModificarUsr(aux);
                } else {
                    duser.CrearUsr(usr, pass, doc, nombre, apellido);
                }

                session.removeAttribute("idUsuarioEditar");
                session.removeAttribute("UsuarioModificar");
                session.removeAttribute("ListadoUsuarios");

                response.sendRedirect("ABMUsuarios");
            }

            if (request.getParameter("Cancelar") != null) {
                session.removeAttribute("idUsuarioEditar");
                session.removeAttribute("UsuarioModificar");
                response.sendRedirect("ABMUsuarios");
            }

            processRequest(request, response);
        } catch (Exception ex) {

            HttpSession session = request.getSession(true);
            session.setAttribute("Mensaje", "Error: " + ex.getMessage());
            processRequest(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
