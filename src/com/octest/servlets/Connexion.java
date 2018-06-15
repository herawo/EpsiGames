package com.octest.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.epsigames.beans.Utilisateur;
import com.epsigames.forms.ConnexionForm;

/**
 * Servlet implementation class Connexion
 */
@WebServlet( "/Connexion" )
public class Connexion extends HttpServlet {
    private static final long  serialVersionUID          = 1L;
    public static final String ATT_USER                  = "utilisateur";
    public static final String ATT_FORM                  = "form";
    public static final String ATT_INTERVALLE_CONNEXIONS = "intervalleConnexions";
    public static final String ATT_SESSION_USER          = "sessionUtilisateur";
    public static final String COOKIE_DERNIERE_CONNEXION = "derniereConnexion";
    public static final String FORMAT_DATE               = "dd/MM/yyyy HH:mm:ss";
    public static final String VUE                       = "/WEB-INF/connexion.jsp";
    public static final String CHAMP_MEMOIRE             = "memoire";
    public static final int    COOKIE_MAX_AGE            = 60 * 60 * 24 * 365;      // 1an

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        // Tentative de r�cup�ration du cookie depuis la requ�te
        String derniereConnexion = getCookieValue( request, COOKIE_DERNIERE_CONNEXION );
        // Si le cookie existe , alors calcul de la dur�e
        if ( derniereConnexion != null ) {
            // R�cup�ration de la date courante
            DateTime dtCourante = new DateTime();
            // R�cup�ration de la date pr�sente dans le cookie
            DateTimeFormatter formatter = DateTimeFormat.forPattern( FORMAT_DATE );
            DateTime dtDerniereConnexion = formatter.parseDateTime( derniereConnexion );
            // Calcul de la dur�e de l'intervalle
            Period periode = new Period( dtDerniereConnexion, dtCourante );
            // Formatage de la dur�e de l'intervalle
            PeriodFormatter periodFormatter = new PeriodFormatterBuilder()
                    .appendYears().appendSuffix( "an", "ans" )
                    .appendMonths().appendSuffix( "mois" )
                    .appendDays().appendSuffix( "jour", "jours" )
                    .appendHours().appendSuffix( "minute", "minutes" )
                    .appendSeparator( "et" )
                    .appendSeconds().appendSuffix( "seconde", "secondes" )
                    .toFormatter();
            String intervalleConnexions = periodFormatter.print( periode );
            // ajout de l'intervalle en tant qu'atribut de la requ�te
            request.setAttribute( ATT_INTERVALLE_CONNEXIONS, intervalleConnexions );

        }
        // Affichage de la page de connexion
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        ConnexionForm form = new ConnexionForm();
        Utilisateur utilisateur = form.connecterUtilisateur( request );
        HttpSession session = request.getSession();
        if ( form.getErreurs().isEmpty() ) {
            session.setAttribute( ATT_SESSION_USER, utilisateur );
        } else {
            session.setAttribute( ATT_SESSION_USER, null );
        }
        if ( request.getParameter( CHAMP_MEMOIRE ) != null ) {
            DateTime dt = new DateTime();
            DateTimeFormatter formatter = DateTimeFormat.forPattern( FORMAT_DATE );
            String dateDerniereConnexion = dt.toString( formatter );
            setCookie( response, COOKIE_DERNIERE_CONNEXION, dateDerniereConnexion, COOKIE_MAX_AGE );
        } else {
            setCookie( response, COOKIE_DERNIERE_CONNEXION, "", 0 );
        }
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_USER, utilisateur );
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    private static String getCookieValue( HttpServletRequest request, String nom ) {
        Cookie[] cookies = request.getCookies();
        if ( cookies != null ) {
            for ( Cookie cookie : cookies ) {
                if ( cookie != null && nom.equals( cookie.getName() ) ) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private static void setCookie( HttpServletResponse response, String nom, String valeur, int maxAge ) {
        Cookie cookie = new Cookie( nom, valeur );
        cookie.setMaxAge( maxAge );
        response.addCookie( cookie );
    }
}
