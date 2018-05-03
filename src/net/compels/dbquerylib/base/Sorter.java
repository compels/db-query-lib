package net.compels.dbquerylib.base;

import java.util.ArrayList;
import java.util.List;

public class Sorter {

	public static final String ORIENTATION_ASC = "ASC";

	public static final String ORIENTATION_DESC = "DESC";

	private String attribute;

	private String orientation;

	public Sorter() {
		super();
	}

	public Sorter(String attribute, String orientation) {
		setAttribute(attribute);
		setOrientation(orientation);
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public static String getOrientationAsc() {
		return ORIENTATION_ASC;
	}

	public static String getOrientationDesc() {
		return ORIENTATION_DESC;
	}

	/**
	 * @param String attribute - nome do atributo para ser ordenado
	 * @param String orientation - por padrão é ASC
	 * @return Sorter
	 */
	public static Sorter getSorter(String attribute) {
		return new Sorter(attribute, ORIENTATION_ASC);
	}

	/**
	 * @param String attribute - nome do atributo para ser ordenado
	 * @param Object orientation - ASC ou DESC
	 * @return Sorter
	 */
	public static Sorter getSorter(String attribute, String orientation) {
		return new Sorter(attribute, orientation);
	}

	/**
	 * @param String attribute - nome do atributo para ser ordenado
	 * @param String orientation - por padrão é ASC
	 * @return List Sorter
	 */
	public static List<Sorter> getSorterList(String attribute) {
		return getSorterList(attribute, ORIENTATION_ASC);
	}
	
	/**
	 * @param String attribute - nome do atributo para ser ordenado
	 * @param Object orientation - ASC ou DESC
	 * @return List Sorter
	 */
	public static List<Sorter> getSorterList(String attribute, String orientation) {
		List<Sorter> sorterList = new ArrayList<Sorter>();
		sorterList.add(getSorter(attribute, orientation));
		return sorterList;
	}
}
