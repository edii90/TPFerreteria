package Servlets;

import Datos.Dproductos;
import Modelo.LineaDeCompra;
import Modelo.Productos;
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

@WebServlet(name = "ListaProductos", urlPatterns = {"/ListaProductos"})
public class ListaProductos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        Hashtable ListadoMuestreo = new Hashtable();

        try {
            if (session.getAttribute("User") != null) {
                RequestDispatcher mw = request.getRequestDispatcher("MarcoWeb");
                mw.include(request, response);
                if (session.getAttribute("Mensaje") != null) {
                    out.println(session.getAttribute("Mensaje"));
                    session.removeAttribute("Mensaje");
                }
                Dproductos Dpro = new Dproductos();

                out.println("<div class='DivListProduct'>");
                if (session.getAttribute("Carrito") == null) {
                    ListadoMuestreo = Dpro.TraerProductos();
                    session.setAttribute("ListaProductos", ListadoMuestreo);
                } else {
                    ListadoMuestreo = (Hashtable) session.getAttribute("ListaProductos");
                }
                Enumeration e = ListadoMuestreo.elements();
                Productos aux;
                int formid = 1;
                while (e.hasMoreElements()) {
                    aux = (Productos) e.nextElement();
                    String FormName = "Producto" + formid;

                    out.println(
                            "<form name='" + FormName + "' action='ListaProductos' method='post'>"
                            + "<div class='DivProductos'>"
                            + "<br><input type='text' name='id' class='InputId' value='" + aux.getId() + "'>"
                            + "<br><label name='nombre'>" + aux.getNombre() + "</label>"
                            + "<br><label name='precio'>" + aux.getPrecio() + "</label>"
                            + "<br><label name='Stock'>" + aux.getStock() + "</label>"
                            + "<input name='cantidad' type='number' value='0' min='0' max='" + aux.getStock() + "'>"
                            + "<br><button>Agregar</button>"
                            + "</div>"
                            + "</form>");
                    formid++;
                }
                out.println("</div>");
                RequestDispatcher mwc = request.getRequestDispatcher("MarcoWebPie");
                mwc.include(request, response);
            } else {
                response.sendRedirect("Index");
            }
        } catch (Exception ex) {
            out.println("Error: " + ex.getMessage());
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
            Logger.getLogger(ListaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        try {

            Hashtable ListaProductos = new Hashtable();
            Hashtable ListaCarrito = new Hashtable();

            if (session.getAttribute("ListaProductos") != null) {
                ListaProductos = (Hashtable) session.getAttribute("ListaProductos");

                if (session.getAttribute("Carrito") != null) {
                    ListaCarrito = (Hashtable) session.getAttribute("Carrito");
                }

                int idProd = Integer.parseInt(request.getParameter("id"));
                int cantProd = Integer.parseInt(request.getParameter("cantidad"));
                Productos Prod = new Productos();
                Prod = (Productos) ListaProductos.get(idProd);
                LineaDeCompra LineaCompra = new LineaDeCompra(Prod.getId(),Prod.getNombre(),Prod.getPrecio(),cantProd);
                if (ListaCarrito.get(Prod.getId()) != null) {
                    LineaDeCompra aux = new LineaDeCompra();
                    aux = (LineaDeCompra) ListaCarrito.get(Prod.getId());
                    LineaCompra.setCantidad(aux.getCantidad() + LineaCompra.getCantidad());
                }

                ListaCarrito.put(LineaCompra.getId(), LineaCompra);

                Prod.setStock(Prod.getStock() - cantProd);
                ListaProductos.put(Prod.getId(), Prod);

                session.setAttribute("Carrito", ListaCarrito);
            }

            processRequest(request, response);

        } catch (Exception ex) {
            session.setAttribute("Mensaje", "Error in catch " + ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
