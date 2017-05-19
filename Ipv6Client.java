// Omar Rodriguez
// CS 380
// Professor Nima Davarpanah

import java.io.*;
import java.net.*;
import java.util.*;

final class Ipv6Client {
	public static void main(String [] args) throws IOException {
		Socket socket = new Socket("codebank.xyz", 38004);
		System.out.println("connected to server");
		InputStream is = socket.getInputStream();
		OutputStream out = socket.getOutputStream();

		for(int i =0; i < 12; i++) {
			int dataLength = (int)Math.pow(2, i + 1);
			byte[] packet = new byte[dataLength + 40];

			System.out.println("data length:	"+ (dataLength));

			byte[] data = new byte[dataLength];
			new Random().nextBytes(data);

			packet[0] = (0b0110 << 4);
			packet[1] = 0x0;
			packet[2] = 0x0;
			packet[3] = 0x0;

			short temp = (short)(dataLength);
	  	byte second = (byte) ((temp >>> 8) & 0xFF);
			byte first = (byte)(temp & 0xFF);
			packet[4] = second;
			packet[5] = first;

			packet[6] = 0x11;

			packet[7] = 0x14;

			for(int k = 8; k < 18; k++) {
				packet[k] = 0b0;
			}

			packet[18] = (byte) 0xFF;
			packet[19] = (byte) 0xFF;
			packet[20] = 0b01111111;
			packet[21] = 0b0;
			packet[22] = 0b0;
			packet[23] = 0b01;

			for(int j = 24; j < 34; j++) {
				packet[j] = 0b0;
			}

			packet[34] = (byte) 0xFF;
			packet[35] = (byte) 0xFF;
			packet[36] = (byte)0b00110100;
			packet[37] = (byte)0b00100001;
			packet[38] = (byte)0xB5;
			packet[39] = (byte)0b1110010;

			for(int m = 40; m < packet.length; m++) {
				packet[m] = data[(m - 40)];
			}
			out.write(packet);
			System.out.print("Response: ");
			for (int l = 0; l < 4; l++) {

				System.out.printf("%x",is.read());
				System.out.println("\n");
			}
		}
	}
}
