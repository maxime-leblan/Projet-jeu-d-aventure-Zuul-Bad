import java.util.HashMap;
import java.util.Set;
import java.util.Map;

import pkg_items.ItemList;
import pkg_items.Item;
/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 *
 * @author Maxime LE BLAN
 * @version 14/01/2026
 */
public class Room
{
    /**
     * Description de la pièce.
     */
    private String aDescription;
    /**
     * Type de direction inconnue.
     */
    public static final Room UNKNOWN_DIRECTION = new Room( "Direction inconnue !", "Aucune image" );
    /**
     * HashMap avec pour clé une direction de sortie possible de la pièce
     * et en valeur une autre pièce.
     */
    private HashMap<String, Room> aExits;
    /**
     * ItemList contenant la liste des items présents dans la pièce.
     */
    private ItemList aItems;
    
    /**
     * Contient les PNJ présents dans la pièce.
     */
    private HashMap<String, Character> aCharacters;
    
    /**
     * Liste contenant l'ensemble des directions existantes dans le jeu.
     */
    private static final String[] aDirectionsList = {
    "north", "east", "west", "south", "up", "down"};
    /**
     * Nom de l'image dans l'interface graphique associée à la pièce.
     */
    private String aImageName;
    /**
     * Constructeur de la classe Room
     * @param pDescription - String contenant une description de la pièce
     * @param pImage image associée à la pièce
     */
    public Room(final String pDescription, final String pImage)
    {
        this.aDescription = pDescription;
        this.aExits = new HashMap<String, Room>();
        this.aCharacters = new HashMap<String, Character>();
        this.aItems = new ItemList();
        this.aImageName = pImage;
    }//Room
    
    /**
     * Ajoute un PNJ dans la pièce.
     * @param pNPC PNJ à ajouter dans la pièce
     */
    public void addCharacter(final Character pNPC)
    {
        this.aCharacters.put(pNPC.getName(), pNPC);
    }
    
    /**
     * Enlève le PNJ de la pièce.
     * @param pNPCName nom du PNJ que l'on veut retirer de la pièce
     */
    public void removeCharacter(final String pNPCName)
    {
        this.aCharacters.remove(pNPCName);
    }
    
    /**
     * Renvoie le PNJ présent dans la pièce portant le nom passé en
     * paramètre.
     * @param pNPCName nom du PNJ que l'on veut récupérer
     * @return un PNJ de type Character, ou null s'il n'y en a pas
     */
    public Character getCharacter(final String pNPCName)
    {
        return this.aCharacters.get(pNPCName);
    }
    
    /**
     * Fonction qui renvoie une String contenant tous les PNJ présents dans la pièce
     * @return chaine du type "PNJ_1 ... PNJ_n"
     */
    public String getCharacterString()
    {
        String vResult = "";
        for(String vNPCName : this.aCharacters.keySet())
        {
            vResult += vNPCName + " ";
        }
        if (vResult == "")
            vResult = "Aucun";
        return vResult;
    }
    
    /**
     * Accesseur qui renvoie la description de la pièce
     * @return la description de la pièce.
     */
    public String getDescription()
    {
        return this.aDescription;
    }//getDescription
    
    /**
     * Indique si la pièce passé en paramètre est une sortie
     * de la pièce courante.
     * @param pRoom la pièce qui est une potentielle sortie
     * @return true si pRoom est une sortie de la pièce courante,
     * false sinon
     */
    public boolean isExit(final Room pRoom)
    {
        return this.aExits.containsValue(pRoom);
    }
    
    /**
     * Procédure qui ajoute une sortie à la pièce courante
     * @param pDirection direction de la sortie
     * @param pNeighbor pièce correspondant à la sortie à ajouter à la pièce courante
     */
    public void setExit(final String pDirection, final Room pNeighbor)
    {
        this.aExits.put(pDirection, pNeighbor);
    }//setExits
    
    /**
     * Méthode qui ajoute un item à la liste des items présents dans
     * la pièce
     * @param pItem Item à ajouter dans la pièce
     */
    public void addItem(final Item pItem)
    {
        this.aItems.add(pItem);
    }
    
    /**
     * Fonction qui supprime un item de la pièce.
     * @param pItem item à supprimer de la pièce
     * @return l'item supprimé, ou null si la suppression a échoué
     */
    public Item removeItem(final Item pItem)
    {
        return this.aItems.remove(pItem.getName());
    }
    
    /**
     * Fonction qui renvoie la sortie (pièce) de la pièce courante associée à la direction pDirection
     * @param pDirection String contenant une direction
     * @return sortie associée à la direction, vaut UNKNOWN_DIRECTION si
     * la sortie entrée en paramètre n'existe pas.
     */
    public Room getExit(String pDirection)
    {
        
        for(String vDirection : Room.aDirectionsList)
        {
            if(vDirection.equals(pDirection))
            {
                return this.aExits.get(pDirection);
            }
        }
        
        return Room.UNKNOWN_DIRECTION;
    }//getExit
    
    /**
     * Fonction qui renvoie une String contenant toutes les directions accessibles depuis la pièce courante
     * @return chaine du type "direction_1 ... direction_n"
     */
    public String getExitString()
    {
        String vExitString = "";
        for(String vDirection : this.aExits.keySet())
        {
            if(this.getExit(vDirection) != null)
            {
                vExitString += vDirection + " ";
            }
        }
        
        return vExitString;
    }//getExitString
    
    /**
     * Fonction renvoyant la liste des items de la pièce sous la
     * forme "item1 item2 ... itemn"
     * @return chaine de la forme "item1 item2 ... itemn"
     */
    public String getItemString()
    {
        return this.aItems.getItemString();
    }
    
    /**
     * Fonction qui renvoie l'item associé à son nom.
     * La fonction renvoie null si elle ne trouve pas l'item
     * @param pItemName nom de l'item à renvoyer
     * @return item associé au paramètre, null s'il n'y a pas d'associations
     */
    public Item getItem(final String pItemName)
    {
        return this.aItems.get(pItemName);
    }
    
    /**
     * Affiche une description de la pièce, des sorties accessibles,
     * des items et des PNJ présents dans la pièce
     * @return description de la pièce
     */
    public String getLongDescription()
    {
        return "Vous êtes " + this.aDescription + ".\nSortie(s) : " + getExitString() + "\nItem(s) : " + getItemString()
        + "\nPNJ(s) : " + this.getCharacterString();
    }//getLongDescription
    
    /**
     * Renvoie une description de l'image de la pièce
     * @return description de l'image
     */
    public String getImageName()
    {
         return this.aImageName;
    }//getImageName
    
    /**
     * Indique si la pièce passée en paramètre est égale à celle qui a
     * appelé la fonction.
     * Deux pièces sont égales si elles ont la même description et 
     * le même nom d'image.
     * @param pRoom la pièce à comparer
     * @return true si les deux pièces sont égales, false sinon
     */
    @Override
    public boolean equals(Object pRoom)
    {
        Room vRoom = (Room)pRoom;
        return this.getDescription().equals(vRoom.getDescription())
            && this.getImageName().equals(vRoom.getImageName());
    }
} // Room
