package pkg_commands;


/**
 * Classe CommandWords - gère l'utilisation de commandes dans le jeu. 
 * @author Maxime LE BLAN
 * @version 14/01/2026
 */
public class CommandWords
{
    // a constant array that will hold all valid command words
    /**
     * Array de String contenant toutes les commandes valides du jeu.
     */
    private static final String[] sValidCommands = {
        "go", "quit", "help", "look", "eat", "back", "test", "take",
        "drop", "items", "use", "charge", "alea", "ask"
    };

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        
    } // CommandWords()

    /**
     * Vérifie si la chaine passée en paramètre est une commande. 
     * @param pString chaine de caractères contenant la commande à vérifier
     * @return true si la chaine passée est une commande,
     * false sinon.
     */
    public boolean isCommand( final String pString )
    {
        for ( int vI=0; vI< CommandWords.sValidCommands.length; vI++ ) {
            if ( CommandWords.sValidCommands[vI].equals( pString ) )
                return true;
        } // for
        // if we get here, the string was not found in the commands :
        return false;
    } // isCommand()
    
    /** 
     * Fonction qui renvoie une chaine de caractères contenant toutes
     * les commandes valides.
     * @return une chaine de caractères
     */    
    public String getCommandList()     
    {        
        StringBuilder commands = new StringBuilder();        
        for(int i = 0; i < CommandWords.sValidCommands.length; i++) 
        {            
            commands.append( CommandWords.sValidCommands[i] + "  " );        
        }        
        return commands.toString();    
    }
} // CommandWords
