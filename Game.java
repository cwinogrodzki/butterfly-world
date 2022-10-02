import java.util.Random;
import java.util.Stack;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private String messages;
    private boolean wantToQuit;
    private Stack<Room> backRooms;
    private Room previousRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    /*public Game() 
    {
        createRooms();
        parser = new Parser();
    }*/
    public Game()
    {
        this(new Random());
        backRooms = new Stack<Room>();
    }
    
    public Game(Random gen)
    {
        createRooms();
        parser = new Parser();
        messages = "";
        wantToQuit = false;
        printWelcome();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room entrance, hills, glen, garden, hollow;
        Item lilac, honeysuckle, cherry, milkweed, rose, buttercup; 
      
        // create the rooms
        entrance = new Room("at the meadow entrance");
        hills = new Room("in Swallowtail Hills");
        glen = new Room("in the Shady Glen");
        garden = new Room("in the Rose Garden");
        hollow = new Room("in Honeysuckle Hollow");
        
        // assign images
        entrance.setImage("entrance.jpg");
        hills.setImage("hills.jpg");
        glen.setImage("glen.jpg");
        garden.setImage("garden.jpg");
        hollow.setImage("hollow.jpg");
        
        // assign sounds
        //office.setAudio("cricket.mp3");
        
        //create items
        lilac = new Item("a colorful lilac bush", 20);
        honeysuckle = new Item("a tangled honeysuckle vine", 40);
        cherry = new Item("a sprawling wild cherry tree", 30);
        milkweed = new Item("a blooming milkweed", 50);
        rose = new Item("a fragrant rose bush", 0);
        buttercup = new Item("a patch of buttercups", 20);
        
        //assign items
        entrance.addItem(buttercup);
        hills.addItem(cherry);
        glen.addItem(lilac);
        garden.addItem(rose);
        garden.addItem(milkweed);
        hollow.addItem(honeysuckle);
        
        // initialise room exits
        //8.8
        entrance.setExit("north", garden);
        entrance.setExit("east", hills);
        hills.setExit("north", glen);
        hills.setExit("west", entrance);
        glen.setExit("south", hills);
        glen.setExit("west", garden);
        garden.setExit("east", glen);
        garden.setExit("south", entrance);
        garden.setExit("west", hollow);
        hollow.setExit("east", garden);

        currentRoom = entrance;  // start game outside
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        println();
        println("Welcome to Butterfly World");
        println("Butterfly World is a new, incredibly peaceful adventure game.");
        println("Type 'help' if you need help.");
        println();
        println(currentRoom.getLongDescription());
    }
    
    //8.14
    /**
     * Prints the description and exits of the current room.
     */
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }
    
    //8.15
    /**
     * Lands on a flower and collects nectar points if available.
     */
    private void land()
    {
        System.out.println("You have landed on " + currentRoom.getItem(0).getDescription() +
        ". You earn " + currentRoom.getItem(0).getNectarAmount() + " points for drinking nectar.");
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private void processCommand(Command command) 
    {
        if(command.isUnknown()) {
            println("I don't know what you mean...");
            return;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            quit(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("land")) {
            land();
        }
        else if (commandWord.equals("back")) {
            goBack(command);
        }
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        println("You are a butterfly looking for nectar. You wander");
        println("around a meadow.");
        println();
        println("Your command words are: ");
        //8.18
        println(parser.showCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        //8.6
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            println("There is no door!");
        }
        else {
            previousRoom = currentRoom;
            backRooms.push(previousRoom);
            currentRoom = nextRoom; 
            println(currentRoom.getLongDescription());
        }
    }

    private void goBack(Command command)
    {
        if(previousRoom == null){ //8.24
            println("Can't go back");
            return;
        }
        if(backRooms.empty() == true){ //8.24
            println("Can't go back");
            return;
        }
        else{
            currentRoom = backRooms.pop();
            println(currentRoom.getLongDescription());
        }
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private void quit(Command command) 
    {
        if(command.hasSecondWord()) {
            println("Quit what?");
            return;
        }
        
        wantToQuit = true;  // signal that we want to quit
    }
    
    /****************************************************************
     * If you want to launch an Applet
     ****************************************************************/
    
    /**
     * @return an Image from the current room
     * @see Image
     */
    public String getImage()
    {
        return currentRoom.getImage();
    }
    
    /**
     * @return an audio clip from the current room
     * @see AudioClip
     */
    public String getAudio()
    {
        return currentRoom.getAudio();
    }
    
    /****************************************************************
     * Variables & Methods added 2018-04-16 by William H. Hooper
     ****************************************************************/
  
    
    /**
     * Initialize the new variables and begin the game.
     */
    private void start()
    {
        messages = "";
        wantToQuit = false;
        printWelcome();
    }
    
    /**
     * process commands or queries to the game
     * @param input user-supplied input
     */
    public void processInput(String input)
    {
        if(finished()) {
            println("This game is over.  Please go away.");
            return;
        }
        
        Command command = parser.getCommand(input);
        processCommand(command);
    }
    
    /**
     * clear and return the output messages
     * @return current contents of the messages.
     */
    public String readMessages()
    {
        if(messages == null) {
            start();
        }
        String oldMessages = messages;
        messages = "";
        return oldMessages;
    }
    
    /**
     * @return true when the game is over.
     */
    public boolean finished()
    {
        return wantToQuit;
    }

    /**
     * add a message to the output list.
     * @param message the string to be displayed
     */
    private void print(String message)
    {
        messages += message;
    }
    
    /**
     * add a message to the output list, 
     * followed by newline.
     * @param message the string to be displayed
     * @see readMessages
     */
    private void println(String message)
    {
        print(message + "\n");
    }
    
    /**
     * add a blank line to the output list.
     */
    private void println()
    {
        println("");
    }
}
