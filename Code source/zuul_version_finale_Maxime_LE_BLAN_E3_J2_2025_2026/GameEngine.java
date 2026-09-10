import java.util.HashMap;
import java.util.Stack;

import pkg_items.Item;
import pkg_commands.Command;

/**
 * Moteur principal du jeu, qui contient les différentes commandes du joueur
 * et gère le déroulement du jeu.
 *
 * @author Maxime LE BLAN
 * @version 14/01/2026
 */
public class GameEngine
{
    /**
     * Attribut contenant le gestionnaire des entrées au clavier.
     */
    private Parser        aParser;
    /**
     * Attribut contenant l'interface graphique de notre jeu.
     */
    private UserInterface aGui;
    /**
     * HashMap qui stocke toutes les pièces existantes de notre jeu.
     */
    public static HashMap<String, Room> sRooms;
    /**
     * Attribut contenant notre joueur.
     */
    private Player aPlayer;
    /**
     * Nombre limité de déplacement que le joueur est autorisé à faire
     * pour gagner la partie.
     */
    private int aMovingLimit;
    /**
     * Indique si le jeu est en mode test ou non.
     */
    private boolean aTestMod;
    
    /**
     * HashMap contenant tous les PNJ du jeu qui peuvent se déplacer.
     */
    private HashMap<String, MovingCharacter> aMovingCharacters;
    
    static
    {
        sRooms = new HashMap<String, Room>();
    }

    /**
     * Constructeur de la classe GameEngine.
     */
    public GameEngine()
    {
        this.aParser = new Parser();
        this.aMovingCharacters = new HashMap<String, MovingCharacter>();
        this.createRooms();
        this.aPlayer = new Player("CT-1905", GameEngine.sRooms.get("entree_sim"));
        this.createItems();
        this.createCharacters();
        this.aMovingLimit = 100;
        this.aTestMod = false;
    }
    
    /**
     * Fonction qui initialise l'interface graphique et affiche un
     * message de bienvenue au joueur.
     * @param pUserInterface interface graphique du jeu
     */
    public void setGUI( final UserInterface pUserInterface )
    {
        this.aGui = pUserInterface;
        this.printWelcome();
    }

