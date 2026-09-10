import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Classe qui gère la lecture d'un fichier contenant des commandes de test
 * pour le jeu.
 * @author Maxime LE BLAN
 * @version 14/01/2026
 */
public class LectureFichierSimple
{
    /**
     * Constructeur par défaut de la classe
     */
    public LectureFichierSimple()
    {
        
    }
    
    /**
     * Fonction permettant de lire un fichier contenant des commandes
     * du jeu afin de les tester une par une
     * @param pNomFichier nom du fichier texte contenant la liste des commandes
     * @param pGameEngine instance de la classe GameEngine permettant d'exécute les commandes
     */
    public static void lecture( final String pNomFichier, final GameEngine pGameEngine )
    {
        Scanner vSc;
        try { // pour "essayer" les instructions suivantes :
            vSc = new Scanner( new File( pNomFichier + ".txt" ) ); // ouverture du fichier s'il existe
            while ( vSc.hasNextLine() ) { // tant qu'il y a encore une ligne à lire dans le fichier
                String vLigne = vSc.nextLine(); // lecture de la ligne dans le fichier
                pGameEngine.interpretCommand(vLigne);
            } // while
        } // try
        catch ( final FileNotFoundException pFNFE ) { // si le fichier n'existe pas
            System.out.println("Le fichier à lire n'a pas été trouvé.");
        } // catch
    } // lecture
} // LectureFichierSimple