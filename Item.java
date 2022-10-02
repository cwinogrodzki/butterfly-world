/**
 * Represents an item with a desciption and weight, which is stored in a room.
 *
 * @author Claire Winogrodzki
 * @version 04/17/2020
 */
public class Item
{
    private String description;
    private int nectarAmount;

    /**
     * Create an item with description and weight determined by parameters.
     * @param description The item's description, such as "book"
     * @param weight The weight of the item in pounds, such as 2
     */
    public Item(String description, int nectarAmount)
    {
        this.description = description;
        this.nectarAmount = nectarAmount;
    }

    public void setDescription(String newDescription)
    {
        description = newDescription;
    }
    
    public void setNectar(int newNectarAmount)
    {
        nectarAmount = newNectarAmount;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public int getNectarAmount()
    {
        return nectarAmount;
    }
    
    public String getItemString()
    {
        String description = getDescription();
        int nectarAmount = getNectarAmount();
        String itemString = "item description: " + description + 
        "   nectar amount: " + nectarAmount + "\n";
        return itemString;
    }
}

