package asia.nsgc.nsl.Activity;


public class ChartQuery {
	public String table = "";
	public String[] columns = new String[] { "date", "total_price" };
	public String selection = null;
	public String[] selectionArgs = null;
	public String groupBy = null;
	public String having = null;
	public String orderBy = "date ASC";
	
	public String title = "";
	public int substringIndex = 0;
}
