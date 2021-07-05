package cityBuilderViewer;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

class NetworkListener {
	public NetworkListener() {
		this.run();
	}

	public void run() {
		System.out.println("run");

		byte[] buffer = new byte[65508];
		
		try {
			InetAddress address = InetAddress.getByName("localhost");

			DatagramSocket ds = new DatagramSocket(1337);

			while (true) {			
				try {
					DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
					ds.receive(dp);
					String s = new String(dp.getData(), 0, 0, dp.getLength());

					System.out.println("<<<" + s);
				} catch (Exception e) {
					System.err.println(e);
				}      
			}
		 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
