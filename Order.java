import java.util.Date;

public class Order {
	private int orderID;
	private String userID;
	private int hotelID;
	private Date checkIn;
	private Date checkOut;
	private String roomType;
	private int roomNum;
	
	public Order(int orderID, String userID, int hotelID, Date checkIn, Date checkOut, String roomType, int roomNum) {
		this.userID = new String(userID);
		this.hotelID = hotelID;
		this.checkIn = new Date(checkIn.getTime());
		this.checkOut = new Date(checkOut.getTime());
		this.roomType = new String(roomType);
		this.roomNum = roomNum;
		this.orderID = orderID;
	}
	
	public String getUserID() {return new String(userID);}
	public int getHotelID() {return hotelID;}
	public Date getCheckIn() {return new Date(checkIn.getTime());}
	public Date getCheckOut() {return new Date(checkOut.getTime());}
	public String getRoomType() {return  new String(roomType);}
	public int getRoomNum() {return roomNum;}
	public int getOrderID() {return orderID;}

	public String toString() {
		String result = new String();
		result = result+"HotelID = " + hotelID + "\n";
		result = result+"userID = "+userID+"\n";
		result = result + "checkIn = "+ checkIn.getYear() + "/" + checkIn.getMonth() + "/" + checkIn.getDate()+"\n";
		result = result+"checkIn = "+ checkOut.getYear() + "/" + checkOut.getMonth()+ "/" + checkOut.getDate()+"\n";
		result = result+"roomType = "+roomType+"\n";
		result = result+"roomNum   = "+roomNum+"\n";
		return result;
	}

	
}
