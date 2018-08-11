/*PLEASE DO NOT EDIT THIS CODE*/
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

  //Suggestion Associations
  private WeaponCard weaponCard;
  private CharacterCard characterCard;
  private RoomCard roomCard;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Suggestion(Player aSuggestor, boolean aIsAccusation, WeaponCard aWeapon, RoomCard aRoom, CharacterCard aCharacter, WeaponCard aWeaponCard, CharacterCard aCharacterCard, RoomCard aRoomCard)
  {
    suggestor = aSuggestor;
    isAccusation = aIsAccusation;
    weapon = aWeapon;
    room = aRoom;
    character = aCharacter;
    if (!setWeaponCard(aWeaponCard))
    {
      throw new RuntimeException("Unable to create Suggestion due to aWeaponCard");
    }
    if (!setCharacterCard(aCharacterCard))
    {
      throw new RuntimeException("Unable to create Suggestion due to aCharacterCard");
    }
    if (!setRoomCard(aRoomCard))
    {
      throw new RuntimeException("Unable to create Suggestion due to aRoomCard");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSuggestor(Player aSuggestor)
  {
    boolean wasSet = false;
    suggestor = aSuggestor;
    wasSet = true;
    return wasSet;
  }

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
  /* Code from template association_GetOne */
  public WeaponCard getWeaponCard()
  {
    return weaponCard;
  }
  /* Code from template association_GetOne */
  public CharacterCard getCharacterCard()
  {
    return characterCard;
  }
  /* Code from template association_GetOne */
  public RoomCard getRoomCard()
  {
    return roomCard;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setWeaponCard(WeaponCard aNewWeaponCard)
  {
    boolean wasSet = false;
    if (aNewWeaponCard != null)
    {
      weaponCard = aNewWeaponCard;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCharacterCard(CharacterCard aNewCharacterCard)
  {
    boolean wasSet = false;
    if (aNewCharacterCard != null)
    {
      characterCard = aNewCharacterCard;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setRoomCard(RoomCard aNewRoomCard)
  {
    boolean wasSet = false;
    if (aNewRoomCard != null)
    {
      roomCard = aNewRoomCard;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    weaponCard = null;
    characterCard = null;
    roomCard = null;
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


  public String toString()
  {
    return super.toString() + "["+
            "isAccusation" + ":" + getIsAccusation()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "suggestor" + "=" + (getSuggestor() != null ? !getSuggestor().equals(this)  ? getSuggestor().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "weapon" + "=" + (getWeapon() != null ? !getWeapon().equals(this)  ? getWeapon().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "room" + "=" + (getRoom() != null ? !getRoom().equals(this)  ? getRoom().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "character" + "=" + (getCharacter() != null ? !getCharacter().equals(this)  ? getCharacter().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "weaponCard = "+(getWeaponCard()!=null?Integer.toHexString(System.identityHashCode(getWeaponCard())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "characterCard = "+(getCharacterCard()!=null?Integer.toHexString(System.identityHashCode(getCharacterCard())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "roomCard = "+(getRoomCard()!=null?Integer.toHexString(System.identityHashCode(getRoomCard())):"null");
  }
}