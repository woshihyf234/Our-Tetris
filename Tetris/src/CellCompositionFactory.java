import java.util.Random;

public class CellCompositionFactory {
	private int initRow;
	
	private int initCol;
	
	Random rand;
	
	CellCompositionFactory(int _initRow, int _initCol)
	{
		initRow = _initRow;
		initCol = _initCol;
		rand = new Random(6);
	}
	
	public CellComposition CreateCellComposition()
	{
		int typeNumber = rand.nextInt();
		switch(typeNumber)
		{
		case 0:
			return CreateLine();
		case 1:
			return CreateTian();
		default:
			throw new IllegalArgumentException("not accepted typeNumber!");
		}
	}
	
	private CellComposition CreateLine()
	{
		int[] rows = {1,2,3,4};
		int[] cols = {1,2,3,4};
		
		for(int i = 0 ; i < rows.length ; i ++)
		{
			rows[i] += initRow;
			cols[i] += initCol;
		}
		
		return new CellComposition(rows, cols);
	}
	
	private CellComposition CreateTian()
	{
		int[] rows = {1,2,3,4};
		int[] cols = {1,2,3,4};

		for(int i = 0 ; i < rows.length ; i ++)
		{
			rows[i] += initRow;
			cols[i] += initCol;
		}
		
		return new CellComposition(rows, cols);
	}
}
