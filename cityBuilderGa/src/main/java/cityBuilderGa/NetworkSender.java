package cityBuilderGa;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

class NetworkSender {
	private DatagramSocket ds;
	private InetAddress address;

	public NetworkSender() {
		try { 
			this.address = InetAddress.getByName("127.0.0.1");
			this.ds = new DatagramSocket(1337);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(String txt) {
		try {
			byte[] buf = txt.getBytes();
			DatagramPacket dp = new DatagramPacket(buf, buf.length, this.address, 1337);

			System.out.println("send: " + txt);
			this.ds.send(dp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
