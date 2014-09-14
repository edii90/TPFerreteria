package Servlets;

import Modelo.Usuarios;
import com.sun.webkit.ThemeClient;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/MarcoWeb"})
public class MarcoWeb extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<script type='text/javascript'>\n"
                + "function AbrirMenu(id) {\n"
                + "    var elemento = document.getElementById('MenuCarrito');\n"
                + "    if(elemento.className == 'DivCarritoAbierto'){\n"
                + "      elemento.className = 'DivCarrito';\n"
                + "         document.cookie=\"EstadoMenu=DivCarrito\";"
                + "    }\n"
                + "    else{\n"
                + "      elemento.className = 'DivCarritoAbierto';\n"
                + "         document.cookie=\"EstadoMenu=DivCarritoAbierto\";"
                + "    }\n"
                + "}\n"
                + "</script>\n"
                + "<script type=\"text/javascript\">\n"
                + "function Validar() \n"
                + "{\n"
                + "   if(document.FormularioUsuario.usuario.value == '')\n"
                + "   {\n"
                + "     alert('Ingrese el Usuario');\n"
                + "   }\n"
                + "   else\n"
                + "   {\n"
                + "     if(document.FormularioUsuario.password.value == '') \n"
                + "     {\n"
                + "       alert('Ingrese la contraseña');\n"
                + "     }\n"
                + "   }\n"
                + "}\n"
                + "</script>\n"
                + "<html>\n"
                + "<head>\n"
                + "<link rel='stylesheet' type='text/css' href='css/theme.css'>\n"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
                + "<title></title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<div class='DivMain'>\n"
                + "<div class='DivCabecera'>\n"
                + "<div class='DivLogo'><img class='ImgLogo'src='imagenes/apu.png'></div>\n"
                + "<div class='DivBuscador'>\n"
                + "<input class='Buscador' type='text' name='buscar' autocomplete='off' autofocus=''>\n"
                + "<button class='BotonBuscador'><img class='ImgButtonBuscador' src='imagenes/lupa.png'></button>\n"
                + "</div>\n"
                + "<div class='DivUsuario'>\n"
                + "<form name='FormularioUsuario' action='Index' method='post'>\n");
        Usuarios usr = (Usuarios) session.getAttribute("User");
        if (usr == null) {
            out.println(
                    "<span Class='TxtError'> </span>\n"
                    + "<div class='InLog'>\n"
                    + "<input type='text' class='InUser' name='usuario' placeholder='Usuario' requiered>\n"
                    + "<input type='password' class='InPass' name='password' placeholder='Contraseña' requiered>\n"
                    + "</div>"
                    + "<button class='ButtonLog' onClick='javascript:Validar();'>LOGIN</button>\n"
                    + "</form>\n"
                    + "</div>\n"
                    + "</div>\n"
                    + "<div class='ContenidoMain'>");
        } else {
            out.println(
                    "<div class='InLog'>\n"
                    + "<div class='DivTextLog'>\n"
                    + "<label class='TextLog'>Hi!</label>   \n"
                    + "</div>					\n"
                    + "<div class='DivTextLog'>\n"
                    + "<a href='Menu' class='TextLog'>" + usr.getNombre() + "</a>\n"
                    + "</div>					\n"
                    + "</div>					\n"
                    + "<button class='ButtonLog'>Logout</button>\n"
                    + "</form>"
                    + "</div>\n"
                    + "</div>\n"
                    + "<div class='ContenidoMain'>");
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
    }// </editor-fold>

}
