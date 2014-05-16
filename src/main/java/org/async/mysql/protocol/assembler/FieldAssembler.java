package org.async.mysql.protocol.assembler;

import java.nio.ByteBuffer;

import org.async.mysql.Utils;
import org.async.mysql.protocol.PacketAssembler;
import org.async.mysql.protocol.packets.Field;

public class FieldAssembler implements PacketAssembler<Field>{

	public Field process(int step, ByteBuffer buffer, Field packet, Object message) {
		if(packet==null) {
			packet=new Field();
		}
		if(step==0) {
			packet.setCatalog(new String(buffer.array(),0,buffer.limit()));
		} else if(step==1) {
			packet.setDb(new String(buffer.array(),0,buffer.limit()));
		} else if(step==2) {
			packet.setTable(new String(buffer.array(),0,buffer.limit()));
		} else if(step==3) {
			packet.setOrgTable(new String(buffer.array(),0,buffer.limit()));
		} else if(step==4) {
			packet.setName(new String(buffer.array(),0,buffer.limit()));
		} else if(step==5) {
			packet.setOrgName(new String(buffer.array(),0,buffer.limit()));
		} else if(step==7) {
			packet.setCharsetNum((int) Utils.readLong(buffer.array(), 0, buffer.limit()));
		} else if(step==8) {
			packet.setLength((int) Utils.readLong(buffer.array(), 0, buffer.limit()));
		} else if(step==9) {
			packet.setType((int) Utils.readLong(buffer.array(), 0, buffer.limit()));
		} else if(step==10) {
			packet.setFlags((int) Utils.readLong(buffer.array(), 0, buffer.limit()));
		} else if(step==11) {
			packet.setDecimals((int) Utils.readLong(buffer.array(), 0, buffer.limit()));
		} else if(step==13) {
			packet.setDef((int) Utils.readLong(buffer.array(), 0, buffer.limit()));
		}
		return packet;
	}
//	n (Length Coded String)    catalog
//	 n (Length Coded String)    db
//	 n (Length Coded String)    table
//	 n (Length Coded String)    org_table
//	 n (Length Coded String)    name
//	 n (Length Coded String)    org_name
//	 1                          (filler)
//	 2                          charsetnr
//	 4                          length
//	 1                          type
//	 2                          flags
//	 1                          decimals
//	 2                          (filler), always 0x00
//	 n (Length Coded Binary)    default
}
