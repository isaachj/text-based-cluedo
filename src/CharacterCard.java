/*This code was generated using the UMPLE 1.28.0.4160.f573280ad modeling language!*/



// line 50 "model.ump"
// line 147 "model.ump"
public class CharacterCard extends Card
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //CharacterCard Attributes
    private int startRow;
    private int startCol;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public CharacterCard(String aName, int aStartRow, int aStartCol)
    {
        super(aName);
        startRow = aStartRow;
        startCol = aStartCol;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setStartRow(int aStartRow)
    {
        boolean wasSet = false;
        startRow = aStartRow;
        wasSet = true;
        return wasSet;
    }

    public boolean setStartCol(int aStartCol)
    {
        boolean wasSet = false;
        startCol = aStartCol;
        wasSet = true;
        return wasSet;
    }

    public int getStartRow()
    {
        return startRow;
    }

    public int getStartCol()
    {
        return startCol;
    }

    // line 56 "model.ump"
    public String getPrintable(){
        return "\nCharacter: " + this.getName();
    }

    public String getType(){ return "Character"; }

    // line 60 "model.ump"
    public boolean equals(Card c){
        if(c instanceof CharacterCard && c.getName().equals(this.getName())) {
            return true;
        }
        return false;
    }


    public String toString()
    {
        return super.toString() + "["+
                "startRow" + ":" + getStartRow()+ "," +
                "startCol" + ":" + getStartCol()+ "]";
    }
}