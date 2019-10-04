import java.util.*;

public class BookingDominator {
    private static List<Hotel> hotels;
    private SqlManager sqlman;
    private Date today;
    private static int ordernumber;

    public BookingDominator(){
        sqlman = new SqlManager();
        hotels = sqlman.getAllHotel();
        today = new Date();
        Random ran = new Random();
        ordernumber = ran.nextInt(2019000000);

    }


    public int dateAfter(Date date){
        if(date.getMonth() == today.getMonth() + 1){
            return date.getDate() - today.getDate();
        }
        else if(date.getMonth() > today.getMonth() + 1){
            int m;
            switch (date.getMonth() + 1 ){
                case 1: case 3: case 5: case 7 : case 8: case 10: case 12: m = 31; break;
                case 4 : case 6 : case 9: case 11: m = 30; break;
                case 2 : m = 28; break;
                default: m = 0;
            }
            return date.getDate() - today.getDate() + m;
        }
        else return -1;

    }

    public Date stringToDate(String date){
        String[] datearray = date.split("/");
        int[] retdate = new int[datearray.length];

        for(int i = 0 ; i < datearray.length ; i++){
            retdate[i] = Integer.parseInt(datearray[i]);
        }
        int m;
        switch (today.getMonth() + 1){
            case 1: case 3: case 5: case 7 : case 8: case 10: case 12: m = 31; break;
            case 4 : case 6 : case 9: case 11: m = 30; break;
            case 2 : m = 28; break;
            default: m = 0;
        }
        if(retdate[0] != 2019) throw new IllegalArgumentException("Error Year Input, Please check :-D");
        if(retdate[1] > 13 || retdate[1] < today.getMonth() + 1 ) throw new IllegalArgumentException("Error Month Input, Please check :-D");
        if((retdate[1] == today.getMonth() + 1) && (retdate[2] > m || retdate[2] < today.getDate()))
            throw new IllegalArgumentException("Error Date Input, Please check :-D");
        Date ck = new Date (retdate[0], retdate[1], retdate[2]);
        return ck;
    }


    public List<Hotel> searchHotel(Date checkin, Date checkout, int roomtype, int roomnumber){
        List<Hotel> ret = new ArrayList<>();
        int ckin = dateAfter(checkin);
        //System.out.println("ckin" + ckin);
        int ckout = dateAfter(checkout);
        //System.out.println("ckout" + ckout);
        if(ckin >= ckout) return null;

        int m = 0;
        for(int i = 0 ; i < 1500 ; i++){
            Hotel h = hotels.get(i);
            if(h != null){
                if(checkEmpty(h, roomtype, roomnumber, ckin, ckout)){
                    ret.add(m++,h);
                }
            }
        }
        ret = sortPrice(ret,roomtype);

        return ret;
    }

    public boolean checkEmpty(Hotel h, int roomtype, int roomnumber, int ckin, int ckout){
        int[] s;
        switch(roomtype){
            case 1: {
                s = h.getSingleRoomArray();
                for (int i = ckin; i <= ckout; i++) {
                    if (s[i] < roomnumber)
                        return false;
                }
                return true;
            }
            case 2: {
                s = h.getDoubleRoomArray();
                for (int i = ckin; i <= ckout; i++) {
                    if (s[i] < roomnumber)
                        return false;
                }
                return true;
            }

            case 4: {
                s = h.getQuadRoomArray();
                for (int i = ckin; i <= ckout; i++) {
                    if (s[i] < roomnumber)
                        return false;
                }
                return true;
            }

        }

        return false;
    }

    public List<Hotel> filter(List<Hotel> hts, String column_lab, int wanted_num, int roomtype,
                              int roomnumber, int people, Date checkin, Date checkout){
        List<Hotel> ret = new ArrayList<>();
        int m = 0;
        int i = 0;
        while(m < 1500 && i < 1500){
            if(hotels.get(i) != null && hotels.get(i).getColumn(column_lab) != -1 &&
                    hotels.get(i).getColumn(column_lab) <= wanted_num){
                ret.add(m++,hotels.get(i++));
            }
            else i++;

        }
        return ret;
    }


