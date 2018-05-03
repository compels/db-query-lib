package net.compels.dbquerylib.base;

import java.util.ArrayList;
import java.util.List;

public class Join {

	public static final String INNER_JOIN = "INNER JOIN";

    public static final String LEFT_JOIN = "LEFT JOIN";

    public static final String RIGHT_JOIN = "RIGHT JOIN";

    public static final String FULL_JOIN = "FULL JOIN";
    
    public static final String OPERATION_EQUALS = "=";
    
    public static final String OPERATION_NOT_EQUALS = "!=";

    private String table;

    private String tableColumn;
    
    private String operation;

    private String column;

    private String type;
    
    private List<Criteria> criteriaList;

    public Join() {
        super();
    }
    
    public Join(String type, String table, String tableColumn, String operation, String column, List<Criteria> criteriaList) {
    	setType(type);
    	setTable(table);
    	setTableColumn(tableColumn);
    	setOperation(operation);
    	setColumn(column);
    	setCriteriaList(criteriaList);
    }
    
    public String getTable(){

        return table;
    }

    public void setTable( String table ){

        this.table = table;
    }

    public String getTableColumn(){

        return tableColumn;
    }

    public void setTableColumn( String tableColumn ){

        this.tableColumn = tableColumn;
    }

    public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getColumn(){

        return column;
    }

    public void setColumn( String column ){

        this.column = column;
    }

    public String getType(){

        return type;
    }
    
    public void setType(String type) {
    	this.type = type;
    }
    
    public static Join getJoin(String type, String table, String tableColumn, String operation, String column, List<Criteria> criteriaList) {
    	return new Join(type, table, tableColumn, operation, column, criteriaList);
    }

    public static Join getJoin(String type, String table, String tableColumn, String operation, String column) {
    	return getJoin(type, table, tableColumn, operation, column, null);
    }
    
    public static Join getJoin(String type, String table, String tableColumn, String column) {
    	return getJoin(type, table, tableColumn, Join.OPERATION_EQUALS, column);
    }
    
    public static List<Join> getJoinList (String type, String table, String tableColumn, String operation, String column) {
    	return getJoinList(type, table, tableColumn, operation, column, null);
    }

    public static List<Join> getJoinList (String type, String table, String tableColumn, String operation, String column, List<Criteria> criteriaList) {
    	List<Join> joinList = new ArrayList<Join>();
    	joinList.add(getJoin(type, table, tableColumn, operation, column));
    	return joinList;
    }
    
    public static List<Join> getJoinList (String type, String table, String tableColumn, String column) {
    	return getJoinList(type, table, tableColumn, Join.OPERATION_EQUALS, column);
    }
    
    public static Join getLeftJoin (String table, String tableColumn, String operation, String column, List<Criteria> criteriaList) {
    	return getJoin(Join.LEFT_JOIN, table, tableColumn, operation, column, criteriaList);
    }

    public static Join getLeftJoin (String table, String tableColumn, String operation, String column) {
    	return getJoin(Join.LEFT_JOIN, table, tableColumn, operation, column);
    }
    
    public static Join getLeftJoin(String table, String tableColumn, String column) {
    	return getLeftJoin(table, tableColumn, Join.OPERATION_EQUALS, column);
    }
    
    public static Join getRightJoin (String table, String tableColumn, String operation, String column, List<Criteria> criteriaList) {
    	return getJoin(Join.RIGHT_JOIN, table, tableColumn, operation, column, criteriaList);
    }

    public static Join getRightJoin (String table, String tableColumn, String operation, String column) {
    	return getJoin(Join.RIGHT_JOIN, table, tableColumn, operation, column);
    }
    
    public static Join getRightJoin(String table, String tableColumn, String column) {
    	return getRightJoin(table, tableColumn, Join.OPERATION_EQUALS, column);
    }
    
    public static Join getInnerJoin (String table, String tableColumn, String operation, String column, List<Criteria> criteriaList) {
		return getJoin(Join.INNER_JOIN, table, tableColumn, operation, column, criteriaList);
    }
    
    public static Join getInnerJoin (String table, String tableColumn, String operation, String column) {
    	return getJoin(Join.INNER_JOIN, table, tableColumn, operation, column);
    }
    
    public static Join getInnerJoin(String table, String tableColumn, String column, List<Criteria> criteriaList) {
    	return getInnerJoin(table, tableColumn, Join.OPERATION_EQUALS, column, criteriaList);
    }
    public static Join getInnerJoin(String table, String tableColumn, String column) {
    	return getInnerJoin(table, tableColumn, Join.OPERATION_EQUALS, column);
    }

	public List<Criteria> getCriteriaList() {
		return criteriaList;
	}

	public void setCriteriaList(List<Criteria> criteriaList) {
		this.criteriaList = criteriaList;
	}

}
