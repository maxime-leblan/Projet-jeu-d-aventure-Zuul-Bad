import java.util.HashMap;

/**
 * Classe chargée d'implémenter des PNJ.
 * 
 * @author Maxime LE BLAN
 * @version 14/01/2026
 */
public class Character
{
    /**
     * Nom du PNJ.
     */
    private String aName;
    
    /**
     * Contient toutes les phrases que doit dire le PNJ en
     * fonction de l'item que le joueur lui donne.
     * La clé contient le nom de l'item et la valeur contient
     * les informations correspondantes.
     */
    private HashMap<String, String> aItemsInfo;
    
    /**
     * Indique si le PNJ a déjà rencontré tel ou tel autre joueur.
     * La clé est le nom du joueur et la valeur un booléen indiquant
     * si le PNJ a déjà rencontré celui-ci.
     */
    private HashMap<String, Boolean> aPlayersMeeting;
    
    /**
     * Texte par défaut que dit le PNJ dès que le joueur veut
     * parler avec lui.
     */
    private String aDefaultText;
    
    /**
     * Constructeur du PNJ.
     * @param pName nom du PNJ
     * @param pDefaultText texte que le PNJ dit par défaut lorsqu'il interagit avec le joueur
     */
    public Character(final String pName, final String pDefaultText)
    {
        this.aName = pName;
        this.aDefaultText = pDefaultText;
        this.aItemsInfo = new HashMap<String, String>();
        this.aPlayersMeeting = new HashMap<String, Boolean>();
    }
    
    /**
     * Renvoie le nom du PNJ.
     * @return nom du PNJ
     */
    public String getName()
    {
        return this.aName;
    }
    
    /**
     * Indique si le joueur passé en paramètre a déjà été rencontré
     * par le PNJ.
     * @param pPlayerName nom du joueur décrit ci-dessus
     * @return true si le joueur a déjà été rencontré, false sinon
     */
    public boolean hasMetPlayer(final String pPlayerName)
    {
        Boolean vResult = this.aPlayersMeeting.get(pPlayerName);
        if (vResult == null)
            return false;
        
        return vResult.booleanValue();
    }
    
    /**
     * Indique que le PNJ a rencontré le joueur dont le nom est passé en
     * paramètre.
     * @param pPlayerName nom du joueur décrit ci-dessus
     */
    public void meetPlayer(final String pPlayerName)
    {
        this.aPlayersMeeting.put(pPlayerName, new Boolean(true));
    }
    
    /**
     * Renvoie le message de présentation que le PNJ dit lorsqu'il
     * rencontre le joueur au début d'une interaction.
     * @return la chaine de caractère comportant le message
     */
    public String getIntroductionText()
    {
        return this.getName() + "> Bonjour, je m'appelle " + this.getName() + ".\n" +
        this.aDefaultText;
    }
    
    /**
     * Donne l'information associée à l'item passé en paramètre
     * au PNJ pour qu'il puisse par la suite la donner au joueur
     * si nécessaire.
     * @param pItemName nom de l'item
     * @param pInformation information à associer avec l'item
     */
    public void addItemInfo(final String pItemName, final String pInformation)
    {
        this.aItemsInfo.put(pItemName, pInformation);
    }
    
    /**
     * Renvoie l'information associée à l'item dont le nom a été passé
     * en paramètre.
     * @param pItemName nom de l'item dont on désire obtenir plus d'infos
     * @return les informations associées à l'item sous la forme d'une 
     * chaine, ou bien un message indiquant que le PNJ n'a pas d'info
     * dessus s'il n'en trouve pas dans sa base de donnée.
     */
    public String getItemInfo(final String pItemName)
    {
        String vInfo = this.aItemsInfo.get(pItemName);
        if (vInfo == null)
            vInfo = "Je n'ai pas d'informations sur l'item " + 
            pItemName + ", essayez avec quelqu'un d'autre !";
        
        return this.getName() + "> " + vInfo;
    }
}