/**
 * This class allows access and requests to database
 * @author The Coding Bang Fraternity
 * @version 5.0
 */

package model;

import java.io.File;
import java.sql.*;

public class Database {

	java.sql.Connection connection = null;

	/**
	 * Simple constructor
	 */
	public Database() {
		this.connect();
	}

	/**
	 * Init the connection with DB
	 */
	private void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:mydb.db");
			
			Statement statement = connection.createStatement();
		    String sql = "CREATE TABLE IF NOT EXISTS `tweet` ( `id_tweet` bigint(20) NOT NULL," +
		      "`id_request` int(5) NOT NULL, `name` varchar(25) NOT NULL, `screenName` varchar(25) NOT NULL, " +
		      "`text` varchar(170) NOT NULL, `retweet` int(8) NOT NULL, `city` varchar(40) DEFAULT NULL, " +
		      "`country` varchar(30) DEFAULT NULL, `latitude` double NOT NULL,  `longitude` double NOT NULL, " +
		      "`date_tweet` long NOT NULL, `profile` varchar(150) NOT NULL, `content` varchar(150) NOT NULL, PRIMARY KEY " +
		      "(`id_tweet`,`id_request`))"; 
		    statement.executeUpdate(sql);
		    
		    sql = "CREATE TABLE IF NOT EXISTS `user` ( `id_user` bigint(20) NOT NULL, `id_request` int(5) NOT NULL, " +
		    		"`name` varchar(25) NOT NULL, `screen_name` varchar(25) NOT NULL, `profil` varchar(150), PRIMARY KEY (`id_user`,`id_request`) " +
		    		")";
		    statement.executeUpdate(sql);
		
			sql = "CREATE TABLE IF NOT EXISTS `request` ( `id_request` INTEGER PRIMARY KEY AUTOINCREMENT, `type` varchar(10) " +
					"NOT NULL, `reference` varchar(30) NOT NULL, `req` varchar(20) NOT NULL)";
			statement.executeUpdate(sql);
		      
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Init a connection
	 */
	public void init() {
		try {
			connection.close();
			connection = DriverManager.getConnection("jdbc:sqlite:mydb.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Close connection with DB, should be called at the program ends
	 */
	public void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException ignore) {
			}
		}
	}
	
	/**
	 * Reinit request and tweet Table
	 */
	public int reinit(){
		try {
			init();
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM request");
			statement.executeUpdate("DELETE FROM tweet");
			statement.executeUpdate("DELETE FROM user");
			statement.executeUpdate("VACUUM");
			Media.deleteMedia("./SavedMedia/");
			new File("SavedMedia").mkdir(); 
			statement.close();
			close();
			
		} catch (SQLException e) {
			return -1;
		}
		return 0;
	}
	
	/**
	 * Make a select request to DB
	 * @param request
	 * @return results or null
	 */
	public ResultSet select_request(String request){
		try {
			init();
			PreparedStatement ps = connection.prepareStatement(request);
			//System.out.println(request);
			return ps.executeQuery();	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Make an insert request to DB
	 * @param request
	 * @return 0 error, 1 success on insert request
	 * @return number row updated on update and delete request
	 */
	public int request(String request){
		try {
			init();
			PreparedStatement ps = connection.prepareStatement(request);
			//System.out.println(request);
			return ps.executeUpdate();	
			
		} catch (SQLException e) {
			System.out.println("ERROR -> "+request);
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Get the Auto Increment of Request Table
	 * @return Auto Increment
	 * @throws SQLException
	 */
	public int getAutoIncRequest() throws SQLException{
		String query = "SELECT COUNT(*) AS rowid FROM request LIMIT 1";
		ResultSet res = this.select_request(query);
		if(!res.next())
			return 1;

		return res.getInt("rowid")+1;
	}
}
