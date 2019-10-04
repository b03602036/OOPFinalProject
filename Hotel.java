import java.util.Comparator;

public class Hotel{
	private int hotelID;
	private int hotelStar;
	private String locality;
	private String streetAddress;
	private int singlePrice;
	private int doublePrice;
	private int quadPrice;
	private int singleNum;
	private int doubleNum;
	private int quadNum;
	private String singleRoom;
	private String doubleRoom;
	private String quadRoom;


	public Hotel(int hotelID, int hotelStar, String locality,
				 String streetAddress, int sp, int dp, int qp,
				 int sn, int dn, int qn, String sR, String dR, String qR) {
		this.hotelID = hotelID;
		this.hotelStar = hotelStar;
		this.locality = new String(locality);
		this.streetAddress = new String(streetAddress);
		this.singlePrice = sp;
		this.doublePrice = dp;
		this.quadPrice = qp;
		this.singleNum = sn;
		this.doubleNum = dn;
		this.quadNum = qn;
		this.singleRoom = new String(sR);
		this.doubleRoom = new String(dR);
		this.quadRoom = new String(qR);

	}
	
	public Hotel(Hotel other) {
		this.hotelID = other.getHotelID();
		this.hotelStar = other.getHotelStar();
		this.locality = new String(other.getLocality());
		this.streetAddress = new String(other.getStreetAddress());
		this.singlePrice = other.getSinglePrice();
		this.doublePrice = other.getDoublePrice();
		this.quadPrice = other.getQuadPrice();
		this.singleNum = other.getSingleNum();
		this.doubleNum = other.getDoubleNum();
		this.quadNum = other.getQuadNum();

		this.singleRoom = other.getSingleRoom();
		this.doubleRoom = other.getDoubleRoom();
		this.quadRoom = other.getQuadRoom();

	}
	
	public String toString() {
		String result = new String();
		result = result+"HotelID = "+hotelID+"\n";
		result = result+"HotelStar = "+hotelStar+"\n";
		result = result+"Locality = "+locality+"\n";
		result = result+"StreetAddress = "+streetAddress+"\n";
		result = result+"SinglePrice = "+singlePrice+"\n";
		result = result+"SingleNum   = "+singleNum+"\n";
		result = result+"DoublePrice = "+doublePrice+"\n";
		result = result+"DoubleNum   = "+doubleNum+"\n";
		result = result+"QuadPrice   = "+quadPrice+"\n";
		result = result+"QuadNum     = "+quadNum+"\n";

		return result;
	}


	public String toString(int rmtype) {
		String result = new String();
		result = result+"|HotelID = "+hotelID+"\n";
		result = result+"|HotelStar = "+hotelStar+"\n";
		result = result+"|Locality = "+locality+"\n";
		result = result+"|StreetAddress = "+streetAddress+"\n";
		switch(rmtype){
			case 1: {
				result = result+"|SinglePrice = "+singlePrice+"\n";
				break;
			}
			case 2 : {
				result = result+"|DoublePrice = "+doublePrice+"\n";
				break;
			}
			case 4:{
				result = result+"|QuadPrice = "+quadPrice+"\n";
				break;
			}
		}


		return result;
	}
	
	public int getHotelID() {return hotelID;}
	public int getHotelStar() {return hotelStar;}
	public String getLocality() {return new String(locality);}
	public String getStreetAddress() {return new String(streetAddress);}
	public int getSinglePrice() {return singlePrice;}
	public int getDoublePrice() {return doublePrice;}
	public int getQuadPrice() {return quadPrice;}
	public int getSingleNum() {return singleNum;}
	public int getDoubleNum() {return doubleNum;}
	public int getQuadNum() {return quadNum;}
	public String getSingleRoom() {return new String(singleRoom);}
	public String getDoubleRoom() {return new String(doubleRoom);}
	public String getQuadRoom() {return new String(quadRoom);}
	public int[] getSingleRoomArray() {
		String[] tmp = singleRoom.split("/");
		int[] res = new int[tmp.length];
		for (int i = 0; i<tmp.length; i++) {
			res[i] = Integer.parseInt(tmp[i]);
		}
		return res;
	}
	public int[] getDoubleRoomArray() {
		String[] tmp = doubleRoom.split("/");
		int[] res = new int[tmp.length];
		for (int i = 0; i<tmp.length; i++) {
			res[i] = Integer.parseInt(tmp[i]);
		}
		return res;
	}

	public int[] getQuadRoomArray() {
		String[] tmp = quadRoom.split("/");
		int[] res = new int[tmp.length];
		for (int i = 0; i<tmp.length; i++) {
			res[i] = Integer.parseInt(tmp[i]);
		}
		return res;
	}

	public int getColumn(String column_lab){
		column_lab = column_lab.toLowerCase();
		try{
			switch (column_lab){
				case "hotelid" : return hotelID;
				case "hotelstar" : return hotelStar;
				case "singleprice" : return singlePrice;
				case "doubleprice" : return doublePrice;
				case "quadprice" : return quadPrice;
				case "singlenum" : return singleNum;
				case "doublenum" : return doubleNum;
				case "quadnum" : return quadNum;
				default: System.out.println("Illegal Input");
			}
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
 		return -1;
	}


	public void setHotelID(int x) {hotelID = x;}
	public void setHotelStar(int x) {hotelStar = x;}
	public void setLocality(String x) {locality = new String(x);}
	public void setStreetAddress(String x) {streetAddress =  new String(x);}
	public void setSinglePrice(int x) {singlePrice = x;}
	public void setDoublePrice(int x) {doublePrice = x;}
	public void setQuadPrice(int x) {quadPrice = x;}
	public void setSingleNum(int x) {singleNum = x;}
	public void setDoubleNum(int x) {doubleNum = x;}
	public void setQuadNum(int x) {quadNum = x;}
	public void setSingleRoomArray(int[] rooms) {
		String res;
		String[] tmp = new String[rooms.length];
		for (int i = 0; i<tmp.length; i++) {
			tmp[i] = new String(String.valueOf(rooms[i]));
		}
		res = String.join("/", tmp);
		singleRoom = res;
	}
	public void setDoubleRoomArray(int[] rooms) {
		String res;
		String[] tmp = new String[rooms.length];
		for (int i = 0; i<tmp.length; i++) {
			tmp[i] = new String(String.valueOf(rooms[i]));
		}
		res = String.join("/", tmp);
		doubleRoom = res;
	}
	public void setQuadRoomArray(int[] rooms) {
		String res;
		String[] tmp = new String[rooms.length];
		for (int i = 0; i<tmp.length; i++) {
			tmp[i] = new String(String.valueOf(rooms[i]));
		}
		res = String.join("/", tmp);
		quadRoom = res;
	}


}

class SinglePriceComparator implements Comparator<Hotel> {
	public int compare(Hotel h1, Hotel h2) {
		return h1.getSinglePrice() - h2.getSinglePrice();
	}
}

class DoublePriceComparator implements Comparator<Hotel> {
	public int compare(Hotel h1, Hotel h2) {
		return h1.getDoublePrice() - h2.getDoublePrice();
	}
}

class QuadPriceComparator implements Comparator<Hotel> {
	public int compare(Hotel h1, Hotel h2) {
		return h1.getQuadPrice() - h2.getQuadPrice();
	}
}



