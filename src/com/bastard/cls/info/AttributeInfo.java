package com.bastard.cls.info;

import java.nio.ByteBuffer;

import com.bastard.cls.cpool.ConstantPool;
import com.bastard.cls.cpool.ConstantPoolEntry;
import com.bastard.cls.cpool.entry.UTF8StringEntry;
import com.bastard.cls.info.attribute.AbstractAttribute;
import com.bastard.cls.info.attribute.Attribute;
import com.bastard.util.Indent;

/**
 * Field or method attribute info.
 * @author Tommo
 *
 */
public class AttributeInfo implements Info {
	
	/**
	 * The attribute name index in the constant pool.
	 */
	private int nameIndex;
	private int length;
	private AbstractAttribute attribute;
	
	public AttributeInfo() {
		
	}

	@Override
	public AttributeInfo read(ConstantPool pool, ByteBuffer data) {
		nameIndex = data.getShort();
		length = data.getInt();
		ConstantPoolEntry cpe = pool.getEntries()[nameIndex];
		if (cpe instanceof UTF8StringEntry) {
			UTF8StringEntry entry = (UTF8StringEntry) cpe;
			Attribute attr = Attribute.getAttribute(entry.getString());
			if (attr == Attribute.NOT_IMPLEMENTED) {
				byte[] tmp = new byte[length];
				data.get(tmp);
			} else {
				try {
					this.attribute = attr.getAttributeClass().getConstructor(int.class, int.class).newInstance(nameIndex, length).read(pool, data);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			byte[] tmp = new byte[length];
			data.get(tmp);
		}
		return this;
	}
	
	@Override
	public void print(int indentations) {
		System.out.println(Indent.$(indentations) + "" + toString());
		if (attribute != null) {
			attribute.print(indentations + 1);
		} else {
			System.out.println(Indent.$(indentations + 1) + "UnimplementedAttribute[null]");
		}
	}
	
	public AbstractAttribute getAttribute() {
		return attribute;
	}
	
	@Override
	public String toString() {
		return "AttributeInfo[nameIdx=" + nameIndex + ", length=" + length + "]";
	}

}
