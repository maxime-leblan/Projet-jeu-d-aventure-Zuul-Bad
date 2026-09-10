import java.util.ArrayList;


/**
 * Classe chargée d'implémenter des PNJ pouvant se déplacer, elle
 * hérite de la classe Character.
 * 
 * 
 * @author Maxime LE BLAN
 * @version 14/01/2026
 */
public class MovingCharacter extends Character
{
    /**
     * Pièce dans laquelle se trouve le PNJ.
     */
    private Room aCurrentRoom;
    
    /**
     * Liste des pièces que doit traverser le PNJ
     * lorsqu'il se déplace.
     */
    private ArrayList<Room> aPath;
    
    /**
     * Indice indiquant la prochaine pièce à choisir
     * dans aPath lorsque le PNJ veut se déplacer.
     */
    private int aINextRoom;
    
    /**
     * Constructeur de la classe MovingCharacter.
     * @param pName nom du PNJ
     * @param pDefaultText texte de présentation par défaut du PNJ
     * @param pStarterRoom pièce où se trouve le PNJ au lancement du jeu
     */
    public MovingCharacter(final String pName, final String pDefaultText,
                        final Room pStarterRoom)
    {
        super(pName, pDefaultText);
        this.aCurrentRoom = pStarterRoom;
        this.aCurrentRoom.addCharacter(this);
        this.aPath = new ArrayList<Room>();
        this.aPath.add(pStarterRoom);
        this.aINextRoom = 1;
    }
    
    /**
     * Permet de déplacer le PNJ dans la pièce suivante du parcours
     * qu'il doit effectuer.
     */
    public void move()
    {
        this.aCurrentRoom.removeCharacter(this.getName());
        this.aCurrentRoom = this.aPath.get(this.aINextRoom);
        this.aCurrentRoom.addCharacter(this);
        this.aINextRoom = (this.aINextRoom + 1) % this.aPath.size();
    }
    
    /**
     * Rajoute une pièce en plus au chemin que doit effectuer le PNJ.
     * @param pRoom pièce à ajouter
     */
    public void addToPath(final Room pRoom)
    {
        this.aPath.add(pRoom);
    }
    
    /**
     * Renvoie la pièce dans laquelle se trouve le PNJ.
     * @return une pièce du jeu
     */
    public Room getCurrentRoom()
    {
        return this.aCurrentRoom;
    }
}