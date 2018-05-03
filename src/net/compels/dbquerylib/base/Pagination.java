package net.compels.dbquerylib.base;

public class Pagination {

    private Long total;
    
	private Long offset;

	private Long limit;

	public Long getOffset() {
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

    
    public Long getTotal(){
    
        return total;
    }

    
    public void setTotal( Long total ){
    
        this.total = total;
    }

}
