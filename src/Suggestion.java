/*This code was generated using the UMPLE 1.28.0.4160.f573280ad modeling language!*/



// line 2 "model.ump"
// line 132 "model.ump"
public class Suggestion
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Suggestion Attributes
    private Player suggestor;
    private boolean isAccusation;
    private WeaponCard weapon;
    private RoomCard room;
    private CharacterCard character;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    // secondary constructor for creating the murder circumstance
    public Suggestion(WeaponCard aWeapon, RoomCard aRoom, CharacterCard aCharacter)
    {
        suggestor = null;
        isAccusation = false;
        weapon = aWeapon;
        room = aRoom;
        character = aCharacter;
    }

    public Suggestion(Player aSuggestor, boolean aIsAccusation, WeaponCard aWeapon, RoomCard aRoom, CharacterCard aCharacter)
    {
        suggestor = aSuggestor;
        isAccusation = aIsAccusation;
        weapon = aWeapon;
        room = aRoom;
        character = aCharacter;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setIsAccusation(boolean aIsAccusation)
    {
        boolean wasSet = false;
        isAccusation = aIsAccusation;
        wasSet = true;
        return wasSet;
    }

    public boolean setWeapon(WeaponCard aWeapon)
    {
        boolean wasSet = false;
        weapon = aWeapon;
        wasSet = true;
        return wasSet;
    }

    public boolean setRoom(RoomCard aRoom)
    {
        boolean wasSet = false;
        room = aRoom;
        wasSet = true;
        return wasSet;
    }

    public boolean setCharacter(CharacterCard aCharacter)
    {
        boolean wasSet = false;
        character = aCharacter;
        wasSet = true;
        return wasSet;
    }

    public Player getSuggestor()
    {
        return suggestor;
    }

    public boolean getIsAccusation()
    {
        return isAccusation;
    }

    public WeaponCard getWeapon()
    {
        return weapon;
    }

    public RoomCard getRoom()
    {
        return room;
    }

    public CharacterCard getCharacter()
    {
        return character;
    }

    // line 10 "model.ump"
    public boolean equals(Suggestion s){
        return this.getWeapon().equals(s.getWeapon()) &&
                this.getRoom().equals(s.getRoom()) &&
                this.getCharacter().equals(s.getCharacter());
    }

    // line 16 "model.ump"
    public boolean contains(Card c){
        return weapon.equals(c) || room.equals(c) || character.equals(c);
    }

    public String toString() {
        return room.getName() + " " + weapon.getName() + " " + character.getName();
    }
}