package eg.edu.alexu.csd.filestructure.btree.cs_5_20;

import eg.edu.alexu.csd.filestructure.btree.ISearchResult;

public class MySearchResult implements ISearchResult {
	private String id;
	private int rank;
	
	public MySearchResult() {
		
	}
	
	public MySearchResult(String id,int rank) {
		this.id = id;
		this.rank = rank;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
		
	}

	@Override
	public int getRank() {
		return rank;
	}

	@Override
	public void setRank(int rank) {
		this.rank = rank;
	}

}
