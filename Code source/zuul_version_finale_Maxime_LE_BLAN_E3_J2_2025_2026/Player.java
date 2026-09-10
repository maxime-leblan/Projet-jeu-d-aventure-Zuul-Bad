import java.util.Stack;
import java.util.HashMap;

import pkg_items.ItemList;
import pkg_items.Item;

/**
 * Classe permettant de gérer notre joueur.
 *
 * @author Maxime LE BLAN
 * @version 14/01/2026
 */
public class Player
{
    /**
     * Pièce où se trouve actuellement le joueur.
     */
    private Room aCurrentRoom;
    /**
     * Nom du joueur.
     */
    private String aName;
    /**
     * Inventaire du joueur de type ItemList.
     */
    private ItemList aInventaire;
    /**
     * Poids maximal d'items que peut porter le joueur.
     */
    private int aMaxWeight;
    /**
     * Pile qui enregistre les précédentes pièces où se trouvait le joueur.
     */
    private Stack<Room>   aPreviousRooms;
    /**
     * Nombre de déplacements effectués par le joueur depuis le début de la partie.
     */
    private int aMovingCounter;
    
    /**
     * Constructeur de la classe Player
     * @param pName nom du joueur
     * @param pStarterRoom pièce de départ du joueur
     */
    public Player(final String pName, final Room pStarterRoom)
    {
        this.aName = pName;
        this.aPreviousRooms = new Stack<Room>();
        this.aInventaire = new ItemList();
        this.aMaxWeight = 8;
        this.aMovingCounter = 0;
        
        // initialisation de la position de départ du joueur
        this.aCurrentRoom = pStarterRoom;
        //this.aPreviousRooms.push(this.aCurrentRoom);
    }
    
    /**
     * Renvoie le nom du joueur.
     * @return une chaîne de caractères
     */
    public String getName()
    {
        return this.aName;
    }
    
    /**
     * Renvoie le nombre de déplacements que le joueur a effectué
     * depuis le début de la partie.
     * @return le nombre de déplacements qui est un entier positif
     */
    public int getMovingNumber()
    {
        return this.aMovingCounter;
    }
    
    /**
     * Renvoie un item de l'inventaire du joueur sans le retirer de
     * celui-ci.
     * @param pItemName nom de l'item à renvoyer
     * @return l'item demandé s'il est dans l'inventaire, null sinon
     */
    public Item getItemFromInventory(final String pItemName)
    {
        return this.aInventaire.get(pItemName);
    }
    
    /**
     * Fonction qui renvoie la pièce dans laquelle se trouve le joueur.
     * @return pièce courante du joueur
     */
    public Room getCurrentRoom()
    {
        return this.aCurrentRoom;
    }
    
    /**
     * Fonction qui change le joueur de pièce et incrémente
     * le compteur de déplacement de 1.
     * @param pNewCurrentRoom pièce dans laquelle le joueur veut se rendre
     */
    public void setCurrentRoom(final Room pNewCurrentRoom)
    {
        this.aCurrentRoom = pNewCurrentRoom;
        this.aMovingCounter += 1;
    }
    
    /**
     * Fonction qui enregistre la dernière pièce dans laquelle le joueur
     * était présent
     * @param pRoom pièce à enregistrer
     */
    public void addPreviousRoom(final Room pRoom)
    {
        this.aPreviousRooms.push(pRoom);
    }
    
    /**
     * Fonction qui renvoie la dernière pièce dans laquelle était le joueur
     * et ne la mémorise plus.
     * @return la pièce où se trouvait précédemment le joueur, ou celle actuelle
     * s'il n'était pas dans une autre pièce auparavant ou bien si
     * la pièce précédente n'est plus accessible
     */
    public Room popPreviousRoom()
    {
        /* s'il n'y a pas de pièce précédemment mémorisée ou que la
        dernière mémorisée n'est pas une sortie de la pièce actuelle,
        on renvoie la pièce actuelle
        */
        if (this.aPreviousRooms.empty() || 
        !this.aCurrentRoom.isExit(this.aPreviousRooms.peek()))
        {
            this.aPreviousRooms.clear();
            return this.getCurrentRoom();
        }
        Room vTete = this.aPreviousRooms.pop();
        if (vTete == null)
        {
            return this.getCurrentRoom();
        }
        return vTete;
    }
    
