/*This code was generated using the UMPLE 1.28.0.4160.f573280ad modeling language!*/



// line 67 "model.ump"
// line 152 "model.ump"
public class RoomCard extends Card
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public RoomCard(String aName)
    {
        super(aName);
    }

    //------------------------
    // INTERFACE
    //------------------------

    // line 71 "model.ump"
    public String getPrintable(){
        return "\n     Room: " + this.getName();
    }

    public String getType(){ return "Room"; }

    // line 75 "model.ump"
    public boolean equals(Card c){
        if(c instanceof RoomCard && c.getName().equals(this.getName())) {
            return true;
        }
        return false;
    }

}