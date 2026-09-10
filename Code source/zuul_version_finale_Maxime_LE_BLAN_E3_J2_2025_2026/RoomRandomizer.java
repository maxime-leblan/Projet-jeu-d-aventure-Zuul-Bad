import java.util.Random;
import java.util.ArrayList;

/**
 * Classe chargée de sélectionner aléatoirement une pièce parmi toutes celles
 * du jeu.
 * @author Maxime LE BLAN
 * @version 14/01/2026
 */
public class RoomRandomizer
{
    /**
     * Générateur de nombre aléatoire.
     */
    private final static Random aRandom = new Random();
    
    /**
     * Constructeur de la classe RoomRandomizer (vide)
     */
    public RoomRandomizer(){}
    
    /**
     * Choisit aléatoirement une pièce parmi toutes celles existantes dans
     * le jeu et la renvoie.
     * @return une pièce du jeu
     */
    public static Room findRandomRoom()
    {
       /* On crée un tableau pour pouvoir recopier les pièces stockées
        * dans le Hashmap sRooms
       */
       ArrayList<Room> vRoomsList = new ArrayList<Room>();
       
       for (String vRoomName : GameEngine.sRooms.keySet())
       {
           vRoomsList.add(GameEngine.sRooms.get(vRoomName));
       }
       
       /*
        * On choisit aléatoirement un nombre comprit entre  et 
        * <nombre_total_de_pieces> qui est l'indice de la pièce que l'on
        * choisit aléatoirement dans le tableau vRoomsList
        */
       int vRoomsNumber = vRoomsList.size();
       return vRoomsList.get(RoomRandomizer.aRandom.nextInt(vRoomsNumber));
    }
}