    /**
     * Crée toutes les pièces et associe des sorties à chacune d'entre elles.
     */
    private void createRooms()
    {
        // déclaration des pièces
        Room vEntreeSim = new Room("à l'entrée de la zone de simulation de l'exercice de la Citadelle", "couloir_3.png");
        Room vCourExt = new Room("dans la cour extérieure de la Citadelle", "hangar_3.png");
        Room vEntreeCitadelle = new Room("dans l'entrée de la Citadelle", "entree_citadelle.png");
        Room vCouloirA = new Room("dans le couloir A", "couloir_1.png");
        Room vCouloirB = new Room("dans le couloir B", "couloir_2.png");
        Room vAscenseurRC = new Room("dans l'ascenseur au rez-de-chaussée", "ascenseurs.png");
        Room vAscenseurE1 = new Room("dans l'ascenseur au premier étage", "ascenseurs.png");
        Room vEscalier = new Room("dans la cage d'escalier menant au toit", "escaliers.png");
        Room vHangar = new Room("dans le hangar principal", "hangar_2.png");
        Room vArmurerie = new Room("dans l'armurerie", "armurerie.png");
        TransporterRoom vInfirmerie = new TransporterRoom("dans l'infirmerie", "infirmerie.png");
        Room vPosteCommandement = new Room("dans le poste de commandement de la Citadelle", "poste_commandement.png");
        Room vSalleStockage = new Room("dans la salle de stockage", "hangar_1.png");
        Room vToit = new Room("sur le toit de la Citadelle", "toit_vierge.png");
        
        // affectation des positions de chaque pièce
        vEntreeSim.setExit("east", vCourExt);
        
        // on met en commentaire la ligne ci-dessous car vCourExt
        // a une trapdoor à l'ouest.
        // vCourExt.setExit("west", vEntreeSim);
        vCourExt.setExit("east", vEntreeCitadelle);
        
        vEntreeCitadelle.setExit("north", vCouloirB);
        // on met en commentaire la ligne ci-dessous car vEntreeCitadelle
        // a une trapdoor à l'ouest.
        // vEntreeCitadelle.setExit("west", vCourExt);
        vEntreeCitadelle.setExit("east", vSalleStockage);
        vEntreeCitadelle.setExit("south", vCouloirA);
        
        vCouloirB.setExit("north", vArmurerie);
        vCouloirB.setExit("south", vEntreeCitadelle);
        
        vArmurerie.setExit("south", vCouloirB);
        
        vCouloirA.setExit("north", vEntreeCitadelle);
        vCouloirA.setExit("south", vInfirmerie);
        
        vInfirmerie.setExit("north", vCouloirA);
        
        vSalleStockage.setExit("west", vEntreeCitadelle);
        vSalleStockage.setExit("east", vAscenseurRC);
        
        vAscenseurRC.setExit("up", vAscenseurE1);
        vAscenseurRC.setExit("west", vSalleStockage);
        
        vAscenseurE1.setExit("down", vAscenseurRC);
        vAscenseurE1.setExit("west", vHangar);
        
        vHangar.setExit("east", vAscenseurE1);
        vHangar.setExit("south", vPosteCommandement);
        
        vPosteCommandement.setExit("north", vHangar);
        vPosteCommandement.setExit("south", vEscalier);
        
        vEscalier.setExit("north", vPosteCommandement);
        vEscalier.setExit("up", vToit);
        
        vToit.setExit("down", vEscalier);
        
        
        
        // on enregistre les pièces dans un HashMap pour pouvoir y accéder depuis toutes les classes
        GameEngine.sRooms.put("entree_sim", vEntreeSim);
        GameEngine.sRooms.put("cour_ext", vCourExt);
        GameEngine.sRooms.put("entree_citadelle", vEntreeCitadelle);
        GameEngine.sRooms.put("couloir_a", vCouloirA);
        GameEngine.sRooms.put("couloir_b", vCouloirB);
        GameEngine.sRooms.put("ascenseur_RC", vAscenseurRC);
        GameEngine.sRooms.put("ascenseur_E1", vAscenseurE1);
        GameEngine.sRooms.put("escalier", vEscalier);
        GameEngine.sRooms.put("hangar", vHangar);
        GameEngine.sRooms.put("armurerie", vArmurerie);
        GameEngine.sRooms.put("infirmerie", vInfirmerie);
        GameEngine.sRooms.put("poste_commandement", vPosteCommandement);
        GameEngine.sRooms.put("salle_stockage", vSalleStockage);
        GameEngine.sRooms.put("toit", vToit);
    }// createRooms
    
    /**
     * Méthode qui crée les items et les répartit dans leurs pièces 
     * correspondantes.
     */
    private void createItems()
    {
        // déclaration des items
        Item vFusilDC15A = new Item("un fusil blaster longue distance", 3, "DC-15A");
        Item vFusilDC15S = new Item("un fusil blaster moyenne distance", 2, "DC-15S");
        Item vDetonateurT = new Item("une grenade explosive", 1, "détonateur_thermique");
        Item vDetonateurI = new Item("une grenade ionique", 1, "détonateur_ionique");
        Item vMacroJumelles = new Item("une jumelle avec vision des champs électriques", 1, "macro_jumelles");
        Item vCleCrochetage = new Item("un outil pour dévérouiller les portes fermées", 1, "clé_de_crochetage");
        Item vMedikit = new Item("un kit de premiers soins", 4, "médikit");
        Item vSacADos = new Item("un sac permettant de porter son équipement", 2, "sac_à_dos");
        Item vBrouilleur = new Item("un appareil pour perturber les communications ennemies", 1, "brouilleur");
        Item vCartouche = new Item("une cartouche de gaz de Tibanna alimentant les fusils blasters", 1, "cartouche_de_Tibanna");
        Item vCouteau = new Item("une lame pour le combat rapproché", 1, "couteau_de_combat");
        Item vDrapeau = new Item("le drapeau de la Citadelle", 3, "drapeau");
        
        // déclaration du Beamer
        Beamer vBeamer = new Beamer("une bague contenant un peu de magie des Soeurs de la Nuits", 1, "bague_Dathomirienne");
        
        // répartition des items dans chaque pièce
        GameEngine.sRooms.get("armurerie").addItem(vFusilDC15A);
        GameEngine.sRooms.get("armurerie").addItem(vDetonateurT);
        GameEngine.sRooms.get("armurerie").addItem(vDetonateurI);
        GameEngine.sRooms.get("armurerie").addItem(vMacroJumelles);
        GameEngine.sRooms.get("armurerie").addItem(vCartouche);
        GameEngine.sRooms.get("salle_stockage").addItem(vCleCrochetage);
        GameEngine.sRooms.get("salle_stockage").addItem(vSacADos);
        GameEngine.sRooms.get("salle_stockage").addItem(vBrouilleur);
        GameEngine.sRooms.get("salle_stockage").addItem(vBeamer);
        GameEngine.sRooms.get("infirmerie").addItem(vMedikit);
        GameEngine.sRooms.get("entree_sim").addItem(vCouteau);
        GameEngine.sRooms.get("entree_sim").addItem(vFusilDC15S);
        GameEngine.sRooms.get("toit").addItem(vDrapeau);
    }
    
