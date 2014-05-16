package org.async.mysql.protocol.packets;

import org.async.mysql.protocol.Packet;
import org.async.mysql.protocol.Parser;

public class Handshake implements Packet {
	private int protocolVersion;
	private String serverVersion;
	private int threadId;
	private byte[] scramble = new byte[25];
	private int serverCapabilities;
	private byte serverLanguage;
	private int serverStatus;

	public int getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(int protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	public String getServerVersion() {
		return serverVersion;
	}

	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
	}

	public int getThreadId() {
		return threadId;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	public byte[] getScramble() {
		return scramble;
	}

	public void setScramble(byte[] scramble) {
		this.scramble = scramble;
	}

	public int getServerCapabilities() {
		return serverCapabilities;
	}

	public void setServerCapabilities(int serverCapabilities) {
		this.serverCapabilities = serverCapabilities;
	}

	public byte getServerLanguage() {
		return serverLanguage;
	}

	public void setServerLanguage(byte serverLanguage) {
		this.serverLanguage = serverLanguage;
	}

	public int getServerStatus() {
		return serverStatus;
	}

	public void setServerStatus(int serverStatus) {
		this.serverStatus = serverStatus;
	}

	public String getSeed() {
		return new String(scramble).trim();
	}

	public void onSuccess(Parser parser) {

	}


}
