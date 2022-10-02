import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    private String description;
    private String imageName;
    private String audioName;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
    }
    
    public void addItem(Item item)
    {
        items.add(item);
    }
    
    public Item getItem(int index)
    {
        return items.get(index);
    }
    
    public void removeItem(int index)
    {
        items.remove(index);
    }
    //8.8
    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param direction The direction of this exit
     * @param neighbor The room in this direction
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    //8.6
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }
    
    public String getAllItemStrings()
    {
        String itemString = "";
        for(Item item : items){
            itemString = itemString + item.getItemString();
        }
        return itemString;
    }
    //8.7, 8.11
    /**
     * Return a description of the room's exits, for example, 
     * "Exits: north west".
     * @return A decription of the available exits.
     */
    public String getExitString()
    {
        String returnString = "Exits: ";
        Set<String> keys = exits.keySet();
        for(String exit : keys){
            returnString += exit + " ";
        }
        return returnString;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    
    //8.11
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + "\n"
        + getAllItemStrings();
    }
    
    /*************************************************************
     * added by William H. Hooper, 2006-11-28
    *************************************************************/
    /**
     * @return a String, which hopefully contains the file name
     * of an Image file.
     */
    public String getImage()
    {
        return imageName;
    }
    
    /**
     * associate an image with this room
     * @param filename a String containing a file.
     * The file <b>must</b> reside in the media directory, 
     * and must be in a format viewable in the Java AWT.
     * Readable formats include: 
     * PNG, JPG (RGB color scheme only), GIF
     */
    public void setImage(String filename)
    {
        imageName = "media/" + filename;
    }
    
    /**
     * @return a String, which hopefully contains the file name
     * of an audio file.
     */
    public String getAudio()
    {
        return audioName;
    }
    
    /**
     * associate an audio clip with this room
     * @param filename a String containing a file.
     * The file <b>must</b> reside in the media directory, 
     * and must be in a format palyable in the Java AWT.
     * Readable formats include: 
     * WAV, AU.
     */
    public void setAudio(String filename)
    {
        audioName = "media/" + filename;
    }
}
