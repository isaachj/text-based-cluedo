/*This code was generated using the UMPLE 1.28.0.4160.f573280ad modeling language!*/



// line 35 "model.ump"
// line 142 "model.ump"
public class WeaponCard extends Card
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public WeaponCard(String aName)
    {
        super(aName);
    }

    //------------------------
    // INTERFACE
    //------------------------

    // line 39 "model.ump"
    public String getPrintable(){
        return "\n   Weapon: " + this.getName();
    }

    public String getType(){ return "Weapon"; }

    // line 43 "model.ump"
    public boolean equals(Card c){
        if(c instanceof WeaponCard && c.getName().equals(this.getName())) {
            return true;
        }
        return false;
    }

}