    /**
     * Crée tous les PNJ et les places dans leur salle correspondante.
     */
    private void createCharacters()
    {
        // création des personnages du jeu
        Character vProtocolDroid = new Character("RA-7", "Demandez moi" +
        " plus d'informations sur un objet avec la commande 'ask RA-7 <nom de l'item>'.");
        
        Character vAstromecDroid = new Character("R4-D7", "* Excited Beeping noises * Beep Boop, Waaaahow !");
        
        MovingCharacter vR2D2 = new MovingCharacter("R2-D2", "Buuup, Wow Wow Weeez !",
        GameEngine.sRooms.get("entree_citadelle"));
        
        // on ajoute les PNJ pouvant se déplacer au HashMap les contenants
        this.aMovingCharacters.put(vR2D2.getName(), vR2D2);
        
        // on ajoute les chemins des PNJ pouvant se déplacer
        vR2D2.addToPath(GameEngine.sRooms.get("couloir_a"));
        vR2D2.addToPath(GameEngine.sRooms.get("infirmerie"));
        vR2D2.addToPath(GameEngine.sRooms.get("couloir_a"));
        vR2D2.addToPath(GameEngine.sRooms.get("entree_citadelle"));
        vR2D2.addToPath(GameEngine.sRooms.get("couloir_b"));
        vR2D2.addToPath(GameEngine.sRooms.get("armurerie"));
        vR2D2.addToPath(GameEngine.sRooms.get("couloir_b"));
        
        // placement des personnages dans les pièces du jeu
        GameEngine.sRooms.get("salle_stockage").addCharacter(vProtocolDroid);
        GameEngine.sRooms.get("salle_stockage").addCharacter(vAstromecDroid);
        
        // affectation des informations que possèdent les PNJ
        vProtocolDroid.addItemInfo("clé_de_crochetage", "Clé permettant de"
        + " dévérouiller des portes (ne fonctionne pas actuellement)");
        vProtocolDroid.addItemInfo("sac_à_dos", "Permet d'augmenter le"
        + " poids total d'objets que vous pouvez porter. Tapez 'use sac_à_dos'"
        + " pour pouvoir l'utiliser.");
        vProtocolDroid.addItemInfo("brouilleur", "Permet de brouiller les"
        + "communications (ne fonctionne pas actuellement)");
        vProtocolDroid.addItemInfo("brouilleur", "Permet de brouiller les"
        + "communications (ne fonctionne pas actuellement)");
        vProtocolDroid.addItemInfo("bague_Dathomirienne", 
        "Permet de charger une pièce dans la bague avec la commande 'charge bague_Dathomirienne'"
        + ", puis de se téléporter dedans avec la commande 'use bague_Dathomirienne'");
    }
    
    /**
     * Affiche un message de bienvenue au joueur.
     */
    private void printWelcome()
    {
        this.aGui.println( "Bienvenue dans l'Epreuve de la Citadelle, jeune"
        + " recrue !");
        this.aGui.println("Votre mission est de récupérer le drapeau se "
        + "trouvant sur le Toit de la Citadelle.");
        this.aGui.println("Si jamais vous êtes bloqué, tapez 'help' "
        + "dans le champs de saisie.\n"
        + "Que la force soit avec vous !\n");
        Room vCurrentRoom = this.aPlayer.getCurrentRoom();
        this.aGui.println( vCurrentRoom.getLongDescription() );
        if ( vCurrentRoom.getImageName() != null )
            this.aGui.showImage( vCurrentRoom.getImageName() );
    }
    
