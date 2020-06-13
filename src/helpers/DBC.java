package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBC {
	
	private Connection conn;
	private PropertiesReader pr;
	private String query;
	private List<String> parameters;
	private List<String> check;
	private PreparedStatement psta;
	
	public DBC() {
		pr = new PropertiesReader();
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			this.conn = DriverManager.getConnection(pr.getProperty("url"),pr.getProperty("user"),pr.getProperty("password"));
		}catch(SQLException|ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private void clear() {
		this.query = null;
		this.parameters.clear();
		this.psta = null;

	}
	
	public String getSentence(String sentence) {
		this.query = pr.getProperty(sentence);
		return this.query;
	}
	
	public void prepare(String sentence) {
		if(this.parameters.isEmpty()) {
			this.query=sentence;
		}
		else {
			try {
				this.psta = conn.prepareStatement(sentence);
				for(int i=0; i<this.parameters.size();i++) {
					if(parameters.get(i).equals("true")||parameters.get(i).equals("false")) {
						Boolean b = Boolean.parseBoolean(parameters.get(i));
						this.psta.setObject((i+1),b);
						
					}
					else {
						this.psta.setObject((i+1),parameters.get(i));
					}
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void preparet(String sentence) {
		if(this.parameters.isEmpty()) {
			this.query=sentence;
		}
		else {
			try {
				this.psta = conn.prepareStatement(sentence);
				for(int i=0; i<this.parameters.size();i++) {
					try {
						if(!check.get(i).isEmpty()) {
							if(check.get(i).equals("boolean"))
								this.psta.setObject((i+1),Boolean.getBoolean(this.parameters.get(i)));
							if(check.get(i).equals("int"))
								this.psta.setObject((i+1), Integer.parseInt(this.parameters.get(i)));
							if(check.get(i).equals("float"))
								this.psta.setObject((i+1), Float.parseFloat(this.parameters.get(i)));
						}
						else {
							System.out.println("here to string and check is");
							this.psta.setObject((i+1),parameters.get(i));
						}
					} catch(NullPointerException e) {
						this.psta.setObject((i+1),parameters.get(i));
					}
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public ResultSet exQuery() throws SQLException {
		if(this.psta!=null) {
			try {
				ResultSet rs = this.psta.executeQuery();
				clear();
				return rs;
			}catch(SQLException e) {
				return null;
			}
		}
		else {
			Statement st = this.conn.createStatement();
			ResultSet rs = st.executeQuery(this.query);
			clear();
			return rs;
		}
	}
	
	private void addCheck(int i, String s) {
		if(this.check==null) {
			this.check = new ArrayList<String>();
		}
		this.check.add(i,s);;
	}
	
	public void addParameter(String p) {
		if(this.parameters==null) {
			this.parameters = new ArrayList<String>();
		}
		this.parameters.add(p);
	}

	public void addParameter(int p) {
		String s = Integer.toString(p);
		addParameter(s);
		//addCheck(this.parameters.lastIndexOf(s),"int");
		
	}
	
	public void addParameter(float p) {
		String s = Float.toString(p);
		addParameter(s);
		//addCheck(this.parameters.lastIndexOf(s),"float");
		//System.out.println(this.parameters.lastIndexOf(s));
	}
	
	public void addParameter (Boolean p) {
		String s = Boolean.toString(p);
		addParameter(s);
		//addCheck(this.parameters.lastIndexOf(s),"boolean");
		//System.out.println("Last index" +this.parameters.lastIndexOf(s));
	}

}
