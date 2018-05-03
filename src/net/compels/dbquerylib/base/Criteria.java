package net.compels.dbquerylib.base;

import java.util.ArrayList;
import java.util.List;

public class Criteria {

	public static final String CONCAT_TRUE_AND = "TRUE AND";

	public static final String CONCAT_AND = "AND";

	public static final String CONCAT_OR = "OR";

	public static final String OPERATION_EQUALS = "=";

	public static final String OPERATION_NOT_EQUALS = "!=";

	public static final String OPERATION_LIKE = "LIKE";

	public static final String OPERATION_NOT_LIKE = "NOT LIKE";

	public static final String OPERATION_ILIKE = "ILIKE";

	public static final String OPERATION_NOT_ILIKE = "NOT ILIKE";

	public static final String OPERATION_BIGGER_THEN = ">";

	public static final String OPERATION_BIGGER_OR_EQUALS_THEN = ">=";

	public static final String OPERATION_LESS_THEN = "<";

	public static final String OPERATION_LESS_OR_EQUALS_THEN = "<=";

	public static final String OPERATION_IN = "IN";
	
	public static final String OPERATION_NOT_IN = "NOT IN";

	public static final String OPERATION_IS = "IS";

	public static final String OPERATION_IS_NOT = "IS NOT";

	private String concatType;

	private String attribute;

	private String operation;

	private Object value;

	private boolean withoutAccentuation;

	private boolean valueIsColumn;

	private boolean startSubQuery;

	private boolean endSubQuery;

	public Criteria() {
		super();
	}

	public Criteria(String atribute, String operation, Object value, boolean withoutAccentuation, String concatType, boolean valueIsColumn, boolean startSubQuery, boolean endSubQuery) {
		setAttribute(atribute);
		setOperation(operation);
		setValue(value);
		setWithoutAccentuation(withoutAccentuation);
		setConcatType(concatType);
		setValueIsColumn(valueIsColumn);
		setStartSubQuery(startSubQuery);
		setEndSubQuery(endSubQuery);
	}

	public String getConcatType() {
		return concatType;
	}

	public void setConcatType(String concatType) {
		this.concatType = concatType;
	}

	public static String getOperationEquals() {
		return OPERATION_EQUALS;
	}

	public static String getOperationIlike() {
		return OPERATION_ILIKE;
	}

	public static String getOperationBiggerThen() {
		return OPERATION_BIGGER_THEN;
	}

	public static String getOperationBiggerOrEqualsThen() {
		return OPERATION_BIGGER_OR_EQUALS_THEN;
	}

	public static String getOperationLessThen() {
		return OPERATION_LESS_THEN;
	}