    /**
     * Vérifie d'abord si la partie est terminée ou non. Si elle
     * l'est, alors elle appelle endGame() et se termine. Sinon,
     * elle exécute la fonction associée à la commande passée en paramètre.
     * @param pCommandLine commande saisie par le joueur
     */
    public void interpretCommand( final String pCommandLine ) 
    {
        //this.aGui.println("<DANS LE INTERPRETCOMMAND>");
        if (this.gameIsFinished())
        {
            //this.aGui.println("<DANS LE GAMEISFINISHED>");
            this.endGame();
            return;
        }
        this.aGui.println( "> " + pCommandLine );
        Command vCommand = this.aParser.getCommand(pCommandLine);

        if ( vCommand.isUnknown() ) {
            this.aGui.println( "Je ne connais pas cette instruction..." );
            return;
        }

        String vCommandWord = vCommand.getCommandWord();
        if ( vCommandWord.equals( "help" ) )
            this.printHelp();
        else if ( vCommandWord.equals( "go" ) )
            this.goRoom( vCommand );
        else if ( vCommandWord.equals( "look" ) )
            this.look( vCommand );
        else if ( vCommandWord.equals( "eat" ) )
            this.eat( vCommand );
        else if (vCommandWord.equals("back"))
        {
            // this.aGui.println("<DANS LE BACK>");
            this.back(vCommand);
        }
        else if (vCommandWord.equals("test"))
        {
            this.test(vCommand);
        }
        else if (vCommandWord.equals("take"))
        {
            this.take(vCommand);
        }
        else if (vCommandWord.equals("drop"))
        {
            this.drop(vCommand);
        }
        else if (vCommandWord.equals("items"))
        {
            this.items(vCommand);
        }
        else if (vCommandWord.equals("use"))
        {
            this.use(vCommand);
        }
        else if (vCommandWord.equals("charge"))
        {
            this.charge(vCommand);
        }
        else if (vCommandWord.equals("ask"))
        {
            this.ask(vCommand);
        }
        else if (vCommandWord.equals("alea"))
        {
            if (!this.aTestMod)
                this.aGui.println("Commande accessible uniquement en mode test !");
            else
                this.alea(vCommand);
        }
        else if ( vCommandWord.equals( "quit" ) ) {
            if ( vCommand.hasSecondWord() )
                this.aGui.println( "Quit what?" );
            else
                this.endGame();
        }
    }
    
    /**
     * Permet au joueur de demander des informations à un PNJ concernant
     * un item du jeu.
     * 
     * @param pCommande commande tapée dans le champs de saisie
     */
    public void ask(final Command pCommande)
    {
        if (!pCommande.hasSecondWord()) {
            // on vérifie qu'il n'y a pas de 2e paramètre
            this.aGui.println( "Il faut dire à quel personnage vous voulez parler !" );
            return;
        }
        
        String vNPCName = pCommande.getSecondWord();
        Room vCurrentPlayerRoom = this.aPlayer.getCurrentRoom();
        Character vNPC = vCurrentPlayerRoom.getCharacter(vNPCName);
        
        if (vNPC == null){
            this.aGui.println("Il n'y a pas le personnage " + vNPCName + " dans cette pièce !");
            return;
        }
        
        if (!pCommande.hasThirdWord()) {
            // on vérifie qu'il n'y a pas de 2e paramètre
            this.aGui.println( "Il faut donner un item au personnage pour obtenir"
            + " des informations dessus !" );
            return;
        }
        
        
        String vItemName = pCommande.getThirdWord();
        // this.aGui.println("NPC : " + vNPCName + " | Item : " + vItemName);
        
        if (!vNPC.hasMetPlayer(this.aPlayer.getName()))
        {
            this.aGui.println(vNPC.getIntroductionText());
            vNPC.meetPlayer(this.aPlayer.getName());
        }
        
        this.aGui.println(vNPC.getItemInfo(vItemName));
    }
    