    /**
     * Fonction qui stocke l'item en paramètre dans l'inventaire
     * du joueur. Elle renvoie true si l'objet a bien été ajouté
     * dans l'inventaire, et false sinon.
     * @param pItem item à ajouter à l'inventaire
     */
    public void take(final Item pItem)
    {
        if (this.canAddToInventory(pItem))
        {
            this.aInventaire.add(pItem);
        }
    }
    
    /**
     * Fonction qui enlève l'item dans l'inventaire
     * du joueur et le renvoie.
     * @param pItemName nom de l'item à enlever
     * @return item enlevé de l'inventaire
     */
    public Item drop(final String pItemName)
    {
        return this.aInventaire.remove(pItemName);
    }
    
    /**
     * Fonction qui renvoie le poids total des items rangés dans
     * l'inventaire.
     * @return poids total de l'inventaire
     */
    public int getInventoryWeight()
    {
        return this.aInventaire.getTotalWeight();
    }
    
    /**
     * Fonction qui renvoie une chaine comportant des informations
     * sur l'inventaire, et qui est de la forme : 
     * "item_1, item_2, ... , item_n
     *  Poids total : x"
     *  @return chaine comportant des informations sur l'inventaire
     */
    public String getInventoryString()
    {
        return "Liste des items :\n" + this.aInventaire.getItemString() + 
        "\nPoids total : " + this.getInventoryWeight() + 
        "\nCapacité Max : " + this.aMaxWeight;
    }
    
    /**
     * Fonction qui indique si l'inventaire du joueur est plein ou non.
     * @return true si l'inventaire est plein, false sinon
     */
    public boolean isInventoryFull()
    {
        return this.getInventoryWeight() == this.aMaxWeight;
    }
    
    /**
     * Fonction qui indique si l'on peut ajouter l'item passé en parmaètre
     * à l'inventaire.
     * @param pItem item que l'on veut ajouter dans l'inventaire
     * @return true si on peut ajouter l'item sans dépasser la capacité
     * du joueur, false sinon
     */
    public boolean canAddToInventory(final Item pItem)
    {
        return this.getInventoryWeight() + pItem.getItemWeight() <= this.aMaxWeight;
    }
    
    /**
     * Fonction qui renvoie true si l'inventaire contient l'item passé
     * en paramètre
     * @param pItemName nom de l'item que l'on cherche dans l'inventaire
     * @return true si l'item est dans l'inventaire, false sinon
     */
    public boolean isInInventory(final String pItemName)
    {
        return this.aInventaire.isIn(pItemName);
    }
    
    /**
     * Fonction qui associe une action à l'item passé en paramètre et
     * renvoie un entier indiquant si l'exécution s'est bien déroulé.
     * 
     * @param pItemName nom de l'item que l'on veut utiliser
     * @return un entier entre [-1; 1] avec -1 si l'item passé
     * en paramètre n'est pas dans l'inventaire du joueur et 0
     * si tout s'est bien passé. Une autre valeur signifie qu'il faut
     * afficher un message spécifique d'erreur.
     */
    public int useItem(final String pItemName)
    {
        int vRes = 0;
        if (pItemName.equals("sac_à_dos"))
        {
            this.aMaxWeight += 10;
            this.drop(pItemName);
        }
        else if (pItemName.equals("bague_Dathomirienne"))
        {
            Beamer vBeamer = (Beamer)this.aInventaire.get(pItemName);
            if (vBeamer.isLoaded())
            {
                this.setCurrentRoom(vBeamer.fire());
                this.aPreviousRooms.clear();
            }
            else
            {
                vRes = 1; // signifie que le Beamer n'a pas pu être utilisé car non chargé
            }
        }
        else 
        {
            vRes = -1;
        }
        return vRes;
    }
    
    /**
     * Charge le Beamer que possède le joueur avec la pièce
     * courante.
     * @param pBeamerName nom du Beamer que le joueur possède dans
     * son inventaire
     */
    public void charge(final String pBeamerName)
    {
        Beamer vBeamer = (Beamer)this.aInventaire.get(pBeamerName);
        vBeamer.charge(this.getCurrentRoom());
    }
}