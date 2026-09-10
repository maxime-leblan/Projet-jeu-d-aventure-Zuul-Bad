package pkg_items;

import java.util.HashMap;

/**
 * Structure de donnée basée sur un HashMap et qui est chargée de stocker
 * une liste d'items que l'on pourrait repérer par leur nom.
 * @author Maxime LE BLAN
 * @version 14/01/2026
 */
public class ItemList
{
    /**
     * HashMap ayant pour clé le nom d'un item et en valeur l'item
     * correspondant.
     */
    private HashMap<String, Item> aItemList;
    
    /**
     * Constructeur par défaut de la classe.
     */
    public ItemList()
    {
        this.aItemList = new HashMap<String, Item>();
    }
    
    /**
     * Fonction qui renvoie l'item associé à pItemName dans la ItemList
     * @param pItemName nom de l'item à renvoyer
     * @return item associé au paramètre, ou null si celui-ci n'est pas
     * stocké dans le HashMap
     */
    public Item get(final String pItemName)
    {
        return this.aItemList.get(pItemName);
    }
    
    /**
     * Fonction qui ajoute un item dans la ItemList
     * @param pItem nom de l'item à ajouter
     */
    public void add(final Item pItem)
    {
        this.aItemList.put(pItem.getName(), pItem);
    }
    
    /**
     * Fonction qui supprime un item dans la ItemList
     * @param pItemName nom de l'item à supprimer
     * @return item à supprimer, ou null si la suppression a échoué
     */
    public Item remove(final String pItemName)
    {
        return this.aItemList.remove(pItemName);
    }
    
    /**
     * Fonction renvoyant la liste des items de la pièce sous la
     * forme "item1 item2 ... itemn"
     * @return la chaine d'item
     */
    public String getItemString()
    {
        String vItemString = "";
        for(String vItemName : this.aItemList.keySet())
        {
            vItemString += vItemName + ", ";
        }
        
        if (vItemString == "")
        {
            return "Aucun";
        }
        else
        {
            return vItemString;
        }
    }
    
    /**
     * Fonction qui renvoie le poids total de la Liste d'items
     * @return poids total
     */
    public int getTotalWeight()
    {
        int vTotalWeight = 0;
        for (String vItemName : this.aItemList.keySet())
        {
            vTotalWeight += this.aItemList.get(vItemName).getItemWeight();
        }
        return vTotalWeight;
    }
    
    /**
     * Fonction qui renvoie true si la Liste d'item contient l'item passé
     * en paramètre
     * 
     * @param pItemName nom de l'item que l'on cherche dans la Liste d'items
     * @return true si item est dans la ItemList, false sinon
     */
    public boolean isIn(final String pItemName)
    {
        return this.aItemList.containsKey(pItemName);
    }
}