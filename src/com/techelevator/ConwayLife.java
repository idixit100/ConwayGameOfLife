package com.techelevator;
import java.util.*;

public class ConwayLife {
	final static public int[][] MOORE_HOOD={{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a[][]= {{0,0,0}, 
				   {0,1,0},
			       {0,0,0},

		};
		int[][] b={{1,1,1,0,0,0, 1,0},
			       {1,0,0,0,0,0,0,1},
			       {0,1,0,0,0,1,1,1}
			       };
		
		int[][] c={{}};
		
		for(int i=0; i<10; i++){
			printGrid(getGeneration(b, i));
		}
		int[][] k=b;
//		for(int i=0; i<4; i++){
//			printGrid(k);
//			k=convert2DListToArray(rotate(convert2DArrayToList(k)));
//			
//		}
//		printGrid(k);
//		k=addAnOuterLayer(k);
//		printGrid(k);
//		generateNextGen(k);
//		printGrid(k);
//		updateNextGen(k);
//		printGrid(k);
//		k=removeDeadLayers(k);
//		printGrid(k);
	}
	
	public static int[][] getGeneration(int[][] cells, int generations) {
		while(generations!=0 && cells.length!=0){
			cells=addAnOuterLayer(cells);
			//printGrid(cells);
			generateNextGen(cells);
			updateNextGen(cells);
			cells=removeDeadLayers(cells);
			//printGrid(cells);
			generations--;
		}
		

		//printGrid(cells);

		return cells;
	  }	

	public static void generateNextGen(int[][] cells){
		for(int i=0; i<cells.length; i++){
			for(int j=0; j<cells[0].length; j++){
				int liveCounter=0;
				//System.out.println(i+""+j+ " ");
				for(int[] rowHood: MOORE_HOOD){
					int r=i+rowHood[0];
					int c=j+rowHood[1];	
					//System.out.print(r+""+c+ " ");
					
					//corner cases
//					if(r<0){
//						r=cells.length-1;
//					}
//					else if(r>(cells.length-1)){
//						r=0;
//					}
//					if(c<0){
//						c=cells[0].length-1;
//					}
//					else if(c>(cells[0].length-1)){
//						c=0;
//					}
					if(r<0||(r>(cells.length-1)|| c<0 || c>(cells[0].length-1))){
						continue;
					}
					//counts lives
					if(cells[r][c]==1 || cells[r][c]==-1){
						liveCounter++;}
				}
				//System.out.println();
				//checks rules. -1 means its alive byt meant to die. 2 means its dead but meant to about to be ressurected
				if(cells[i][j]==1 && (liveCounter<2 || liveCounter>3)){
					cells[i][j]=-1;
				}
				else if(cells[i][j]==0 && liveCounter==3){
					cells[i][j]=2;
				}
			}
		}
	}
	
	public static void updateNextGen(int[][] cells){
		for(int i=0; i<cells.length; i++){
			for(int j=0; j<cells[0].length; j++){
				if(cells[i][j]==-1){
					cells[i][j]=0;
				}
				else if(cells[i][j]==2){
					cells[i][j]=1;
				}
			}
		}
	}
	
	public static int[][] addAnOuterLayer(int[][] cells){
		if(cells.length==1 && cells[0].length==0) return new int[1][0];
		
		List<List<Integer>> dCells=convert2DArrayToList(cells);
		
		for(int i=0; i<4; i++){
			List<Integer> blanks=new ArrayList<Integer>();
			for(Integer j: dCells.get(0)){
				blanks.add(0);
			}
			
			dCells.add(0, blanks);
			dCells=rotate(dCells);
		}
		
		return convert2DListToArray(dCells);
	}
	
	public static int[][] removeDeadLayers(int[][] cells){
		List<List<Integer>> grid=new ArrayList<>();
		grid=convert2DArrayToList(cells);
		
		for(int i=0; i<4 && grid.size()!=0; i++){
			grid=removeAll0sRowsInUpperLayer(grid);
			grid=rotate(grid);
		}

		return convert2DListToArray(grid);
	}
	
	public static List<List<Integer>> removeAll0sRowsInUpperLayer(List<List<Integer>> grid){
		while(0<grid.size()){
			if(entireRow0(grid.get(0))){
				grid.remove(0);
			}
			else{
				break;
			}
		}
		return grid;
		
	}
	
	public static boolean entireRow0(List<Integer> i){
		boolean entireRow0=true;
		for(Integer k: i){
			if(k!=0){
				entireRow0=false;
			}
		}
		
		return entireRow0;
	}
	
	public static List<List<Integer>> rotate(List<List<Integer>> a){
		if(a.size()==1 && a.get(0).size()==0) return a;
		int[][] b=new int[a.get(0).size()][a.size()];
		for(int i=0; i<b.length; i++){
			for(int j=b[0].length-1, k=0; j>=0; j--, k++){
				b[i][k]=a.get(j).get(i);
			}
		}
		List<List<Integer>> bList=convert2DArrayToList(b);
		return bList;
	}
	
	public static List<List<Integer>> undoRotate(List<List<Integer>> a){ //check
		if(a.size()==1 && a.get(0).size()==0) return a;
		int[][] b=new int[a.get(0).size()][a.size()];
		for(int i=0, m=b.length-1; i<b.length; i++, m--){
			for(int j=b[0].length-1; j>=0; j--){
				b[m][j]=a.get(j).get(i);
			}
		}
		List<List<Integer>> bList=convert2DArrayToList(b);
		return bList;
	}
	
	public static List<List<Integer>> convert2DArrayToList(int[][] cells){
		List<List<Integer>> grid=new ArrayList<>();
		for(int[] row: cells){
			List<Integer> rowCellsList=new ArrayList<>();
			for(int cell: row){
				rowCellsList.add(cell);
			}
			grid.add(rowCellsList);
		}
		return grid;
	}
	
	public static int[][] convert2DListToArray(List<List<Integer>> grid){
		int[][] fixedCells=new int[grid.size()][grid.get(0).size()];
		for(int l=0; l<fixedCells.length; l++){
			for(int j=0; j<fixedCells[0].length; j++){
				fixedCells[l][j]=grid.get(l).get(j);
			}
		}
		return fixedCells;
	}
	
	public static void printGrid(int[][] cells){
		for(int[] rowC: cells){
			System.out.println();
			for(int cell: rowC){
				System.out.print(cell);
			}
		}
		System.out.println();
	}
	
//	public static boolean isAllDead(int[][] cells){ //isEmpty will also empty if empty
//		int counter=0;
//		boolean empty=false;
//		for(int[] rowCells: cells){
//			for(int cell: rowCells){
//				if(cell==0) counter++;
//			}
//		}
//		
//		if((cells.length*cells[0].length)==counter){
//			empty=true;
//		}
//		
//		return empty;
//	}

}
