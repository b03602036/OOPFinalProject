import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.*;
import java.util.List;

public class GUI extends JFrame {

    public GUI() {
        UI();
    }

    private void UI() {
		JFrame j = new JFrame();

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JPanel panel6 = new JPanel();
		JTextArea area1=new JTextArea(100,100);
		JScrollPane scrollpanel1 = new JScrollPane(area1);  
		panel1.setBounds(300, 150, 400, 360);
		panel1.setBackground(Color.white);
		panel2.setBounds(300, 150, 400, 360);
		panel2.setBackground(Color.white);
		panel3.setBounds(300, 150, 400, 360);
		panel3.setBackground(Color.white);
		panel4.setBounds(300, 150, 400, 360);
		panel4.setBackground(Color.WHITE);
		panel5.setBounds(300, 150, 400, 360);
		panel5.setBackground(Color.WHITE);
		panel6.setBounds(300, 150, 400, 360);
		panel6.setBackground(Color.WHITE);
		area1.setFont(new Font("微軟正黑體", Font.BOLD, 22));
		area1.setEditable(false);
		scrollpanel1.setBounds(750,150,400,360);
		scrollpanel1.setBackground(Color.WHITE);  
        scrollpanel1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
		panel1.setVisible(true);
		panel2.setVisible(false);
		panel3.setVisible(false);
		panel4.setVisible(false);
		panel5.setVisible(false);
		panel6.setVisible(false);
		scrollpanel1.setVisible(true);
        BookingDominator bd = new BookingDominator();
		
        //output work
        PrintStream outStream = new PrintStream( new TextAreaOutputStream(area1));
        area1.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));

        System.setOut( outStream );
        System.setErr( outStream );
        
		// panel1
		JLabel l11 = new JLabel("入住日期 :");
		l11.setBounds(50, 20, 120, 40);
		l11.setOpaque(true);
		l11.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		JTextField t11 = new JTextField();
		t11.setBounds(200, 20, 150, 40);
		t11.setBackground(Color.WHITE);
		t11.setFont(new Font("微軟正黑體", Font.BOLD, 22));

		JLabel l12 = new JLabel("退房日期 :");
		l12.setBounds(50, 70, 120, 40);
		l12.setOpaque(true);
		l12.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		JTextField t12 = new JTextField();
		t12.setBounds(200, 70, 150, 40);
		t12.setBackground(Color.WHITE);
		t12.setFont(new Font("微軟正黑體", Font.BOLD, 22));

		JLabel l13 = new JLabel("入住房型 :");
		l13.setBounds(50, 120, 120, 40);
		l13.setOpaque(true);
		l13.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		String[] x1= {"-","quadroom","doubleroom","singleroom"};
		JComboBox c11 = new JComboBox(x1);
		c11.setBounds(200, 130, 180, 30);
		c11.setFont(new Font("微軟正黑體", Font.BOLD, 22));

		JLabel l14 = new JLabel("客房數量 :");
		l14.setBounds(50, 170, 120, 40);
		l14.setOpaque(true);
		l14.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		JTextField t14 = new JTextField();
		t14.setBounds(200, 170, 150, 40);
		t14.setBackground(Color.WHITE);
		t14.setFont(new Font("微軟正黑體", Font.BOLD, 22));

		JButton b11 = new JButton("搜尋");
		b11.setBounds(230, 260, 100, 30);
		b11.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		b11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try{
				area1.setText("");
				String datein = t11.getText();
				String dateout = t12.getText();
				String roomtype = (String)(c11.getSelectedItem());
				String roomnumber = t14.getText();
				Date ckin;
				Date ckout;
					if(datein.equals("") || dateout.equals("") || roomtype.equals("") || roomnumber.equals(""))
						throw new NumberFormatException();
					ckin = bd.stringToDate(datein);
					ckout = bd.stringToDate(dateout);
					int rmtype = 0;
					switch (roomtype){
						case "singleroom": rmtype = 1; break;
						case "doubleroom": rmtype = 2; break;
						case "quadroom": rmtype = 4; break;
					}
					int rmnumber = Integer.parseInt(roomnumber);
					List<Hotel> h;
					h = bd.searchHotel(ckin, ckout, rmtype, rmnumber);

					for(int i = 0 ; i< h.size();i++){
						area1.append((h.get(i).toString(rmtype)));
						area1.append("-----------------------------------\n");
					}
				}catch(NumberFormatException e ) {
					System.out.print("Detect Empty Input, Please check!");
				}catch(IllegalArgumentException e){
					System.out.print(e.getMessage());
				}catch (NullPointerException e){
					System.out.println("**********************************************************");
					System.out.println("** Invalid userID or Order ID, Please contact the staff **");
					System.out.println("**********************************************************");
				}


			}
		});

		panel1.add(t11);
		panel1.add(l11);
		panel1.add(t12);
		panel1.add(l12);
		panel1.add(c11);
		panel1.add(l13);
		panel1.add(t14);
		panel1.add(l14);
		panel1.add(b11);
		panel1.setLayout(null);

		// panel2
		JLabel l21 = new JLabel("使用者ID:");
		l21.setBounds(50, 20, 120, 30);
		l21.setOpaque(true);
		l21.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		JTextField t21 = new JTextField();
		t21.setBounds(200, 20, 150, 30);
		t21.setBackground(Color.WHITE);
		t21.setFont(new Font("微軟正黑體", Font.BOLD, 22));

		JLabel l22 = new JLabel("旅館編號 :");
		l22.setBounds(50, 70, 120, 30);
		l22.setOpaque(true);
		l22.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		JTextField t22 = new JTextField();
		t22.setBounds(200, 70, 150, 30);
		t22.setBackground(Color.WHITE);
		t22.setFont(new Font("微軟正黑體", Font.BOLD, 22));

		JLabel l23 = new JLabel("入住日期 :");
		l23.setBounds(50, 120, 120, 30);
		l23.setOpaque(true);
		l23.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		JTextField t23 = new JTextField();
		t23.setBounds(200, 120, 150, 30);
		t23.setBackground(Color.WHITE);
		t23.setFont(new Font("微軟正黑體", Font.BOLD, 22));

		JLabel l24 = new JLabel("退房日期 :");
		l24.setBounds(50, 170, 120, 30);
		l24.setOpaque(true);
		l24.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		JTextField t24 = new JTextField();
		t24.setBounds(200, 170, 150, 30);
		t24.setBackground(Color.WHITE);
		t24.setFont(new Font("微軟正黑體", Font.BOLD, 22));

		JLabel l25 = new JLabel("入住房型 :");
		l25.setBounds(50, 220, 120, 30);
		l25.setOpaque(true);
		l25.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		String[] x4= {"-","quadroom","doubleroom","singleroom"};
		JComboBox c21 = new JComboBox(x1);
		c21.setBounds(200, 220, 180, 30);
		c21.setFont(new Font("微軟正黑體", Font.BOLD, 22));

		JLabel l26 = new JLabel("客房數量 :");
		l26.setBounds(50, 270, 120, 30);
		l26.setOpaque(true);
		l26.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		JTextField t26 = new JTextField();
		t26.setBounds(200, 270, 150, 30);
		t26.setBackground(Color.WHITE);
		t26.setFont(new Font("微軟正黑體", Font.BOLD, 22));

		JButton b21 = new JButton("搜尋");
		b21.setBounds(230, 320, 100, 30);
		b21.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		b21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try{
				area1.setText("");
				String userid = t21.getText();
				String hotel = t22.getText();
				String datein = t23.getText();
				String dateout = t24.getText();
				String roomtype = (String) c21.getSelectedItem();
				String roomnumber = t26.getText();
				if(userid.equals("")|| hotel.equals("") || datein.equals("") || dateout.equals("") || roomtype.equals("") || roomnumber.equals(""))
						throw new NumberFormatException();
				Date ckin;
				Date ckout;

					ckin = bd.stringToDate(datein);
					ckout = bd.stringToDate(dateout);
					int rmtype = 0;
					switch (roomtype){
						case "singleroom": rmtype = 1; break;
						case "doubleroom": rmtype = 2; break;
						case "quadroom": rmtype = 4; break;
						}
						int hotelid = Integer.parseInt(hotel);
						int rmnumber = Integer.parseInt(roomnumber);

						if (bd.roomReserve(userid, hotelid, ckin, ckout, rmtype, rmnumber)){
							area1.append("  Reservation is done!!! Thank You!");
						}

				}
				catch(NumberFormatException e ) {
					System.out.print("Detect Empty Input, Please check!");
				}catch(IllegalArgumentException e){
					System.out.print(e.getMessage());
				}
				catch (NullPointerException e){
					System.out.println("**********************************************************");
					System.out.println("** Invalid userID or Order ID, Please contact the staff **");
					System.out.println("**********************************************************");
				}

			}
		});

		panel2.add(t21);
		panel2.add(l21);
		panel2.add(t22);
		panel2.add(l22);
		panel2.add(t23);
		panel2.add(l23);
		panel2.add(t24);
		panel2.add(l24);
		panel2.add(c21);
		panel2.add(l25);
		panel2.add(t26);
		panel2.add(l26);
		panel2.add(b21);
		panel2.setLayout(null);
		// panel3

		JButton b31 = new JButton("取消訂單");
		b31.setBounds(120, 80, 170, 80);
		b31.setFont(new Font("微軟正黑體", Font.BOLD, 30));
		b31.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				panel1.setVisible(false);
				panel2.setVisible(false);
				panel3.setVisible(false);
				panel4.setVisible(false);
				panel5.setVisible(true);
				panel6.setVisible(false);
			}
		});

		JButton b32 = new JButton("修改訂單");
		b32.setBounds(120, 180, 170, 80);
		b32.setFont(new Font("微軟正黑體", Font.BOLD, 30));
		b32.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				panel1.setVisible(false);
				panel2.setVisible(false);
				panel3.setVisible(false);
				panel4.setVisible(false);
				panel5.setVisible(false);
				panel6.setVisible(true);
			}
		});

		panel3.add(b31);
		panel3.add(b32);
		panel3.setLayout(null);
		
		// panel4
		JLabel l41 = new JLabel("使用者ID:");
		l41.setBounds(50, 20, 120, 40);
		l41.setOpaque(true);
		l41.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		JTextField t41 = new JTextField();
		t41.setBounds(200, 20, 150, 40);
		t41.setBackground(Color.WHITE);
		t41.setFont(new Font("微軟正黑體", Font.BOLD, 22));

		JLabel l42 = new JLabel("訂位代碼 :");
		l42.setBounds(50, 70, 120, 40);
		l42.setOpaque(true);
		l42.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		JTextField t42 = new JTextField();
		t42.setBounds(200, 70, 150, 40);
		t42.setBackground(Color.WHITE);
		t42.setFont(new Font("微軟正黑體", Font.BOLD, 22));

		JButton b41 = new JButton("搜尋");
		b41.setBounds(230, 180, 100, 30);
		b41.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		b41.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try{
					area1.setText("");
					String userid = t41.getText();
					String orderid = t42.getText();
					if(userid.equals("")|| orderid.equals(""))
						throw new NumberFormatException();

					int oid = Integer.parseInt(orderid);
					area1.append(bd.getReservationInfo(userid, oid));
				}catch (NullPointerException e){
					System.out.println("**********************************************************");
					System.out.println("** Invalid userID or Order ID, Please contact the staff **");
					System.out.println("**********************************************************");
				}
				catch(NumberFormatException e ) {
					System.out.print("Detect Empty Input, Please check!");
				}
			}
		});

		panel4.add(t41);
		panel4.add(l41);
		panel4.add(t42);
		panel4.add(l42);
		panel4.add(b41);
		panel4.setLayout(null);

		// panel5
		JLabel l51 = new JLabel("使用者ID:");
		l51.setBounds(50, 20, 120, 30);
		l51.setOpaque(true);
		l51.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		JTextField t51 = new JTextField();
		t51.setBounds(200, 20, 150, 30);
		t51.setBackground(Color.WHITE);
		t51.setFont(new Font("微軟正黑體", Font.BOLD, 22));

		JLabel l52 = new JLabel("訂位代碼 :");
		l52.setBounds(50, 70, 120, 30);
		l52.setOpaque(true);
		l52.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		JTextField t52 = new JTextField();
		t52.setBounds(200, 70, 150, 30);
		t52.setBackground(Color.WHITE);
		t52.setFont(new Font("微軟正黑體", Font.BOLD, 22));

		JButton b51 = new JButton("取消訂單");
		b51.setBounds(140, 200, 140, 30);
		b51.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		b51.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try{
					area1.setText("");
					String userid = t51.getText();
					String orderid = t52.getText();
					if(userid.equals("")|| orderid.equals(""))
						throw new NumberFormatException();
					int oid = Integer.parseInt(orderid);
					if(bd.cancelReservation(userid, oid)){
						area1.append("成功取消, 這次不住以後也別來了");
					}
				}
				catch(NumberFormatException e ){
					System.out.println("**********************************************************");
					System.out.println("** Invalid userID or Order ID, Please contact the staff **");
					System.out.println("**********************************************************");
				} catch(IllegalArgumentException e){
					System.out.print(e.getMessage());
				}
				catch (NullPointerException e){
					System.out.println("**********************************************************");
					System.out.println("** Invalid userID or Order ID, Please contact the staff **");
					System.out.println("**********************************************************");
				}

			}
		});
		
		panel5.add(t51);
		panel5.add(l51);
		panel5.add(t52);
		panel5.add(l52);
		panel5.add(b51);
		panel5.setLayout(null);

		// panel6
		JLabel l61 = new JLabel("使用者ID:");
		l61.setBounds(50, 20, 120, 30);
		l61.setOpaque(true);
		l61.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		JTextField t61 = new JTextField();
		t61.setBounds(200, 20, 150, 30);
		t61.setBackground(Color.WHITE);
		t61.setFont(new Font("微軟正黑體", Font.BOLD, 22));

		JLabel l62 = new JLabel("訂位代碼 :");
		l62.setBounds(50, 70, 120, 30);
		l62.setOpaque(true);
		l62.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		JTextField t62 = new JTextField();
		t62.setBounds(200, 70, 150, 30);
		t62.setBackground(Color.WHITE);
		t62.setFont(new Font("微軟正黑體", Font.BOLD, 22));
		
		JLabel l63 = new JLabel("減少 :");
		l63.setBounds(50, 120, 70, 30);
		l63.setOpaque(true);
		l63.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		
		String[] x3= {"-","1","2","3","4","5","6"};
		JComboBox c62 = new JComboBox(x3);
		c62.setBounds(280, 120, 100, 30);
		c62.setFont(new Font("微軟正黑體", Font.BOLD, 22));
		
		JLabel l64 = new JLabel("變更日期:");
		l64.setBounds(50, 170, 95, 30);
		l64.setOpaque(true);
		l64.setFont(new Font("微軟正黑體", Font.BOLD, 22));
		
		JLabel l65 = new JLabel("入住日期:");
		l65.setBounds(150, 170, 95, 30);
		l65.setOpaque(true);
		l65.setFont(new Font("微軟正黑體", Font.BOLD, 22));
		JTextField t63 = new JTextField();
		t63.setBounds(260, 170, 95, 30);
		t63.setBackground(Color.WHITE);
		t63.setFont(new Font("微軟正黑體", Font.BOLD, 22));
		
		JLabel l66 = new JLabel("退房日期:");
		l66.setBounds(150, 220, 95, 30);
		l66.setOpaque(true);
		l66.setFont(new Font("微軟正黑體", Font.BOLD, 22));
		JTextField t64 = new JTextField();
		t64.setBounds(260, 220, 95, 30);
		t64.setBackground(Color.WHITE);
		t64.setFont(new Font("微軟正黑體", Font.BOLD, 22));

		JButton b61 = new JButton("修改訂單");
		b61.setBounds(140, 280, 140, 30);
		b61.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		b61.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				try{ area1.setText("");

				String userid = t61.getText();
				String orderid = t62.getText();

				int oid = Integer.parseInt(orderid);
				String decrease = (String)c62.getSelectedItem();
				int dec;
				if (decrease.equals("-")) dec = 0;
				else dec = Integer.parseInt(decrease);

				String datein = t63.getText();
				String dateout = t64.getText();
				Date ckin;
				Date ckout;


					if(!datein.equals("") && !dateout.equals("")){
						ckin = bd.stringToDate(datein);
						ckout = bd.stringToDate(dateout);
						if(bd.modifyReservation(userid, oid, dec, ckin, ckout)){
							area1.append("修改好了");
						}
					}else if(bd.modifyReservation(userid, oid, dec));
					area1.append("修改好了");

				} catch(NumberFormatException e ){
					System.out.println("**********************************************************");
					System.out.println("** Invalid userID or Order ID, Please contact the staff **");
					System.out.println("**********************************************************");
				} catch(IllegalArgumentException e){
					System.out.print(e.getMessage());
				}
				catch (NullPointerException e){
					System.out.println("**********************************************************");
					System.out.println("** Invalid userID or Order ID, Please contact the staff **");
					System.out.println("**********************************************************");
				}
			}
		});

		panel6.add(t61);
		panel6.add(l61);
		panel6.add(t62);
		panel6.add(l62);
		panel6.add(c62);
		panel6.add(l63);
		panel6.add(l64);
		panel6.add(l65);
		panel6.add(l66);
		panel6.add(t63);
		panel6.add(t64);
		panel6.add(b61);
		panel6.setLayout(null);

		// 菜單

		JLabel label = new JLabel("TopBook");
		label.setBounds(50, 60, 220, 100);
		label.setOpaque(true);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("SansSerif", Font.BOLD, 50));

		JButton searchButton = new JButton("查詢旅館");
		searchButton.setBounds(70, 200, 150, 60);
		searchButton.setBackground(Color.gray);
		searchButton.setFont(new Font("微軟正黑體", Font.TYPE1_FONT, 22));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				panel1.setVisible(true);
				panel2.setVisible(false);
				panel3.setVisible(false);
				panel4.setVisible(false);
				panel5.setVisible(false);
				panel6.setVisible(false);
			}
		});

		JButton bookButton = new JButton("住宿預訂");
		bookButton.setBounds(70, 270, 150, 60);
		bookButton.setBackground(Color.gray);
		bookButton.setFont(new Font("微軟正黑體", Font.TYPE1_FONT, 22));
		bookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				panel1.setVisible(false);
				panel2.setVisible(true);
				panel3.setVisible(false);
				panel4.setVisible(false);
				panel5.setVisible(false);
				panel6.setVisible(false);
			}
		});

		JButton fixButton = new JButton("退訂與修改");
		fixButton.setBounds(70, 350, 150, 60);
		fixButton.setBackground(Color.gray);
		fixButton.setFont(new Font("微軟正黑體", Font.TYPE1_FONT, 22));
		fixButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				panel1.setVisible(false);
				panel2.setVisible(false);
				panel3.setVisible(true);
				panel4.setVisible(false);
				panel5.setVisible(false);
				panel6.setVisible(false);
			}
		});

		JButton assureButton = new JButton("查詢訂房");
		assureButton.setBounds(70, 430, 150, 60);
		assureButton.setBackground(Color.gray);
		assureButton.setFont(new Font("微軟正黑體", Font.TYPE1_FONT, 22));
		assureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				panel1.setVisible(false);
				panel2.setVisible(false);
				panel3.setVisible(false);
				panel4.setVisible(true);
				panel5.setVisible(false);
				panel6.setVisible(false);
			}
		});

		j.add(panel1);
		j.add(panel2);
		j.add(panel3);
		j.add(panel4);
		j.add(panel5);
		j.add(panel6);
		j.add(scrollpanel1);
		j.add(label);
		j.add(searchButton);
		j.add(bookButton);
		j.add(fixButton);
		j.add(assureButton);
		j.setTitle("Hotel");
		j.setLayout(null);
		j.setSize(1200, 600);
		j.setLocationRelativeTo(null);
		j.setDefaultCloseOperation(EXIT_ON_CLOSE);
		j.setVisible(true);
	}

    public static void main(String[] args) {
        GUI ex = new GUI();
    }
}