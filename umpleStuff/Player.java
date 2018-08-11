/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4160.f573280ad modeling language!*/


import java.util.*;

// line 119 "model.ump"
// line 182 "model.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private String name;
  private Tile position;

  //Player Associations
  private Tile tile;
  private List<RoomCard> roomCards;
  private List<CharacterCard> characterCards;
  private List<WeaponCard> weaponCards;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String aName, Tile aPosition, Tile aTile)
  {
    name = aName;
    position = aPosition;
    if (aTile == null || aTile.getPlayer() != null)
    {
      throw new RuntimeException("Unable to create Player due to aTile");
    }
    tile = aTile;
    roomCards = new ArrayList<RoomCard>();
    characterCards = new ArrayList<CharacterCard>();
    weaponCards = new ArrayList<WeaponCard>();
  }

  public Player(String aName, Tile aPosition)
  {
    name = aName;
    position = aPosition;
    tile = new Tile(this);
    roomCards = new ArrayList<RoomCard>();
    characterCards = new ArrayList<CharacterCard>();
    weaponCards = new ArrayList<WeaponCard>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPosition(Tile aPosition)
  {
    boolean wasSet = false;
    position = aPosition;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public Tile getPosition()
  {
    return position;
  }
  /* Code from template association_GetOne */
  public Tile getTile()
  {
    return tile;
  }
  /* Code from template association_GetMany */
  public RoomCard getRoomCard(int index)
  {
    RoomCard aRoomCard = roomCards.get(index);
    return aRoomCard;
  }

  public List<RoomCard> getRoomCards()
  {
    List<RoomCard> newRoomCards = Collections.unmodifiableList(roomCards);
    return newRoomCards;
  }

  public int numberOfRoomCards()
  {
    int number = roomCards.size();
    return number;
  }

  public boolean hasRoomCards()
  {
    boolean has = roomCards.size() > 0;
    return has;
  }

  public int indexOfRoomCard(RoomCard aRoomCard)
  {
    int index = roomCards.indexOf(aRoomCard);
    return index;
  }
  /* Code from template association_GetMany */
  public CharacterCard getCharacterCard(int index)
  {
    CharacterCard aCharacterCard = characterCards.get(index);
    return aCharacterCard;
  }

  public List<CharacterCard> getCharacterCards()
  {
    List<CharacterCard> newCharacterCards = Collections.unmodifiableList(characterCards);
    return newCharacterCards;
  }

  public int numberOfCharacterCards()
  {
    int number = characterCards.size();
    return number;
  }

  public boolean hasCharacterCards()
  {
    boolean has = characterCards.size() > 0;
    return has;
  }

  public int indexOfCharacterCard(CharacterCard aCharacterCard)
  {
    int index = characterCards.indexOf(aCharacterCard);
    return index;
  }
  /* Code from template association_GetMany */
  public WeaponCard getWeaponCard(int index)
  {
    WeaponCard aWeaponCard = weaponCards.get(index);
    return aWeaponCard;
  }

  public List<WeaponCard> getWeaponCards()
  {
    List<WeaponCard> newWeaponCards = Collections.unmodifiableList(weaponCards);
    return newWeaponCards;
  }

  public int numberOfWeaponCards()
  {
    int number = weaponCards.size();
    return number;
  }

  public boolean hasWeaponCards()
  {
    boolean has = weaponCards.size() > 0;
    return has;
  }

  public int indexOfWeaponCard(WeaponCard aWeaponCard)
  {
    int index = weaponCards.indexOf(aWeaponCard);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRoomCards()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addRoomCard(RoomCard aRoomCard)
  {
    boolean wasAdded = false;
    if (roomCards.contains(aRoomCard)) { return false; }
    roomCards.add(aRoomCard);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRoomCard(RoomCard aRoomCard)
  {
    boolean wasRemoved = false;
    if (roomCards.contains(aRoomCard))
    {
      roomCards.remove(aRoomCard);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRoomCardAt(RoomCard aRoomCard, int index)
  {  
    boolean wasAdded = false;
    if(addRoomCard(aRoomCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRoomCards()) { index = numberOfRoomCards() - 1; }
      roomCards.remove(aRoomCard);
      roomCards.add(index, aRoomCard);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRoomCardAt(RoomCard aRoomCard, int index)
  {
    boolean wasAdded = false;
    if(roomCards.contains(aRoomCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRoomCards()) { index = numberOfRoomCards() - 1; }
      roomCards.remove(aRoomCard);
      roomCards.add(index, aRoomCard);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRoomCardAt(aRoomCard, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCharacterCards()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addCharacterCard(CharacterCard aCharacterCard)
  {
    boolean wasAdded = false;
    if (characterCards.contains(aCharacterCard)) { return false; }
    characterCards.add(aCharacterCard);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCharacterCard(CharacterCard aCharacterCard)
  {
    boolean wasRemoved = false;
    if (characterCards.contains(aCharacterCard))
    {
      characterCards.remove(aCharacterCard);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCharacterCardAt(CharacterCard aCharacterCard, int index)
  {  
    boolean wasAdded = false;
    if(addCharacterCard(aCharacterCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCharacterCards()) { index = numberOfCharacterCards() - 1; }
      characterCards.remove(aCharacterCard);
      characterCards.add(index, aCharacterCard);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCharacterCardAt(CharacterCard aCharacterCard, int index)
  {
    boolean wasAdded = false;
    if(characterCards.contains(aCharacterCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCharacterCards()) { index = numberOfCharacterCards() - 1; }
      characterCards.remove(aCharacterCard);
      characterCards.add(index, aCharacterCard);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCharacterCardAt(aCharacterCard, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWeaponCards()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addWeaponCard(WeaponCard aWeaponCard)
  {
    boolean wasAdded = false;
    if (weaponCards.contains(aWeaponCard)) { return false; }
    weaponCards.add(aWeaponCard);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWeaponCard(WeaponCard aWeaponCard)
  {
    boolean wasRemoved = false;
    if (weaponCards.contains(aWeaponCard))
    {
      weaponCards.remove(aWeaponCard);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWeaponCardAt(WeaponCard aWeaponCard, int index)
  {  
    boolean wasAdded = false;
    if(addWeaponCard(aWeaponCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWeaponCards()) { index = numberOfWeaponCards() - 1; }
      weaponCards.remove(aWeaponCard);
      weaponCards.add(index, aWeaponCard);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWeaponCardAt(WeaponCard aWeaponCard, int index)
  {
    boolean wasAdded = false;
    if(weaponCards.contains(aWeaponCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWeaponCards()) { index = numberOfWeaponCards() - 1; }
      weaponCards.remove(aWeaponCard);
      weaponCards.add(index, aWeaponCard);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWeaponCardAt(aWeaponCard, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Tile existingTile = tile;
    tile = null;
    if (existingTile != null)
    {
      existingTile.delete();
    }
    roomCards.clear();
    characterCards.clear();
    weaponCards.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "position" + "=" + (getPosition() != null ? !getPosition().equals(this)  ? getPosition().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "tile = "+(getTile()!=null?Integer.toHexString(System.identityHashCode(getTile())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 123 "model.ump"
  String getPrintable() ;
// line 124 "model.ump"
  void move() ;

  
}