    /**
     * Fonction qui affiche des informations sur l'inventaire
     * du joueur.
     * 
     * @param pCommande commande tapée dans le champs de saisie
     */
    public void items(final Command pCommande)
    {
        if (pCommande.hasSecondWord()) {
            // on vérifie qu'il n'y a pas de 2e paramètre
            this.aGui.println( "Pas besoin de préciser un item !" );
            return;
        }
        this.aGui.println(this.aPlayer.getInventoryString());
    }
    
    /**
     * Fonction qui joue le rôle de la fonction eat de l'exercice 7.34 et
     * joue également le rôle de la commande fire de l'exercice 7.44
     * ==> vérifie si l'item passé en paramètre peut-être utilisé ou non
     * et si oui, effectue les actions correspondantes à l'item.
     * @param pCommande commande saisie par le joueur
     */
    public void use(final Command pCommande)
    {
        if ( ! pCommande.hasSecondWord() ) {
            // on vérifie que le nom de l'item a bien été passé en paramètre
            this.aGui.println( "Utiliser quel item ?" );
            return;
        }
        
        String vItemName = pCommande.getSecondWord();
        
        if (!this.aPlayer.isInInventory(vItemName))
        {
            this.aGui.println("L'item " + vItemName + " n'est pas dans l'inventaire !");
            return;
        }
        
        /* on récupère le résultat indiquant l'état de l'exécution
        lors de l'utilisation de l'item passé en paramètre
        */
        int vRes = this.aPlayer.useItem(vItemName);
        
        if (vRes == -1)
        {
            this.aGui.println("L'item " + vItemName + " ne peut pas être utilisé !");
        }
        else if (vRes == 1)
        {
            this.aGui.println("L'item " + vItemName + " n'est pas chargé !");
        }
        else
        {
            this.aGui.println("L'item " + vItemName + " a bien été utilisé.");
        }
        
        // on charge l'image de la nouvelle pièce dans laquelle se trouve le joueur
        Room vCurrentRoom = this.aPlayer.getCurrentRoom();
        if ( vCurrentRoom.getImageName() != null )
                this.aGui.showImage( vCurrentRoom.getImageName() );
    }
    /**
     * Commande permettant au joueur de charger le Beamer passé en
     * paramètre.
     * @param pCommande commande saisie par le joueur
     */
    public void charge(final Command pCommande)
    {
        if ( ! pCommande.hasSecondWord() ) {
            // on vérifie que le nom de l'item a bien été passé en paramètre
            this.aGui.println( "Utiliser quel item ?" );
            return;
        }
        
        String vItemName = pCommande.getSecondWord();
        
        if (!this.aPlayer.isInInventory(vItemName))
        {
            this.aGui.println("L'item " + vItemName + " n'est pas dans l'inventaire !");
            return;
        }
        
        this.aPlayer.charge(vItemName);
        this.aGui.println("L'item " + vItemName + " a bien été chargé.");
    }
    
    /**
     * Fonction qui prend un item dans la pièce où se trouve le joueur
     * et l'ajoute dans son inventaire
     * 
     * @param pCommande commande tapée dans le champs de saisie
     */
    public void take(final Command pCommande)
    {
        if ( ! pCommande.hasSecondWord() ) {
            // on vérifie que le nom de l'item a bien été passé en paramètre
            this.aGui.println( "Prendre quel item ?" );
            return;
        }
        String vItemName = pCommande.getSecondWord();
        Room vCurrentRoom = this.aPlayer.getCurrentRoom();
        Item vItem = vCurrentRoom.getItem(vItemName);
        
        if ( vItem == null)
        {
            this.aGui.println("Cet item n'est pas dans la pièce.");
            return;
        }
        if(!this.aPlayer.canAddToInventory(vItem))
        {
            this.aGui.println("L'inventaire est déjà plein !");
            return;
        }
        this.aPlayer.take(vCurrentRoom.removeItem(vItem));
        this.aGui.println(vItemName + " est dans l'inventaire.");
        
        // on vérifie si le joueur a récupéré le drapeau ou non
        if (this.gameIsFinished())
            this.endGame();
    }
    
