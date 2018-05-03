package net.compels.dbquerylib.base;

public class Column {

	private String name;
	
	private String alias;
	
	public Column () {
		super();
	}
	
	public Column (String name, String alias) {
		setName(name);
		setAlias(alias);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public static Column getColumn (String name, String alias) {
		return new Column(name, alias);
	}
	
	public static Column getColumn (String name) {
		return new Column(name, null);
	}
}
