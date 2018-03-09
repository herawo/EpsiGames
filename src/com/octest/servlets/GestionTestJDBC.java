package com.octest.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GestionTestJDBC
 */
@WebServlet( "/GestionTestJDBC" )
public class GestionTestJDBC extends HttpServlet {
    private static final long  serialVersionUID = 1L;
    public static final String ATT_MESSAGES     = "messages";
    public static final String VUE              = "/WEB-INF/test_jdbc.jsp";

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        // Transmission vers la page en charge de l'affichage des r�sultats
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet( request, response );
    }

}