    /**
     * Fonction qui prend l'item du joueur et le dépose dans la
     * pièce où il se trouve
     * 
     * @param pCommande commande tapée dans le champs de saisie
     */
    public void drop(final Command pCommande)
    {
        if (!pCommande.hasSecondWord()) {
            // on vérifie que le nom de l'item a bien été passé en paramètre
            this.aGui.println( "Laisser quel item ?" );
            return;
        }
        Room vCurrentRoom = this.aPlayer.getCurrentRoom();
        String vItemName = pCommande.getSecondWord();
        Item vItemToDrop = this.aPlayer.drop(vItemName);
        if (vItemToDrop == null)
        {
            this.aGui.println("Cet item n'est pas dans l'inventaire !");
            return;
        }
        vCurrentRoom.addItem(vItemToDrop);
        this.aGui.println("L'item " + vItemToDrop.getName() + " a été déposé.");
    }
    
    /**
     * Fonction qui effectue des tests sur les différentes commandes
     * du jeu
     * 
     * @param pCommande commande tapée dans le champs de saisie
     */
    private void test(final Command pCommande)
    {
        if ( ! pCommande.hasSecondWord() ) {
            // on vérifie que le nom du fichier a bien été passé en paramètre
            this.aGui.println( "Tester quel fichier ?" );
            return;
        }
        String vNomFichier = pCommande.getSecondWord();
        this.aTestMod = true;
        LectureFichierSimple.lecture(vNomFichier, this);
    }
    
    /**
     * Commande permettant de déterminer à l'avance la pièce où sera téléporté
     * le joueur lorsqu'il sortira d'une transporter room.
     * @param pCommande commande tapée dans le champs de saisie
     */
    private void alea(final Command pCommande)
    {
        String vNextRandomRoom = pCommande.getSecondWord();
        
        if (vNextRandomRoom != null &&
        !GameEngine.sRooms.containsKey(vNextRandomRoom))
        {
            this.aGui.println("La pièce désignée n'existe pas !");
            return;
        }
        
        TransporterRoom vInfirmerie = (TransporterRoom)GameEngine.sRooms.get("infirmerie");
        
        if (vNextRandomRoom == null)
        {
            vInfirmerie.setNextRandomRoom(null);
            this.aTestMod = false;
            this.aGui.println("On sort du mode test.");
        }
        else
        {
            vInfirmerie.setNextRandomRoom(GameEngine.sRooms.get(vNextRandomRoom));
            this.aGui.println("En sortant des prochaines transporter room,"
            + " on ira dans la pièce " + vNextRandomRoom);
        }
    }
    
    /** 
     * Déplace le joueur en fonction de la commande passé en paramètre.
     * Si le joueur saisie une sortie inexistante ou en invente une,
     * un message d'erreur adapté est renvoyé.
     * @param pCommand commande tapée dans le champs de saisie
     */
    private void goRoom( final Command pCommand ) 
    {
        if ( ! pCommand.hasSecondWord() ) {
            // if there is no second word, we don't know where to go...
            this.aGui.println( "Aller où ?" );
            return;
        }

        String vDirection = pCommand.getSecondWord();

        // Try to leave current room.
        Room vCurrentRoom = this.aPlayer.getCurrentRoom();
        Room vNextRoom = vCurrentRoom.getExit( vDirection );
        //System.out.println(vNextRoom.getLongDescription());

        if ( vNextRoom == null )
            this.aGui.println( "Il n'y a pas cette sortie !" );
        //on vérifie que la direction saisie existe bien
        else if (vNextRoom == Room.UNKNOWN_DIRECTION) 
        {
            this.aGui.println( Room.UNKNOWN_DIRECTION.getDescription() );
        }
        // Les vérifications ayant été effectuées, on peut déplacer le joueur
        else {
            this.aPlayer.addPreviousRoom(vCurrentRoom);
            this.aPlayer.setCurrentRoom(vNextRoom);
            vCurrentRoom = this.aPlayer.getCurrentRoom();
            
            // les PNJ qui le peuvent se déplacent également
            for (String vNPCName : this.aMovingCharacters.keySet())
            {
                this.aMovingCharacters.get(vNPCName).move();
            }
            
            // on met à jour l'interface graphique
            this.aGui.println( vCurrentRoom.getLongDescription() );
            if ( vCurrentRoom.getImageName() != null )
                this.aGui.showImage( vCurrentRoom.getImageName() );
        }
    }
    
