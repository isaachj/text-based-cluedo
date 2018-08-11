/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4160.f573280ad modeling language!*/



// line 50 "model.ump"
// line 147 "model.ump"
public class CharacterCard extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CharacterCard Attributes
  private int startX;
  private int startY;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CharacterCard(String aName, int aStartX, int aStartY)
  {
    super(aName);
    startX = aStartX;
    startY = aStartY;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartX(int aStartX)
  {
    boolean wasSet = false;
    startX = aStartX;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartY(int aStartY)
  {
    boolean wasSet = false;
    startY = aStartY;
    wasSet = true;
    return wasSet;
  }

  public int getStartX()
  {
    return startX;
  }

  public int getStartY()
  {
    return startY;
  }

  public void delete()
  {
    super.delete();
  }

  // line 56 "model.ump"
  public String getPrintable(){
    return "\nCharacter: " + this.getName();
  }

  // line 60 "model.ump"
  public boolean equals(Card c){
    if(c instanceof CharacterCard && c.getName.equals(this.getName)) {
            return true;
        }
        return false;
  }


  public String toString()
  {
    return super.toString() + "["+
            "startX" + ":" + getStartX()+ "," +
            "startY" + ":" + getStartY()+ "]";
  }
}