    public List<Hotel> sortPrice(List<Hotel> hts, int roomtype){
        switch(roomtype){
            case 1 : Collections.sort(hts, new SinglePriceComparator()); break;
            case 2 : Collections.sort(hts, new DoublePriceComparator()); break;
            case 4 : Collections.sort(hts, new QuadPriceComparator()); break;
            default: throw new IllegalArgumentException(" 按照單人房的價位排序請輸入singleprice,\n" +
                    " 雙人房請輸入doubleprice,\n" +
                    " 四人房請輸入quadprice, 謝謝");
        }
        return hts;
    }

    public boolean roomReserve(String userid, int hotelid, Date checkin, Date checkout,
                               int roomtype, int roomnumber){
        int ckin = dateAfter(checkin);
        int ckout = dateAfter(checkout);
        String r_t = null;
        Hotel h = hotels.get(hotelid);
        int price;
        switch(roomtype){
            case 1: r_t = "singleroom"; price = h.getSinglePrice(); break;
            case 2: r_t = "doubleroom"; price = h.getDoublePrice(); break;
            case 4: r_t = "quadroom"; price = h.getQuadPrice(); break;
            default: throw new IllegalArgumentException("Error Input!\n Please Enter 1, 2, 4 in room type");
        }
        price *= roomnumber;

        if(checkEmpty(h, roomtype, roomnumber, ckin, ckout)){
            Order o = new Order(ordernumber, userid, hotelid, checkin, checkout, r_t, roomnumber);
            sqlman.addOrder(o);
            ordernumber ++;
            System.out.print("Order number is : "+ o.getOrderID() + "\n");
            System.out.println(o.toString());
            System.out.println("Total price is :" + price );
        }
        else {
            System.out.println("Error Input, Please check your hotel is reservable");
            return false;
        }

        int[] roomarray;
        switch(roomtype){
            case 1: {
                roomarray = h.getSingleRoomArray();
                for(int i = ckin ; i < ckout ; i++){
                    roomarray[i] -= roomnumber;
                }
                h.setSingleRoomArray(roomarray);
                break;
            }
            case 2: {
                roomarray = h.getDoubleRoomArray();
                for(int i = ckin ; i < ckout ; i++){
                    roomarray[i] -= roomnumber;
                }
                h.setDoubleRoomArray(roomarray);
                break;
            }
            case 4: {
                roomarray = h.getQuadRoomArray();
                for(int i = ckin ; i < ckout ; i++){
                    roomarray[i] -= roomnumber;
                }
                h.setQuadRoomArray(roomarray);
                break;
            }
        }
        sqlman.setHotel(hotelid, h);

        return true;
    }

