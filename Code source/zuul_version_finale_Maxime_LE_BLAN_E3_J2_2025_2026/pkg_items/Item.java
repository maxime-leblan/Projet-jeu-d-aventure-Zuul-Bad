package pkg_items;

/**
 * Classe permettant de créer un item pour le jeu qui pourra être présent
 * dans une des pièces du jeu.
 *
 * @author Maxime LE BLAN
 * @version 14/01/2026
 */
public class Item
{
    /**
     * Poids de l'item.
     */
    private int aItemWeight;
    /**
     * Description de l'item.
     */
    private String aItemDescription;
    /**
     * Nom de l'item.
     */
    private String aName;
    
    /**
     * Constructeur de la classe Item
     * @param pItemDescription Contient la description de l'item
     * @param pItemWeight Contient le poids de l'item
     * @param pName Contient le nom de l'item
     */
    public Item(final String pItemDescription, final int pItemWeight, final String pName)
    {
        this.aItemWeight = pItemWeight;
        this.aItemDescription = pItemDescription;
        this.aName = pName;
    }
    
    /**
     * Fonction qui renvoie la valeur du poids de l'item.
     * @return poids de l'item
     */
    public int getItemWeight()
    {
        return this.aItemWeight;
    }
    
    /**
     * Fonction qui renvoie la description de l'item.
     * @return description de l'item
     */
    public String getItemDescription()
    {
        return this.aItemDescription;
    }
    
    /**
     * Fonction qui renvoie le nom de l'item.
     * @return nom de l'item
     */
    public String getName()
    {
        return this.aName;
    }
    
    /**
     * Fonction qui renvoie une description détaillée de l'item avec toutes
     * ses caractéristiques
     * ex : "L'item Fusil DC-15A pèse 3 kilo(s)"
     * @return longue description de l'item
     */
    public String getItemLongDescription()
    {
        return "L'item " + getName() + " qui est " + getItemDescription() + " pèse " + getItemWeight() + " kilo(s)";
    }
}