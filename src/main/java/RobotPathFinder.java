import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class RobotPathFinder {
	
	Tile[][] AllTiles;
	
	HashMap<Integer,LinkedList<Tile>> TilesByDistance = new HashMap<Integer,LinkedList<Tile>>();
	Integer smallestDistance;
	
	// may clean up wrappers later
	private class Tile{
		public Integer[] location;
		int[] distance;
		Integer[] previousTile;
		public boolean xLarger; // keeps track if x or y is farther from the end point
		public boolean checked = false; // used for checked tiles and invalid tiles., might not be necessary
		public Tile(Integer[] location, int[] distance, Integer[] previousTile) {
			this.location = location;
			this.distance = distance;
			this.previousTile = previousTile;
			if(distance[0]>distance[1])
			{
				xLarger = true;
			}
			else
			{
				xLarger = false;
			}
		}
		public int TotalDistance()
		{
			return distance[0] + distance[1];
		}	
	} // end of inner class tile

	
	RobotPathFinder(){
		
	}
	
	public StringBuffer findPath(int[] gridSize, LinkedList<Integer[]> invalidTiles) throws Exception {
		TilesByDistance.clear();
		
		AllTiles = new Tile[gridSize[0]][gridSize[1]];
		int currentDistance = 0;
		for(Integer[] i : invalidTiles)
		{
			Tile t = new Tile(i,gridSize,null);
			t.checked = true;
			AllTiles[i[0]][i[1]] = t;
		}
		
		// starting tile, skips if checked
		if(AllTiles[0][0] == null)
		{
			int[] distance = new int[]{AllTiles.length-1,AllTiles[0].length-1};
			Integer[] location = new Integer[]{0,0};
			AllTiles[0][0] = new Tile(location,distance,null);
			LinkedList<Tile> lt= new LinkedList<Tile>();
			lt.push(AllTiles[0][0]);
			TilesByDistance.put(AllTiles[0][0].TotalDistance(),lt);
			smallestDistance = AllTiles[0][0].TotalDistance();
		}
		
		
		while(smallestDistance > 0 && smallestDistance <= AllTiles[0][0].TotalDistance())
		{
			currentDistance = smallestDistance;
			while(currentDistance == smallestDistance)
			{
				if(TilesByDistance.get(smallestDistance).isEmpty() == false)
				{
					Tile currentTile = TilesByDistance.get(smallestDistance).pop();
					generateTile(currentTile);
					AllTiles[currentTile.location[0]][currentTile.location[1]].checked = true;
				}
				else
				{
					smallestDistance++;
				}
			}
		}
		
		if(smallestDistance == 0)
		{	
			return generatePath();
		}
		else
		{
			return new StringBuffer("no valid route");
		}
	}
	
	// recursivly builds string
	// stack overflows on big-enough grid
	// No Longer Used
	public String getPreviousStep(Tile currentTile)
	{
		if(currentTile.location[0] != 0 || currentTile.location[1] != 0)
		{
			return getPreviousStep(AllTiles[currentTile.previousTile[0]][currentTile.previousTile[1]]) + " -> " +  Arrays.deepToString(currentTile.location);
		}
		else{
			return "start:[0, 0]" ;
		}
	}
	
	// limited by a string size cap
	public StringBuffer generatePath() {
		
		Tile currentTile = AllTiles[AllTiles.length-1][AllTiles[0].length-1];
		StringBuffer output = new StringBuffer("");
		while(currentTile.location[0] != 0 || currentTile.location[1] != 0)
		{
			output.insert(0," -> "+ Arrays.deepToString(currentTile.location));
			//output = " -> "+ Arrays.deepToString(currentTile.location) + output;
			currentTile = AllTiles[currentTile.previousTile[0]][currentTile.previousTile[1]];
		}
		output.insert(0,"Start(0, 0)");
		return output;
	}
	
	
	
	public void generateTile(Tile Origin)
	{
		Tile tX;
		Tile tY;
		// go farther direction toward end point
		if(Origin.xLarger)
		{
			// checks if destination is valid
			// first if for both should be unnecessary, will remove during testing, logic in is still necessary
			if(AllTiles.length - 2 - Origin.location[0] >= 0) {
				
				// checks if empty
				if(AllTiles[Origin.location[0]+1][Origin.location[1]] == null)
				{
					tX = new Tile(new Integer[]{Origin.location[0]+1,Origin.location[1]},new int[]{AllTiles.length - 2 - Origin.location[0], AllTiles[0].length - 1 - Origin.location[1]},Origin.location);	
					AllTiles[Origin.location[0]+1][Origin.location[1]] = tX;
					if(TilesByDistance.containsKey(tX.TotalDistance())) {
						// is this necessary? 
						LinkedList<Tile> temp = TilesByDistance.get(tX.TotalDistance());// is this by value or reference
						temp.add(tX);
						TilesByDistance.put(tX.TotalDistance(), temp);
						//	TilesByDistance.computeIfPresent(tX.TotalDistance(), (k, v) -> v.add(tX));  //One line version not working
						
					}
					else {
						LinkedList<Tile> temp = new LinkedList<Tile>();// is this by value or reference
						temp.add(tX);
						TilesByDistance.put(tX.TotalDistance(), temp);
					}
					//dont think this if is strictly necessary, can reduce code by removing it from the optional if
					smallestDistance = tX.TotalDistance();
					
				}
				
			}
			if(AllTiles[0].length - 2 - Origin.location[1] >= 0) {
				if(AllTiles[Origin.location[0]][Origin.location[1]+1] == null)
				{
					tY = new Tile(new Integer[]{Origin.location[0],Origin.location[1]+1},new int[]{AllTiles.length - 1 - Origin.location[0], AllTiles[0].length - 2 - Origin.location[1]},Origin.location);
					AllTiles[Origin.location[0]][Origin.location[1]+1] = tY;
					if(TilesByDistance.containsKey(tY.TotalDistance())) {
						// is this necessary? 
						LinkedList<Tile> temp = TilesByDistance.get(tY.TotalDistance());// is this by value or reference
						temp.add(tY);
						TilesByDistance.put(tY.TotalDistance(), temp);
						//	TilesByDistance.computeIfPresent(tX.TotalDistance(), (k, v) -> v.add(tX));  //One line version not working
					}
					else {
						LinkedList<Tile> temp = new LinkedList<Tile>();// is this by value or reference
						temp.add(tY);
						TilesByDistance.put(tY.TotalDistance(), temp);
					}
					smallestDistance = tY.TotalDistance();
				}
			}
			
		}
		else
		{
			if(AllTiles[0].length - 2 - Origin.location[1] >= 0) {
				if(AllTiles[Origin.location[0]][Origin.location[1]+1] == null)
				{
					tY = new Tile(new Integer[]{Origin.location[0],Origin.location[1]+1},new int[]{AllTiles.length - 1 - Origin.location[0], AllTiles[0].length - 2 - Origin.location[1]},Origin.location);
					AllTiles[Origin.location[0]][Origin.location[1]+1] = tY;
					if(TilesByDistance.containsKey(tY.TotalDistance())) {
						// is this necessary? 
						LinkedList<Tile> temp = TilesByDistance.get(tY.TotalDistance());// is this by value or reference
						temp.add(tY);
						TilesByDistance.put(tY.TotalDistance(), temp);
						//	TilesByDistance.computeIfPresent(tX.TotalDistance(), (k, v) -> v.add(tX));  //One line version not working
					}
					else {
						LinkedList<Tile> temp = new LinkedList<Tile>();// is this by value or reference
						temp.add(tY);
						TilesByDistance.put(tY.TotalDistance(), temp);
					}
					smallestDistance = tY.TotalDistance();
				}
			}
			if(AllTiles.length - 2 - Origin.location[0] >= 0) {
				if(AllTiles[Origin.location[0]+1][Origin.location[1]] == null)
				{
					tX = new Tile(new Integer[]{Origin.location[0]+1,Origin.location[1]},new int[]{AllTiles.length - 2 - Origin.location[0], AllTiles[0].length - 1 - Origin.location[1]},Origin.location);	
					AllTiles[Origin.location[0]+1][Origin.location[1]] = tX;
					if(TilesByDistance.containsKey(tX.TotalDistance())) {
						// is this necessary? 
						LinkedList<Tile> temp = TilesByDistance.get(tX.TotalDistance());// is this by value or reference
						temp.add(tX);
						TilesByDistance.put(tX.TotalDistance(), temp);
						//	TilesByDistance.computeIfPresent(tX.TotalDistance(), (k, v) -> v.add(tX));  //One line version not working
					}
					else {
						LinkedList<Tile> temp = new LinkedList<Tile>();// is this by value or reference
						temp.add(tX);
						TilesByDistance.put(tX.TotalDistance(), temp);
					}
					smallestDistance = tX.TotalDistance();
				}
				
			}
		}
	}
	
	

}
