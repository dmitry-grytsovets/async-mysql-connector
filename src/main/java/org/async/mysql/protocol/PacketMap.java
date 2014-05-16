package org.async.mysql.protocol;

public class PacketMap<T extends Packet> {
	protected int[] sizes;
	protected PacketAssembler<T> assembler;

	public PacketMap(int[] sizes, PacketAssembler<T> assembler) {
		super();
		this.sizes = sizes;
		this.assembler = assembler;
	}

	public int getSize(int idx, Object message) {

		if (idx >= sizes.length && sizes[sizes.length - 1] == Protocol.REPEAT) {
			return sizes[sizes.length - 2];
		}
		return (sizes[idx] == -1) ? sizes[idx - 1] : sizes[idx];
	}

	public int size() {
		return sizes.length == 0 || sizes[sizes.length - 1] == -1 ? -1
				: sizes.length;
	}

	public void setSizes(int[] sizes) {
		this.sizes = sizes;
	}

	public PacketAssembler<T> getAssembler() {
		return assembler;
	}

	public void setAssembler(PacketAssembler<T> assembler) {
		this.assembler = assembler;
	}

}
