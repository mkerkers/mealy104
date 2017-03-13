/*
 * Copyright 2014-16 Fraunhofer ISE
 *
 * This file is part of j60870.
 * For more information visit http://www.openmuc.org
 *
 * j60870 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * j60870 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with j60870.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openmuc.j60870;

import org.openmuc.j60870.internal.ConnectionSettings;

import java.io.DataInputStream;
import java.io.IOException;

public class APdu {

	public enum APCI_TYPE {
		I_FORMAT,
		S_FORMAT,
		TESTFR_CON,
		TESTFR_ACT,
		STOPDT_CON,
		STOPDT_ACT,
		STARTDT_CON,
		STARTDT_ACT,
		L_MAX
	}

	private int sendSeqNum = 0;

	private int receiveSeqNum = 0;

	private APCI_TYPE apciType;

	private ASdu aSdu = null;

	public APdu(int sendSeqNum, int receiveSeqNum, APCI_TYPE apciType, ASdu aSdu) {
		this.sendSeqNum = sendSeqNum;
		this.receiveSeqNum = receiveSeqNum;
		this.apciType = apciType;
		this.aSdu = aSdu;
	}

	public APdu(DataInputStream is, ConnectionSettings settings) throws IOException {

		int length = is.readByte() & 0xff;

		if (length < 4 || length > 253) {
			throw new IOException("APDU contain invalid length: " + length);
		}

		byte[] aPduHeader = new byte[4];
		is.readFully(aPduHeader);

		if ((aPduHeader[0] & 0x01) == 0) {
			apciType = APCI_TYPE.I_FORMAT;
			sendSeqNum = ((aPduHeader[0] & 0xfe) >> 1) + ((aPduHeader[1] & 0xff) << 7);
			receiveSeqNum = ((aPduHeader[2] & 0xfe) >> 1) + ((aPduHeader[3] & 0xff) << 7);

			aSdu = new ASdu(is, settings, length - 4);
		}
		else if ((aPduHeader[0] & 0x02) == 0) {
			apciType = APCI_TYPE.S_FORMAT;
			receiveSeqNum = ((aPduHeader[2] & 0xfe) >> 1) + ((aPduHeader[3] & 0xff) << 7);
		}
		else {
			if (aPduHeader[0] == (byte) 0x83) {
				apciType = APCI_TYPE.TESTFR_CON;
			}
			else if (aPduHeader[0] == 0x43) {
				apciType = APCI_TYPE.TESTFR_ACT;
			}
			else if (aPduHeader[0] == 0x23) {
				apciType = APCI_TYPE.STOPDT_CON;
			}
			else if (aPduHeader[0] == 0x13) {
				apciType = APCI_TYPE.STOPDT_ACT;
			}
			else if (aPduHeader[0] == 0x0B) {
				apciType = APCI_TYPE.STARTDT_CON;
			}
			else {
				apciType = APCI_TYPE.STARTDT_ACT;
			}
		}

	}

	int encode(byte[] buffer, ConnectionSettings settings) throws IOException {

		buffer[0] = 0x68;

		int length = 4;

		if (apciType == APCI_TYPE.I_FORMAT) {
			buffer[2] = (byte) (sendSeqNum << 1);
			buffer[3] = (byte) (sendSeqNum >> 7);
			buffer[4] = (byte) (receiveSeqNum << 1);
			buffer[5] = (byte) (receiveSeqNum >> 7);
			length += aSdu.encode(buffer, 6, settings);
		}
		else if (apciType == APCI_TYPE.STARTDT_ACT) {
			buffer[2] = 0x07;
			buffer[3] = 0x00;
			buffer[4] = 0x00;
			buffer[5] = 0x00;
		}
		else if (apciType == APCI_TYPE.STARTDT_CON) {
			buffer[2] = 0x0b;
			buffer[3] = 0x00;
			buffer[4] = 0x00;
			buffer[5] = 0x00;
		}  // <=== Addition ===
		else if (apciType == APCI_TYPE.STOPDT_ACT) {
			buffer[2] = 0x13;
			buffer[3] = 0x00;
			buffer[4] = 0x00;
			buffer[5] = 0x00;
		}
		else if (apciType == APCI_TYPE.STOPDT_CON) {
			buffer[2] = 0x23;
			buffer[3] = 0x00;
			buffer[4] = 0x00;
			buffer[5] = 0x00;
		}
		else if (apciType == APCI_TYPE.TESTFR_ACT) {
			buffer[2] = 0x43;
			buffer[3] = 0x00;
			buffer[4] = 0x00;
			buffer[5] = 0x00;
		}
		else if (apciType == APCI_TYPE.TESTFR_CON) {
			buffer[2] = (byte)0x83;
			buffer[3] = 0x00;
			buffer[4] = 0x00;
			buffer[5] = 0x00;
		} // === Addition ===>
		else if (apciType == APCI_TYPE.S_FORMAT) {
			buffer[2] = 0x01;
			buffer[3] = 0x00;
			buffer[4] = (byte) (receiveSeqNum << 1);
			buffer[5] = (byte) (receiveSeqNum >> 7);
		}  // <=== Addition ===
		else if (apciType == APCI_TYPE.L_MAX) {
			buffer[1] = (byte) 253;
			return 2;
		} // === Addition ===>

		buffer[1] = (byte) length;

		return length + 2;

	}

	public APCI_TYPE getApciType() {
		return apciType;
	}

	public int getSendSeqNumber() {
		return sendSeqNum;
	}

	public void setSendSeqNum(int sendSeqNum) {
		this.sendSeqNum = sendSeqNum;
	}

	public int getReceiveSeqNumber() {
		return receiveSeqNum;
	}

	public void setReceiveSeqNum(int receiveSeqNum) {
		this.receiveSeqNum = receiveSeqNum;
	}

	public ASdu getASdu() {
		return aSdu;
	}

	@Override
	public String toString() {
		return super.toString() + ":" + getApciType().toString();
	}
}
