package com.epsigames.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epsigames.beans.Utilisateur;

public class UtilisateurBDD {

    private Connection connexion;

    public List<Utilisateur> recupererUtilisateurs() {
        
        
        List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
        Statement statement = null;
        ResultSet resultat = null;

        loadDatabase();
        try {

            statement = connexion.createStatement();
            resultat = statement.executeQuery( "Select nom, prenom FROM utilisateur;" );
            while ( resultat.next() ) {

                String nom = resultat.getString( "nom" );

                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setNom( nom );

                utilisateurs.add( utilisateur );
            }
        } catch ( SQLException e ) {

        } finally {
            // Fermeture de la connexion
            try {
                if ( resultat != null )
                    resultat.close();
                if ( statement != null )
                    statement.close();
                if ( connexion != null )
                    connexion.close();
            } catch ( SQLException ignore ) {
                System.out.println("Error");
            }
        }
        return utilisateurs;

    }

    private void loadDatabase() {
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch ( ClassNotFoundException e ) {

        }

        try {
            connexion = DriverManager.getConnection( "jdbc:mysql://localhost:3306/orm_epsigames", "root", "" );
        } catch ( SQLException e ) {
            e.printStackTrace();

        }

    }

    public void ajouterUtilisateur( Utilisateur utilisateur ) {
        loadDatabase();

        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "Insert INTO utilisateur (nom, login, mdp) VALUES (?, ?, ?);" );
            preparedStatement.setString( 1, utilisateur.getNom() );
            preparedStatement.setString( 3, utilisateur.getEmail() );
            preparedStatement.setString( 2, utilisateur.getMotDePasse() );

            preparedStatement.executeUpdate();
        } catch ( SQLException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
