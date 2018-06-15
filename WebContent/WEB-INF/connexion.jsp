<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Connexion</title>
        <link type="text/css" rel="stylesheet" media="screen" href="<c:url value="/inc/form.css"/>" />
    </head>
    <body>
        <form method="post" action="connexion">
            <fieldset>
                <legend>Connexion</legend>
                <p> Vous pouvez vous connecter via ce formulaire </p>

                <label for="nom">Adresse email</label>
                <input type="text" id="email" name="email"/>
                <span class="erreur">${form.erreurs['email']}</span>
                <br/>

                <label for="motDePasse">Mot de passe</label>
                <input type="password" id="motDePasse" name="motDePasse"  />
                <span class="erreur">${form.erreurs['motDePasse']}</span>
                <br/>
                <input type="submit" value="Connexion" class="sansLabel"/>
                <br/>
            </fieldset>
        </form>


    </body>
</html>