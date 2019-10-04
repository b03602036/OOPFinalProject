import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlManager{
	Connection conn;
	
	public SqlManager() {
		try {
			this.conn = DriverManager.getConnection("jdbc:sqlite:Hotel7.db");
		    this.conn.setAutoCommit(false);
		}
		catch (SQLException e){
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}
	
	/**
	 * This method is used to obtain data of a hotel by giving its index.
	 * 
	 * @param hotelID 
	 * 		Specify which hotel data you are requesting.
	 * @return
	 * 		An Hotel Object with data corresponding to the given hotelID.
	 */
	public Hotel getHotel(int hotelID) {
		Hotel res = null;
		Statement stmt = null;
		try {
		    stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT * FROM Hotel WHERE HotelID=="+hotelID);
		    res = new Hotel(
				rs.getInt("HotelID"),
				rs.getInt("HotelStar"),
				rs.getString("Locality"),
				rs.getString("StreetAddress"),
				rs.getInt("SinglePrice"),
				rs.getInt("DoublePrice"),
				rs.getInt("QuadPrice"),
				rs.getInt("SingleNum"),
				rs.getInt("DoubleNum"),
				rs.getInt("QuadNum"),
				rs.getString("SingleRoom"),
				rs.getString("DoubleRoom"),
				rs.getString("QuadRoom")
			);
		}
		catch (SQLException e) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}

		return res;
	}

	/**
	 * This method will return all data in the database, within the form of array of Hotel object.
	 * 
	 * @return
	 * 		An array of Hotel.
	 */
	public List<Hotel> getAllHotel() {
		List<Hotel> hotels = new ArrayList<>();
		Statement stmt = null;
		try {
		    stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Hotel");
			int index = 0;
			while(rs.next()){
			    hotels.add(index, new Hotel(
						rs.getInt("HotelID"),
						rs.getInt("HotelStar"),
						rs.getString("Locality"),
						rs.getString("StreetAddress"),
						rs.getInt("SinglePrice"),
						rs.getInt("DoublePrice"),
						rs.getInt("QuadPrice"),
						rs.getInt("SingleNum"),
						rs.getInt("DoubleNum"),
						rs.getInt("QuadNum"),
						rs.getString("SingleRoom"),
						rs.getString("DoubleRoom"),
						rs.getString("QuadRoom")

				));
			    index += 1;
			}
		}
		catch(SQLException e) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}

		return hotels;
	}
	
	/**
	 * 
	 * @param hotelID
	 * 		Specify which hotel data you are setting.
	 * @param newHotel
	 * 		The new data you want to update.
	 * @return
	 * 		True if success. Otherwise, return false.
	 */
	public boolean setHotel(int hotelID, Hotel newHotel) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE Hotel SET HotelStar="+newHotel.getHotelStar()+" WHERE HotelID="+hotelID);
			stmt.executeUpdate("UPDATE Hotel SET Locality='"+newHotel.getLocality()+"' WHERE HotelID="+hotelID);
			stmt.executeUpdate("UPDATE Hotel SET StreetAddress='"+newHotel.getStreetAddress()+"' WHERE HotelID="+hotelID);
			stmt.executeUpdate("UPDATE Hotel SET SinglePrice="+newHotel.getSinglePrice()+" WHERE HotelID="+hotelID);
			stmt.executeUpdate("UPDATE Hotel SET SingleNum="+newHotel.getSingleNum()+" WHERE HotelID="+hotelID);
			stmt.executeUpdate("UPDATE Hotel SET DoublePrice="+newHotel.getDoublePrice()+" WHERE HotelID="+hotelID);
			stmt.executeUpdate("UPDATE Hotel SET DoubleNum="+newHotel.getDoubleNum()+" WHERE HotelID="+hotelID);
			stmt.executeUpdate("UPDATE Hotel SET QuadPrice="+newHotel.getQuadPrice()+" WHERE HotelID="+hotelID);
			stmt.executeUpdate("UPDATE Hotel SET QuadNum="+newHotel.getQuadNum()+" WHERE HotelID="+hotelID);

			stmt.executeUpdate("UPDATE Hotel SET SingleRoom='"+newHotel.getSingleRoom()+"' WHERE HotelID="+hotelID);
			stmt.executeUpdate("UPDATE Hotel SET DoubleRoom='"+newHotel.getDoubleRoom()+"' WHERE HotelID="+hotelID);
			stmt.executeUpdate("UPDATE Hotel SET QuadRoom='"+newHotel.getQuadRoom()+"' WHERE HotelID="+hotelID);

			//conn.commit();
		}
		catch(SQLException e) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      return false;
		}
		return true;
	}

	public boolean addOrder(Order o) {
		String sql = "INSERT INTO Orders(OrderID,UserID,HotelID,CheckIn,CheckOut,RoomType,RoomNum) VALUES(?,?,?,?,?,?,?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, o.getOrderID());
			pstmt.setString(2, o.getUserID());
			pstmt.setInt(3, o.getHotelID());
			pstmt.setLong(4, o.getCheckIn().getTime());
			pstmt.setLong(5, o.getCheckOut().getTime());
			pstmt.setString(6, o.getRoomType());
			pstmt.setInt(7, o.getRoomNum());
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			System.out.print("\n");
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return false;
		}

		return true;
	}
	public Order getOrder(int orderID, String userID) {
		try {
			String sql = String.format("SELECT * FROM Orders WHERE OrderID==%d AND UserID==\"%s\"", orderID, userID);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			Order res = new Order(
					rs.getInt("OrderID"),
					rs.getString("UserID"),
					rs.getInt("HotelID"),
					new Date(rs.getLong("CheckIn")),
					new Date(rs.getLong("CheckOut")),
					rs.getString("roomType"),
					rs.getInt("roomNum")
			);
			return res;
		}
		catch(SQLException e) {
			System.out.print("\n");
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return null;
		}
	}

	public Order deleteOrder(int orderID, String userID) {
		try {
			Order res = getOrder(orderID, userID);
			String sql = String.format("DELETE FROM Orders WHERE OrderID==%d AND UserID==\"%s\"", orderID, userID);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			return res;
		}
		catch(SQLException e) {
			System.out.print("\n");
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return null;
		}
	}



}
