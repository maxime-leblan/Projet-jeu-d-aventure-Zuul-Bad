package pkg_commands;


 
/**
 * Classe Command - une commande du jeu d'aventure Zuul.
 *
 * @author Maxime LE BLAN
 * @version 14/01/2026
 */
public class Command
{
    /**
     * Premier mot contenant la commande
     */
    private String aCommandWord; 
    /**
     * Deuxième mot contenant le premier paramètre
     */
    private String aSecondWord;
    
    /**
     * Troisième mot contenant le deuxième paramètre
     */
    private String aThirdWord;
    
    /**
     * Constructeur de la classe commande
     * @param pCommandWord premier mot correspondant à l'instruction de la commande appelée
     * @param pSecondWord deuxième mot correspondant au premier paramètre associée à la commande
     * @param pThirdWord troisième mot correspondant au deuxième paramètre associée à la commande
     */
    public Command(final String pCommandWord, final String pSecondWord,
                    final String pThirdWord)
    {
        this.aCommandWord = pCommandWord; 
        this.aSecondWord = pSecondWord;
        this.aThirdWord = pThirdWord;
    }//Command
    
    /**
     * Accesseur renvoyant le premier mot de la commande
     * @return la chaine contenant le premier mot ou null si il n'y en a pas
     */
    public String getCommandWord()
    {
        return this.aCommandWord;
    }//getCommandWord
    
    /**
     * Accesseur renvoyant le second mot de la commande
     * @return la chaine contenant le deuxième mot ou null si il n'y en a pas
     */
    public String getSecondWord()
    {
        return this.aSecondWord;
    }//getSecondWord
    
    /**
     * Accesseur renvoyant le troisième mot de la commande
     * @return la chaine contenant le troisième mot ou null si il n'y en a pas
     */
    public String getThirdWord()
    {
        return this.aThirdWord;
    }
    
    /**
     * Fonction indiquant si la commande possède un second mot / premier paramètre ou non
     * @return true s'il y a un second mot, false sinon
     */
    public boolean hasSecondWord()
    {
        return this.aSecondWord != null;
    }//hasSecondWord
    
    /**
     * Fonction indiquant si la commande possède un troisième mot / deuxième paramètre ou non
     * @return true s'il y a un troisième mot, false sinon
     */
    public boolean hasThirdWord()
    {
        return this.aThirdWord != null;
    }
    
    /**
     * Fonction indiquant si la commande est connue ou non
     * @return true si le premier mot vaut null, false sinon 
     */
    public boolean isUnknown()
    {
        return this.aCommandWord == null;
    }//isUnknown
} // Command
