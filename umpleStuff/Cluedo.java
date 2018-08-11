/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4160.f573280ad modeling language!*/


import java.util.List;
import java.util.*;

// line 98 "model.ump"
// line 174 "model.ump"
public class Cluedo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cluedo Attributes
  private List<Card> deck;
  private Suggestion murder;
  private List<Player> players;
  private List<> roomGroup;

  //Cluedo Associations
  private Board board;
  private List<Room> rooms;
  private Suggestion suggestion;
  private List<RoomCard> roomCards;
  private List<CharacterCard> characterCards;
  private List<WeaponCard> weaponCards;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cluedo(Suggestion aMurder, List<> aRoomGroup, Board aBoard, Suggestion aSuggestion)
  {
    deck = new ArrayList<Card>();
    murder = aMurder;
    players = new ArrayList<Player>();
    roomGroup = aRoomGroup;
    if (!setBoard(aBoard))
    {
      throw new RuntimeException("Unable to create Cluedo due to aBoard");
    }
    rooms = new ArrayList<Room>();
    if (!setSuggestion(aSuggestion))
    {
      throw new RuntimeException("Unable to create Cluedo due to aSuggestion");
    }
    roomCards = new ArrayList<RoomCard>();
    characterCards = new ArrayList<CharacterCard>();
    weaponCards = new ArrayList<WeaponCard>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template attribute_SetMany */
  public boolean addDeck(Card aDeck)
  {
    boolean wasAdded = false;
    wasAdded = deck.add(aDeck);
    return wasAdded;
  }

  public boolean removeDeck(Card aDeck)
  {
    boolean wasRemoved = false;
    wasRemoved = deck.remove(aDeck);
    return wasRemoved;
  }

  public boolean setMurder(Suggestion aMurder)
  {
    boolean wasSet = false;
    murder = aMurder;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetMany */
  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    wasAdded = players.add(aPlayer);
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    wasRemoved = players.remove(aPlayer);
    return wasRemoved;
  }

  public boolean setRoomGroup(List<> aRoomGroup)
  {
    boolean wasSet = false;
    roomGroup = aRoomGroup;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_GetMany */
  public Card getDeck(int index)
  {
    Card aDeck = deck.get(index);
    return aDeck;
  }

  public Card[] getDeck()
  {
    Card[] newDeck = deck.toArray(new Card[deck.size()]);
    return newDeck;
  }

  public int numberOfDeck()
  {
    int number = deck.size();
    return number;
  }

  public boolean hasDeck()
  {
    boolean has = deck.size() > 0;
    return has;
  }

  public int indexOfDeck(Card aDeck)
  {
    int index = deck.indexOf(aDeck);
    return index;
  }

  public Suggestion getMurder()
  {
    return murder;
  }
  /* Code from template attribute_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public Player[] getPlayers()
  {
    Player[] newPlayers = players.toArray(new Player[players.size()]);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }

  public List<> getRoomGroup()
  {
    return roomGroup;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_GetMany */
  public Room getRoom(int index)
  {
    Room aRoom = rooms.get(index);
    return aRoom;
  }

  public List<Room> getRooms()
  {
    List<Room> newRooms = Collections.unmodifiableList(rooms);
    return newRooms;
  }

  public int numberOfRooms()
  {
    int number = rooms.size();
    return number;
  }

  public boolean hasRooms()
  {
    boolean has = rooms.size() > 0;
    return has;
  }

  public int indexOfRoom(Room aRoom)
  {
    int index = rooms.indexOf(aRoom);
    return index;
  }
  /* Code from template association_GetOne */
  public Suggestion getSuggestion()
  {
    return suggestion;
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
  /* Code from template association_SetUnidirectionalOne */
  public boolean setBoard(Board aNewBoard)
  {
    boolean wasSet = false;
    if (aNewBoard != null)
    {
      board = aNewBoard;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRooms()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addRoom(Room aRoom)
  {
    boolean wasAdded = false;
    if (rooms.contains(aRoom)) { return false; }
    rooms.add(aRoom);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRoom(Room aRoom)
  {
    boolean wasRemoved = false;
    if (rooms.contains(aRoom))
    {
      rooms.remove(aRoom);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRoomAt(Room aRoom, int index)
  {  
    boolean wasAdded = false;
    if(addRoom(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRoomAt(Room aRoom, int index)
  {
    boolean wasAdded = false;
    if(rooms.contains(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRoomAt(aRoom, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setSuggestion(Suggestion aNewSuggestion)
  {
    boolean wasSet = false;
    if (aNewSuggestion != null)
    {
      suggestion = aNewSuggestion;
      wasSet = true;
    }
    return wasSet;
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
    board = null;
    rooms.clear();
    suggestion = null;
    roomCards.clear();
    characterCards.clear();
    weaponCards.clear();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "murder" + "=" + (getMurder() != null ? !getMurder().equals(this)  ? getMurder().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "roomGroup" + "=" + (getRoomGroup() != null ? !getRoomGroup().equals(this)  ? getRoomGroup().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "suggestion = "+(getSuggestion()!=null?Integer.toHexString(System.identityHashCode(getSuggestion())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 105 "model.ump"
  void redraw() ;
// line 106 "model.ump"
  void setUpBoard() ;
// line 107 "model.ump"
  void setUpRooms() ;
// line 108 "model.ump"
  void parseLayout() ;
// line 109 "model.ump"
  void run() ;

  
}