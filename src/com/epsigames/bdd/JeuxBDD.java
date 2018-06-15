package com.epsigames.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epsigames.beans.Jeux;

public class JeuxBDD {
    private Connection connexion;

    public List<Jeux> recupererJeux() {
        List<Jeux> jeux = new ArrayList<Jeux>();

        // Represente la requete Sql
        Statement statement = null;
        // Repr�sente les r�sultats
        ResultSet resultat = null;

        loadDatabase();
        try {

            statement = connexion.createStatement();
            resultat = statement.executeQuery( "Select titre, Producteur FROM jeux;" );
            while ( resultat.next() ) {             

                String titre = resultat.getString( "titre" );

                Jeux jeux1 = new Jeux();
                jeux1.setTitre( titre );
                jeux.add( jeux1 );
            }
        } catch ( SQLException e ) {

        } finally {
            try {
                if ( resultat != null )
                    resultat.close();
                if ( statement != null )
                    statement.close();
                if ( connexion != null )
                    connexion.close();
            } catch ( SQLException ignore ) {

            }
        }
        return jeux;

    }

    private void loadDatabase() {

        try {
            connexion = DriverManager.getConnection( "jdbc:mysql://localhost:3306/epsigames", "root", "" );
        } catch ( SQLException e ) {
            e.printStackTrace();

        }

    }

    public void ajouterJeux( Jeux jeux ) {
        loadDatabase();
        
        
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch ( ClassNotFoundException e ) {

        }
        
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "Insert INTO jeux (titre, sousTitre, societeDeProduction, description, genre, paysDeProduction, anneeDeProduction) VALUES (?, ?, ?, ?, ?, ?,?);" );
            preparedStatement.setString( 1, jeux.getTitre() );
            preparedStatement.setString( 2, jeux.getSousTitre() );
            preparedStatement.setString( 6, jeux.getGenre() );

            preparedStatement.executeUpdate();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }

    }

}