    public boolean cancelReservation(String userid, int orderid){
        try{
            Order o = sqlman.getOrder(orderid, userid);
            Hotel h = hotels.get(o.getHotelID());
            int roomnumber = o.getRoomNum();
            int ckin = dateAfter(o.getCheckIn());
            int ckout = dateAfter(o.getCheckOut());
            int[] roomarray;
            switch(o.getRoomType()){
                case "singleroom": {
                    roomarray = h.getSingleRoomArray();
                    for(int i = ckin ; i < ckout ; i++){
                        roomarray[i] += roomnumber;
                    }
                    h.setSingleRoomArray(roomarray);
                    break;
                }
                case "doubleroom": {
                    roomarray = h.getDoubleRoomArray();
                    for(int i = ckin ; i < ckout ; i++){
                        roomarray[i] += roomnumber;
                    }
                    h.setDoubleRoomArray(roomarray);
                    break;
                }
                case "quadroom": {
                    roomarray = h.getQuadRoomArray();
                    for(int i = ckin ; i < ckout ; i++){
                        roomarray[i] += roomnumber;
                    }
                    h.setQuadRoomArray(roomarray);
                    break;
                }
                default: throw new IllegalArgumentException("Error Input!\n Please Enter 1, 2, 4 in room type");
            }
            sqlman.deleteOrder(o.getOrderID(),o.getUserID());
        }catch(IllegalArgumentException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return true;
    }

    public boolean modifyReservation(String userid, int orderid, int decrease, Date checkin, Date checkout){
        try{
            Order o = sqlman.getOrder(orderid, userid);
            int roomNum = o.getRoomNum();
            roomNum -= decrease;
            int ckin = dateAfter(checkin);
            int ckout = dateAfter(checkout);
            int rmtype = 0;
            int price = 0;
            Hotel h = hotels.get(o.getHotelID());
            switch(o.getRoomType()){
                case "singleroom" : rmtype = 1; price = h.getSinglePrice(); break;
                case "doubleroom" : rmtype = 2; price = h.getDoublePrice(); break;
                case "quadroom" : rmtype = 4; price = h.getQuadPrice();break;
            }
            price = (o.getRoomNum()-decrease) * price;

            if(checkEmpty(hotels.get(o.getHotelID()),rmtype, roomNum, ckin, ckout)){
                sqlman.deleteOrder(o.getOrderID(),userid);
                Order m = new Order(orderid, userid, o.getHotelID(), checkin, checkout, o.getRoomType(), roomNum);
                sqlman.addOrder(m);
                System.out.println("Total price is ;" + price);
            }

        }catch(IllegalArgumentException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return true;
    }

    public boolean modifyReservation(String userid, int orderid, int decrease){
        try{
            Order o = sqlman.getOrder(orderid, userid);
            int roomNum = o.getRoomNum();
            roomNum -= decrease;
            int ckin = dateAfter(o.getCheckIn());
            int ckout = dateAfter(o.getCheckOut());
            int rmtype = 0;
            int price = 0;
            Hotel h = hotels.get(o.getHotelID());
            switch(o.getRoomType()){
                case "singleroom" : rmtype = 1; price = h.getSinglePrice(); break;
                case "doubleroom" : rmtype = 2; price = h.getDoublePrice(); break;
                case "quadroom" : rmtype = 4; price = h.getQuadPrice();break;
            }
            price = (o.getRoomNum()-decrease) * price;

            if(checkEmpty(hotels.get(o.getHotelID()),rmtype, roomNum, ckin, ckout)){
                sqlman.deleteOrder(o.getOrderID(),userid);
                Order m = new Order(orderid, userid, o.getHotelID(), o.getCheckIn(), o.getCheckOut(), o.getRoomType(), roomNum);
                sqlman.addOrder(m);
                System.out.println("Total price is ;" + price);
            }

        }catch(IllegalArgumentException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return true;
    }

    
    public String getReservationInfo(String userid, int orderid){
        Order o = sqlman.getOrder(orderid, userid);
        int price = 0;

        Hotel h = hotels.get(o.getHotelID());
        switch(o.getRoomType()){
            case "singleroom" : price = h.getSinglePrice(); break;
            case "doubleroom" : price = h.getDoublePrice(); break;
            case "quadroom" : price = h.getQuadPrice();break;
        }

        price = o.getRoomNum() * price;

        String s = hotels.get(o.getHotelID()).toString();


        return o.toString() + s  + "\nTotal price is :" + price;
    }

    public static void main(String[] args) {
        BookingDominator bd = new BookingDominator();

        Date ckin = new Date(2019,6,23);
        Date ckout = new Date(2019,7,1);
        List<Hotel> h = bd.searchHotel(ckin,ckout,1,25);
        for(int i = 0 ; i < h.size();i++){
            System.out.println(h.get(i));
        }

        if(bd.roomReserve("chiu", 1459, ckin, ckout, 1, 25 )){
            int[] arra= hotels.get(1459).getSingleRoomArray();
            for(int i = 0 ; i < arra.length ; i ++)
                System.out.print(arra[i]+" ");
        }

        System.out.println("\n------------------------------------");
        if(bd.roomReserve("chiu", 1455, ckin, ckout, 4, 3 )){
            int[] arra= hotels.get(1455).getQuadRoomArray();
            for(int i = 0 ; i < arra.length ; i ++)
                System.out.print(arra[i]+" ");
        }
        else System.out.println("Reserve Failed");
        System.out.println();

        //hotels = bd.filter(hotels,"hotelstar", 2);
        //hotels = bd.sortPrice(hotels, "singleprice");
        hotels.sort(Comparator.<Hotel, Integer>comparing(p -> p.getSinglePrice()));
        //for(int i = 0; i < hotels.size();i++)
        //System.out.println(hotels.get(i));

        //hotels = bd.filter(hotels,"doubleprice", 1222);
        //System.out.println("----------------------------------------");
        //for(int  i = 0 ; i < 1500 ; i ++){
        //    if(hotels[i] == null) continue;
        //    System.out.print(hotels[i]);
        //    System.out.println("----------------------------------------");
        //}


        //hotel.setLocality("彰化");
        //hotel.setSinglePrice(8765439);
        //hotel.setSingleNum(-29);
        //m.setHotel(hotel.getHotelID(), hotel);
        //Hotel hotel2 = m.getHotel(0);
        //System.out.print(hotel2);


    }
}
