

/**
 * Classe Game, crée un nouveau jeu à l'aide des classes GameEngine et
 * UserInterface.
 *
 * @author Maxime LE BLAN
 * @version 14/01/2026
 */
public class Game
{
    /**
     * Attribut contenant une instance de notre interface graphique.
     */
    private UserInterface aGui;
    /**
     * Attribut contenant une instance du moteur de notre jeu.
     */
    private GameEngine aEngine;

    /**
     * Create the game and initialise its internal map. Create the inerface and link to it.
     */
    public Game() 
    {
        this.aEngine = new GameEngine();
        this.aGui = new UserInterface( this.aEngine );
        this.aEngine.setGUI( this.aGui );
    }
    
} // Game
