/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4160.f573280ad modeling language!*/



// line 93 "model.ump"
// line 169 "model.ump"
public class Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Associations
  private RoomCard roomCard;
  private Tile tile;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(RoomCard aRoomCard, Tile aTile)
  {
    if (!setRoomCard(aRoomCard))
    {
      throw new RuntimeException("Unable to create Room due to aRoomCard");
    }
    boolean didAddTile = setTile(aTile);
    if (!didAddTile)
    {
      throw new RuntimeException("Unable to create room due to tile");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public RoomCard getRoomCard()
  {
    return roomCard;
  }
  /* Code from template association_GetOne */
  public Tile getTile()
  {
    return tile;
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
  /* Code from template association_SetOneToMany */
  public boolean setTile(Tile aTile)
  {
    boolean wasSet = false;
    if (aTile == null)
    {
      return wasSet;
    }

    Tile existingTile = tile;
    tile = aTile;
    if (existingTile != null && !existingTile.equals(aTile))
    {
      existingTile.removeRoom(this);
    }
    tile.addRoom(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    roomCard = null;
    Tile placeholderTile = tile;
    this.tile = null;
    if(placeholderTile != null)
    {
      placeholderTile.removeRoom(this);
    }
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 94 "model.ump"
  String getPrintable() ;

  
}