    /**
     * Affiche un message d'aide comportant la liste des commandes
     * que peut utiliser le joueur.
     */
    private void printHelp() 
    {
        this.aGui.println( "Pas de panique, cadet !" );
        this.aGui.println( "Vous avez toutes les capacités nécessaires pour accomplir votre mission" + "\n" );
        this.aGui.println( "Vos actions possibles sont : " + this.aParser.getCommandString() );
    }
    
    /**
     * Permet à l'utilisateur d'afficher la description détaillée de la pièce
     * en tapant "look" dans le terminal, ou bien d'afficher la
     * description d'un item en tapant "look (Nom de l'item)"
     * @param pCommande commande tapée dans le champs de saisie
     */
    private void look(final Command pCommande)
    {
        Room vCurrentRoom = this.aPlayer.getCurrentRoom();
        // on vérifie si l'utilisateur a saisi 1 ou 2 mot dans la commande
        if(!pCommande.hasSecondWord())
        {
            this.aGui.println(vCurrentRoom.getLongDescription());
            return;
        }
        
        if (vCurrentRoom.getItemString().equals("No item here"))
        {
            this.aGui.println("Cet objet n'est pas présent dans la pièce");
            return;
        }
        
        String vParam = pCommande.getSecondWord();
        Item vItem = vCurrentRoom.getItem(vParam);
        
        if (vItem != null)
        {
            this.aGui.println(vItem.getItemLongDescription());
        }
        else
        {
            this.aGui.println("Cet objet n'est pas présent dans la pièce");
        }
        
    }//look
    
    /**
     * Permet au joueur de manger en affichant un message
     * ==> Pour information, cette méthode ne correspond pas à la méthode eat de l'exercice
     * 7.34, il faut se référer à la méthode use. 
     * @param pCommande commande tapée dans le champs de saisie
     */
    private void eat(final Command pCommande)
    {
        // on vérifie que l'utilisateur n'a pas saisie 2 mots dans la commande
        if(pCommande.hasSecondWord())
        {
            this.aGui.println("Je ne peux pas manger d'items spécifique pour le moment !.");
            return;
        }
        this.aGui.println("Vous avez mangé, vous n'avez plus faim maintenant.");
    }//look
    
    /**
     * Permet au joueur de revenir dans la pièce précédente.
     * @param pCommande commande tapée dans le champs de saisie
     */
    private void back(final Command pCommande)
    {
        Room vCurrentRoom = this.aPlayer.getCurrentRoom();
        // on vérifie que l'utilisateur n'a pas saisie 2 mots dans la commande
        if(pCommande.hasSecondWord())
        {
            this.aGui.println("Vous n'avez pas à spécifier une pièce de retour.");
            return;
        }
        else {
            this.aPlayer.setCurrentRoom(this.aPlayer.popPreviousRoom());
            vCurrentRoom = this.aPlayer.getCurrentRoom();
            this.aGui.println( vCurrentRoom.getLongDescription() );
            if ( vCurrentRoom.getImageName() != null )
                this.aGui.showImage( vCurrentRoom.getImageName() );
        }
        
    }
    
    /**
     * Fonction qui vérifie si la partie est terminée ou non.
     * Si elle est terminée, elle affiche un message au joueur
     * pour expliquer la raison ayant conduit à la fin de la 
     * partie.
     * @return true si la partie est finie, false sinon
     */
    private boolean gameIsFinished()
    {
        if (this.aPlayer.getMovingNumber() >= this.aMovingLimit)
        {
            this.aGui.println("Le temps imparti pour terminer l'épreuve est écoulé !" +
            "\nVous avez échoué à accomplir votre mission soldat, l'épreuve est donc terminée.");
            return true;
        }
        else if (this.aPlayer.getItemFromInventory("drapeau") != null)
        {
            this.aGui.println("Félicitation, " + this.aPlayer.getName()
            + ", vous avez rempli votre mission et\nréussi l'épreuve de la Citadelle avec succès !");
            return true;
        }
        return false;
    }
    
    /**
     * Fonction qui désactive l'interface après avoir affiché un message de fin
     * de partie
     */
    private void endGame()
    {
        this.aGui.println( "Ravi d'avoir combattu à vos côtés, soldat." );
        this.aGui.println( "Que la Force soit avec vous !" );
        this.aGui.enable( false );
    }
}