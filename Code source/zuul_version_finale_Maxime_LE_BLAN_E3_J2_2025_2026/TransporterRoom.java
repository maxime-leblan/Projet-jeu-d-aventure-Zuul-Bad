
/**
 * Classe héritière de la classe Room chargé d'instancier des TransporterRoom
 * ==> pièce pour pouvoir se téléporter aléatoirement dans une autre pièce du
 * jeu
 * @author Maxime LE BLAN
 * @version 14/01/2026
 */
public class TransporterRoom extends Room
{
    /**
     * Contient la pièce que doit renvoyer la fonction getExit si l'on
     * est en mode test.
     */
    private Room aNextRandomRoom;
    
    /**
     * Constructeur de la classe TransporterRoom.
     * @param pDescription - String contenant une description de la pièce
     * @param pImage image associée à la pièce
     */
    public TransporterRoom(final String pDescription, final String pImage)
    {
        super(pDescription, pImage);
    }
    
    /**
     * Change la valeur de la prochaine pièce aléatoire que doit
     * renvoyer la fonction getExit.
     * @param pRoom pièce que devra renvoyer getExit
     */
    public void setNextRandomRoom(final Room pRoom)
    {
        this.aNextRandomRoom = pRoom;
    }
    
    /**
     * Fonction qui renvoie une sortie choisie aléatoirement.
     * @param pDirection String contenant une direction (pas utile ici)
     * @return une pièce du jeu
     */
    @Override
    public Room getExit(String pDirection)
    {
        Room vNextRoom;
        if (this.aNextRandomRoom != null)
            vNextRoom = this.aNextRandomRoom;
        else
            vNextRoom = RoomRandomizer.findRandomRoom();
        
        while (this.equals(vNextRoom))
        {
            vNextRoom = RoomRandomizer.findRandomRoom();
        }
        
        return vNextRoom;
    }
}