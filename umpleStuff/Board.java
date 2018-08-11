/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4160.f573280ad modeling language!*/


import java.util.*;

// line 82 "model.ump"
// line 157 "model.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Attributes
  private List<Tile> board;

  //Board Associations
  private List<Tile> tiles;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(Tile... allTiles)
  {
    board = new ArrayList<Tile>();
    tiles = new ArrayList<Tile>();
    boolean didAddTiles = setTiles(allTiles);
    if (!didAddTiles)
    {
      throw new RuntimeException("Unable to create Board, must have 676 tiles");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template attribute_SetMany */
  public boolean addBoard(Tile aBoard)
  {
    boolean wasAdded = false;
    wasAdded = board.add(aBoard);
    return wasAdded;
  }

  public boolean removeBoard(Tile aBoard)
  {
    boolean wasRemoved = false;
    wasRemoved = board.remove(aBoard);
    return wasRemoved;
  }
  /* Code from template attribute_GetMany */
  public Tile getBoard(int index)
  {
    Tile aBoard = board.get(index);
    return aBoard;
  }

  public Tile[] getBoard()
  {
    Tile[] newBoard = board.toArray(new Tile[board.size()]);
    return newBoard;
  }

  public int numberOfBoard()
  {
    int number = board.size();
    return number;
  }

  public boolean hasBoard()
  {
    boolean has = board.size() > 0;
    return has;
  }

  public int indexOfBoard(Tile aBoard)
  {
    int index = board.indexOf(aBoard);
    return index;
  }
  /* Code from template association_GetMany */
  public Tile getTile(int index)
  {
    Tile aTile = tiles.get(index);
    return aTile;
  }

  public List<Tile> getTiles()
  {
    List<Tile> newTiles = Collections.unmodifiableList(tiles);
    return newTiles;
  }

  public int numberOfTiles()
  {
    int number = tiles.size();
    return number;
  }

  public boolean hasTiles()
  {
    boolean has = tiles.size() > 0;
    return has;
  }

  public int indexOfTile(Tile aTile)
  {
    int index = tiles.indexOf(aTile);
    return index;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfTiles()
  {
    return 676;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTiles()
  {
    return 676;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfTiles()
  {
    return 676;
  }
  /* Code from template association_SetUnidirectionalN */
  public boolean setTiles(Tile... newTiles)
  {
    boolean wasSet = false;
    ArrayList<Tile> verifiedTiles = new ArrayList<Tile>();
    for (Tile aTile : newTiles)
    {
      if (verifiedTiles.contains(aTile))
      {
        continue;
      }
      verifiedTiles.add(aTile);
    }

    if (verifiedTiles.size() != newTiles.length || verifiedTiles.size() != requiredNumberOfTiles())
    {
      return wasSet;
    }

    tiles.clear();
    tiles.addAll(verifiedTiles);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    tiles.clear();
  }


  public String toString()
  {
    return super.toString() + "["+ "]";
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 83 "model.ump"
  String getPrintable() ;

  
}