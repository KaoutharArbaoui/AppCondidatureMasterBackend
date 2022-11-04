package master.appcondidature.security;

import javax.servlet.http.HttpServletRequest;

//utiliser dans la verification d'email, returnavec le lien hypertexte de vérification incluant
// le code de vérification. La valeur de siteURL est envoyée par le contrôleur.
public class Untity {
    public static String getSiteURL(HttpServletRequest request)
    {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(),"");
    }
}
