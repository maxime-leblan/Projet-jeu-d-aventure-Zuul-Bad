
import pkg_items.Item;

/**
 * Classe permettant de créer un Beamer, qui est une sorte d'item permettant
 * au joueur de se téléporter dans une pièce qu'il aura préalablement chargé
 * dans le Beamer.
 *
 * @author Maxime LE BLAN
 * @version 14/01/2026
 */
public class Beamer extends Item
{
    /**
     * Contient la dernière pièce précédemment chargée avec le Beamer.
     */
    private Room aLoadedRoom;
    
    /**
     * Constructeur de la classe Beamer.
     * @param pItemDescription Contient la description de l'item
     * @param pItemWeight Contient le poids de l'item
     * @param pName Contient le nom de l'item
     */
    public Beamer(final String pItemDescription, final int pItemWeight, final String pName)
    {
        super(pItemDescription, pItemWeight, pName);
        this.aLoadedRoom = null;
    }
    
    /**
     * Indique si le Beamer est chargé ou non.
     * @return true si le Beamer contient une pièce chargée,
     * false sinon (dans ce cas il contient la pièce null)
     */
    public boolean isLoaded()
    {
        return this.aLoadedRoom != null;
    }
    
    /**
     * Permet de charger la pièce passé en paramètre dans le Beamer.
     * @param pRoom pièce à charger dans le Beamer
     */
    public void charge(final Room pRoom)
    {
        this.aLoadedRoom = pRoom;
    }
    
    /**
     * Permet d'utiliser le Beamer en renvoyant la pièce précédemment chargée
     * et en réinitialisant le chargeur à null.
     * @return la pièce précédemment chargée s'il y en avait une,
     * null sinon
     */
    public Room fire()
    {
        Room vFiredRoom = this.aLoadedRoom;
        this.aLoadedRoom = null;
        return vFiredRoom;
    }
}