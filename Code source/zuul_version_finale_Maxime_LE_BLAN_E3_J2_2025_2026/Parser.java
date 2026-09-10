 
import pkg_commands.CommandWords;
import pkg_commands.Command;
import java.util.StringTokenizer;

/**
 * Classe permettant de récupérer le texte saisie par le joueur lorsque
 * celui-ci tape une commande.
 * @author Maxime LE BLAN
 * @version 14/01/2026
 */
public class Parser 
{
    /**
     * Commande saisie par le joueur.
     */
    private CommandWords aCommandWords;  // (voir la classe CommandWords)
    
    /**
     * Constructeur par defaut qui crée les 2 objets prévus pour les attributs
     */
    public Parser() 
    {
        this.aCommandWords = new CommandWords();
    } // Parser()

    /** 
     * Convert an input given by the user into a Command object.
     * @param pInputLine the input from the user.
     * @return The next command from the user.
     */
    public Command getCommand(final String pInputLine) 
    {
        String vWord1 = null;
        String vWord2 = null;
        String vWord3 = null;

        StringTokenizer tokenizer = new StringTokenizer( pInputLine );

        if ( tokenizer.hasMoreTokens() )
            vWord1 = tokenizer.nextToken();      // get first word
        else
            vWord1 = null;

        if ( tokenizer.hasMoreTokens() )
            vWord2 = tokenizer.nextToken();      // get second word
        else
            vWord2 = null;
            
        if ( tokenizer.hasMoreTokens() )
            vWord3 = tokenizer.nextToken();      // get third word
        else
            vWord3 = null;

        // note: we just ignore the rest of the input line.

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).

        if ( this.aCommandWords.isCommand( vWord1 ) )
            return new Command( vWord1, vWord2, vWord3 );
        else
            return new Command( null, vWord2, vWord3 );
    } // getCommand()
    
    /**
     * Returns a String with valid command words.
     * @return a String with valid command words.
     */
    public String getCommandString() // was showCommands()
    {
        return this.aCommandWords.getCommandList();
    } // getCommandString()
} // Parser
