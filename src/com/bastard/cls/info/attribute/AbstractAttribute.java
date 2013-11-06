package com.bastard.cls.info.attribute;

import java.nio.ByteBuffer;

import com.bastard.cls.cpool.ConstantPool;

public abstract class AbstractAttribute {
	
	private int nameIndex;
	private int length;
	
	public AbstractAttribute(int nameIndex, int length) {
		this.nameIndex = nameIndex;
		this.length = length;
	}
	
	public abstract AbstractAttribute read(ConstantPool pool, ByteBuffer data);

	public int getNameIndex() {
		return nameIndex;
	}

	public int getLength() {
		return length;
	}

}