	public static String getOperationLessOrEqualsThen() {
		return OPERATION_LESS_OR_EQUALS_THEN;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isWithoutAccentuation() {
		return withoutAccentuation;
	}

	public void setWithoutAccentuation(boolean withoutAccentuation) {
		this.withoutAccentuation = withoutAccentuation;
	}

	public boolean isValueIsColumn() {
		return valueIsColumn;
	}

	public void setValueIsColumn(boolean valueIsColumn) {
		this.valueIsColumn = valueIsColumn;
	}

	public boolean isStartSubQuery() {
		return startSubQuery;
	}

	public void setStartSubQuery(boolean startSubQuery) {
		this.startSubQuery = startSubQuery;
	}

	public boolean isEndSubQuery() {
		return endSubQuery;
	}

	public void setEndSubQuery(boolean endSubQuery) {
		this.endSubQuery = endSubQuery;
	}

	/**
	 * @param String atribute - nome do atributo para a consulta
	 * @param Object value - valor do atributo para a consulta
	 * @return Criteria
	 */
	public static Criteria getCriteria(String atribute, Object value) {
		return getCriteria(atribute, Criteria.OPERATION_EQUALS, value, false);
	}

	/**
	 * @param String atribute - nome do atributo para a consulta
	 * @param String operation - qual operação sera feita ( ILIKE, NOT ILIKE, =, !=, IN, NOT IN, <, <=, >, >=, IS, IS NULL ) 
	 * @param Object value - valor do atributo para a consulta
	 * @return Criteria
	 */
	public static Criteria getCriteria(String atribute, String operation, Object value) {
		return getCriteria(atribute, operation, value, false);
	}

	/**
	 * @param String atribute - nome do atributo para a consulta.
	 * @param String operation - qual operação sera feita. Ex: ILIKE, NOT ILIKE, =, !=, IN, NOT IN, <, <=, >, >=, IS, IS NULL, etc...
	 * @param Object value - valor do atributo para a consulta.
	 * @param String concatType - valor para concatenar na sentence. Ex: AND, OR, etc...
	 * @return Criteria
	 */
	public static Criteria getCriteria(String atribute, String operation, Object value, String concatType) {
		return getCriteria(atribute, operation, value, false, concatType, false, false, false);
	}

//	public static Criteria getCriteria(String atribute, Object value, String concatType) {
//		return getCriteria(atribute, Criteria.OPERATION_EQUALS, value, false, concatType, false, false, false);
//	}

	/**
	 * @param String atribute - nome do atributo para a consulta
	 * @param Object value - valor do atributo para a consulta
	 * @param boolean withoutAccentuation - se true retira a acentuação do valor
	 * @return Criteria
	 */
	public static Criteria getCriteria(String atribute, Object value, boolean withoutAccentuation) {
		return getCriteria(atribute, Criteria.OPERATION_EQUALS, value, withoutAccentuation);
	}

	/**
	 * @param String atribute - nome do atributo para a consulta
	 * @param String operation - qual operação sera feita. Ex: ILIKE, NOT ILIKE, =, !=, IN, NOT IN, <, <=, >, >=, IS, IS NULL, etc...
	 * @param Object value - valor do atributo para a consulta
	 * @param boolean withoutAccentuation - se true retira a acentuação do valor
	 * @return Criteria
	 */
	public static Criteria getCriteria(String atribute, String operation, Object value, boolean withoutAccentuation) {
		return getCriteria(atribute, operation, value, withoutAccentuation, null, false, false, false);
	}

	/**
	 * @param String atribute - nome do atributo para a consulta
	 * @param String operation - qual operação sera feita. Ex: ILIKE, NOT ILIKE, =, !=, IN, NOT IN, <, <=, >, >=, IS, IS NULL, etc...
	 * @param Object value - valor do atributo para a consulta
	 * @param boolean withoutAccentuation - se true retira a acentuação do valor
	 * @param String concatType - valor para concatenar na sentence. Ex: AND, OR, etc...
	 * @param boolean valueIsColumn -
	 * @param boolean startSubQuery - se true insere parentese abrindo '(' na sentence.
	 * @param boolean endSubQuery - se true insere parentese fechando ')' na sentence.
	 * @return Criteria
	 */
	public static Criteria getCriteria(String atribute, String operation, Object value, boolean withoutAccentuation, String concatType, boolean valueIsColumn, boolean startSubQuery, boolean endSubQuery) {
		return new Criteria(atribute, operation, value, withoutAccentuation, concatType, valueIsColumn, startSubQuery, endSubQuery);
	}

	/**
	 * @param String atribute - nome do atributo para a consulta
	 * @param Object value - valor do atributo para a consulta
	 * @return List Criteria
	 */
	public static List<Criteria> getCriteriaList(String atribute, Object value) {
		return getCriteriaList(atribute, Criteria.OPERATION_EQUALS, value, false, null, false, false, false);
	}

	/**
	 * @param String atribute - nome do atributo para a consulta
	 * @param String operation - qual operação sera feita. Ex: ILIKE, NOT ILIKE, =, !=, IN, NOT IN, <, <=, >, >=, IS, IS NULL, etc...
	 * @param Object value - valor do atributo para a consulta
	 * @return List Criteria
	 */
	public static List<Criteria> getCriteriaList(String atribute, String operation, Object value) {
		return getCriteriaList(atribute, operation, value, false, null, false, false, false);
	}

	/**
	 * @param String atribute - nome do atributo para a consulta
	 * @param Object value - valor do atributo para a consulta
	 * @param boolean withoutAccentuation - se true retira a acentuação do valor
	 * @return List Criteria
	 */
	public static List<Criteria> getCriteriaList(String atribute, Object value, boolean withoutAccentuation) {
		return getCriteriaList(atribute, Criteria.OPERATION_EQUALS, value, withoutAccentuation, null, false, false, false);
	}

	/**
	 * @param String atribute - nome do atributo para a consulta
	 * @param Object value - valor do atributo para a consulta
	 * @param boolean withoutAccentuation - se true retira a acentuação do valor
	 * @param String concatType - valor para concatenar na sentence. Ex: AND, OR, etc...
	 * @return List Criteria
	 */
	public static List<Criteria> getCriteriaList(String atribute, Object value, boolean withoutAccentuation, String concatType) {
		return getCriteriaList(atribute, Criteria.OPERATION_EQUALS, value, withoutAccentuation, concatType, false, false, false);
	}

	/**
	 * @param String atribute - nome do atributo para a consulta
	 * @param Object value - valor do atributo para a consulta
	 * @param boolean withoutAccentuation - se true retira a acentuação do valor
	 * @param String concatType - valor para concatenar na sentence. Ex: AND, OR, etc...
	 * @param boolean valueIsColumn -
	 * @return List Criteria
	 */
	public static List<Criteria> getCriteriaList(String atribute, Object value, boolean withoutAccentuation, String concatType, boolean valueIsColumn) {
		return getCriteriaList(atribute, Criteria.OPERATION_EQUALS, value, withoutAccentuation, concatType, valueIsColumn, false, false);
	}

	/**
	 * @param String atribute - nome do atributo para a consulta
	 * @param String operation - qual operação sera feita. Ex: ILIKE, NOT ILIKE, =, !=, IN, NOT IN, <, <=, >, >=, IS, IS NULL, etc...
	 * @param Object value - valor do atributo para a consulta
	 * @param boolean withoutAccentuation - se true retira a acentuação do valor
	 * @param String concatType - valor para concatenar na sentence. Ex: AND, OR, etc...
	 * @param boolean valueIsColumn -
	 * @param boolean startSubQuery - se true insere parentese abrindo '(' na sentence.
	 * @param boolean endSubQuery - se true insere parentese fechando ')' na sentence.
	 * @return List Criteria
	 */
	public static List<Criteria> getCriteriaList(String atribute, String operation, Object value, boolean withoutAccentuation, String concatType, boolean valueIsColumn, boolean startSubQuery, boolean endSubQuery) {
		List<Criteria> criteriaList = new ArrayList<Criteria>();
		criteriaList.add(getCriteria(atribute, operation, value, withoutAccentuation, concatType, valueIsColumn, startSubQuery, endSubQuery));
		return criteriaList;
	}

}
