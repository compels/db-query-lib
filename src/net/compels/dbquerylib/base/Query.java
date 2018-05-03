package net.compels.dbquerylib.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class Query {

	public static final short INSERT = 0;
	public static final short UPDATE = 1;
	public static final short DELETE = 2;
	public static final short SELECT = 3;
	public static final short NATIVE = 4;

	private short type;

	private boolean count;

	private String table;

	private String returning;

	private String nativeQuery;

	private Map<String, Object> map;

	private List<Join> joinList;

	private List<Criteria> criteriaList;

	private List<Sorter> sorterList;

	private List<Column> columnList;

	private List<Column> groupByList;

	private Pagination pagination;

	public Query(String table, short type) {
		this.type = type;
		this.table = table;
		this.map = new LinkedHashMap<String, Object>();
	}

	public void setParameter(String key, Object value) {

		this.map.put(key, value);
	}

	public void setJoin(List<Join> joinList) {

		this.joinList = joinList;
	}

	public void setCriteria(List<Criteria> criteriaList) {

		this.criteriaList = criteriaList;
	}

	public void setSorter(List<Sorter> sorterList) {

		this.sorterList = sorterList;
	}

	public void setPagination(Pagination pagination) {

		this.pagination = pagination;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

	public void setGroupBylist(List<Column> groupBylist) {
		this.groupByList = groupBylist;
	}

	public void setReturn(String returning) {

		this.returning = returning;
	}

	private boolean isCount() {

		return count;
	}

	public void setCount(boolean count) {

		this.count = count;
	}

	public Object[] getArgs() {

		List<Object> listArgs = new LinkedList<Object>();

		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			listArgs.add(map.get(key));
		}
		if (criteriaList != null && !criteriaList.isEmpty()) {
			for (Criteria criteria : criteriaList) {
				if (criteria.getOperation() != Criteria.OPERATION_IN && criteria.getOperation() != Criteria.OPERATION_NOT_IN) {
					if (criteria.getValue() != null)
						listArgs.add(criteria.getValue());
				}
			}
		}
		if (pagination != null) {
			listArgs.add(pagination.getLimit());
			listArgs.add(pagination.getOffset());
		}
		return listArgs.toArray(new Object[listArgs.size()]);
	}

	private String[] getPreparedValues() {

		List<Object> listArgs = new LinkedList<Object>();
		int size = map.keySet().size();
		for (int i = 0; i < size; i++)
			listArgs.add("?");

		return listArgs.toArray(new String[listArgs.size()]);

	}

	private String[] getColumnsForUpdate() {

		List<Object> listArgs = new LinkedList<Object>();

		Iterator<String> it = map.keySet().iterator();

		while (it.hasNext()) {
			String key = it.next();
			StringBuffer buff = new StringBuffer();
			buff.append(key);
			buff.append(" = ?");
			listArgs.add(buff.toString());
		}
		return listArgs.toArray(new String[listArgs.size()]);

	}

	private String[] getColumns() {

		List<String> listArgs = new LinkedList<String>();

		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			listArgs.add(key);
		}

		return listArgs.toArray(new String[listArgs.size()]);

	}

	private String getNativeQuery() {

		return nativeQuery;
	}

	public void setNativeQuery(String specificQuery) {

		this.nativeQuery = specificQuery;
	}

	public String toSql() {

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		switch (this.type) {
		case INSERT:
			sql.append("INSERT INTO ");
			sql.append(this.table).append(" ");

			String keys = String.join(", ", getColumns());
			String values = String.join(", ", getPreparedValues());

			sql.append("(");
			sql.append(keys);
			sql.append(")");
			sql.append(" VALUES ");
			sql.append("(");
			sql.append(values);
			sql.append(")");

			if (returning != null)
				sql.append(" RETURNING ").append(returning);
			break;

		case UPDATE:
			sql.append("UPDATE ");
			sql.append(this.table);
			sql.append(" SET ");
			sql.append(String.join(",", getColumnsForUpdate()));

			if (criteriaList == null || criteriaList.isEmpty()) {
				sql = null;
				throw new IllegalArgumentException("Criteria não pode ser nulo ou vazio!");
			}

			sql.append(createCriteria(criteriaList, params));
			break;

		case DELETE:
			sql.append("DELETE FROM ");
			sql.append(this.table);

			if (criteriaList == null || criteriaList.isEmpty()) {
				sql = null;
				throw new IllegalArgumentException("Criteria não pode ser nulo ou vazio!");
			}
			sql.append(createCriteria(criteriaList, params));
			break;

		case SELECT:
			sql.append("SELECT ");

			if (isCount()) {
				sql.append("COUNT (*) ");
			} else {
				if (columnList != null && !columnList.isEmpty()) {
					sql.append(createColumnSelection(columnList) + " ");
				} else {
					sql.append("* ");
				}
			}

			sql.append("FROM ");
			sql.append(this.table);
			sql.append(createJoins(joinList, params));
			sql.append(createCriteria(criteriaList, params));
			sql.append(createGroupBy(groupByList));
			sql.append(createSorter(sorterList));
			sql.append(createPagination(pagination, params));
			break;

		case NATIVE:
			sql.append(getNativeQuery());
			break;

		default:
			break;
		}

		return sql.toString();
	}

	public static String createJoins(List<Join> joinList, List<Object> params) {

		StringBuffer query = new StringBuffer();
		if (joinList != null && !joinList.isEmpty()) {
			for (Join join : joinList) {
				query.append(" ");
				query.append(join.getType());
				query.append(" ");
				query.append(join.getTable());
				query.append(" ON ");
				query.append(join.getTableColumn());
				query.append(" ");
				query.append(join.getOperation());
				query.append(" ");
				query.append(join.getColumn());
				query.append(" ");
				if (join.getCriteriaList() != null && !join.getCriteriaList().isEmpty()) {
					for (Criteria criteria : join.getCriteriaList()) {
						if (criteria.isStartSubQuery())
							query.append("( ");
						query.append(criteria.getConcatType());
						query.append(" ");
						query.append(criteria.getAttribute());
						query.append(" ");
						query.append(criteria.getOperation());
						query.append(" ");
						query.append(criteria.getValue());
						query.append(" ");
						if (criteria.isEndSubQuery())
							query.append(") ");
					}
				}
			}
		}
		return query.toString();
	}

	public static String createCriteria(List<Criteria> criteriaList, List<Object> params) {

		String query = "";

		if (criteriaList != null && !criteriaList.isEmpty()) {
			query += " WHERE ";
			for (Criteria criteria : criteriaList) {
				if (criteria.getAttribute() != null) {
					if (criteria.getConcatType() != null)
						query += criteria.getConcatType() + " ";

					if (criteria.isStartSubQuery())
						query += "( ";

					if (criteria.isWithoutAccentuation())
						query += "retira_acentuacao(" + criteria.getAttribute() + ")";
					else
						query += criteria.getAttribute();
					query += " ";
					query += criteria.getOperation();
					if (criteria.getOperation() != Criteria.OPERATION_IN && criteria.getOperation() != Criteria.OPERATION_NOT_IN) {
						if (criteria.getOperation() == Criteria.OPERATION_IS || criteria.getOperation() == Criteria.OPERATION_IS_NOT) {
							if (criteria.getValue() == null) {
								query += " NULL ";
							} else {
								if (criteria.isWithoutAccentuation() && criteria.getValue() instanceof String) {
									query += " retira_acentuacao(?) ";
								} else {
									query += " ? ";
								}
								params.add(criteria.getValue());
							}
						} else {
							if (criteria.isWithoutAccentuation() && criteria.getValue() instanceof String) {
								query += " retira_acentuacao(?) ";
							} else {
								query += " ? ";
							}
							params.add(criteria.getValue());
						}
					} else {
						query += "( ";
						if (criteria.getValue() instanceof List) {
							List<Object> values = (List<Object>) criteria.getValue();
							if (values != null) {
								for (Object value : values) {
									query += "?";
									if (values.indexOf(value) != (values.size() - 1))
										query += ", ";
									params.add(value);
								}
							}
						} else {
							query += criteria.getValue();
						}
						query += ") ";
					}
					if (criteria.isEndSubQuery())
						query += ") ";
				}
			}
		}

		return query;
	}

	public static String createSorter(List<Sorter> sorterList) {

		String query = "";

		if (sorterList != null) {
			query += " ORDER BY ";
			for (int indexSorter = 0; indexSorter < sorterList.size(); indexSorter++) {
				Sorter sorter = sorterList.get(indexSorter);
				query += sorter.getAttribute() + " ";
				if (sorter.getOrientation() != null) {
					query += sorter.getOrientation() + " ";
				}
				if ((indexSorter + 1) < sorterList.size()) {
					query += ", ";
				}
			}
		}

		return query;
	}

	public static String createPagination(Pagination pagination, List<Object> params) {

		String query = "";

		if (pagination != null) {
			query += " LIMIT ? ";
			query += " OFFSET ? ";
			params.add(pagination.getLimit());
			params.add(pagination.getOffset());
		}

		return query;
	}

	public static String createColumnSelection(List<Column> columnList) {

		String query = "";
		if (columnList != null && !columnList.isEmpty()) {
			for (Column column : columnList) {
				if (column != null) {
					if (column.getName() != null) {
						query += column.getName();
					}
					if (column.getAlias() != null) {
						query += " AS " + column.getAlias();
					}
					if ((columnList.indexOf(column) + 1) != columnList.size()) {
						query += ", ";
					}
				}
			}
		}
		return query;
	}

	public static String createGroupBy(List<Column> groupByList) {
		String query = "";
		if (groupByList != null && !groupByList.isEmpty()) {
			query += " GROUP BY ";
			for (Column column : groupByList) {
				if (column != null) {
					if (column.getName() != null && !column.getName().isEmpty()) {
						query += column.getName();
					}
					if ((groupByList.indexOf(column) + 1) != groupByList.size()) {
						query += ", ";
					}
				}
			}
		}
		return query;
	}

}
