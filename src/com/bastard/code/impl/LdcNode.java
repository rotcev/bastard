package com.bastard.code.impl;

import com.bastard.cls.cpool.ConstantPool;
import com.bastard.cls.cpool.ConstantPoolEntry;
import com.bastard.cls.cpool.entry.DoubleEntry;
import com.bastard.cls.cpool.entry.FloatEntry;
import com.bastard.cls.cpool.entry.IntegerEntry;
import com.bastard.cls.cpool.entry.LongEntry;
import com.bastard.cls.cpool.entry.StringRefEntry;
import com.bastard.cls.cpool.entry.UTF8StringEntry;
import com.bastard.code.Node;
import com.bastard.instruction.impl.LdcInstruction;

/**
 * Represents an {@link LdcInstruction} as a node.
 * @author Shawn Davies<sodxeh@gmail.com>
 */
public class LdcNode extends Node {

	/**
	 * The constant of this ldc instruction.
	 */
	private Object constant;
	public LdcNode(ConstantPool pool, LdcInstruction instruction) {
		super(pool, instruction);
		this.constant = cacheConstant();
	}

	public Object cacheConstant() {
		ConstantPoolEntry entry = pool.getEntries()[((LdcInstruction)instruction).getConstantIndex()];

		if (entry instanceof UTF8StringEntry) {
			UTF8StringEntry ref = (UTF8StringEntry) entry;
			return ref.getString();
		}
		
		if (entry instanceof StringRefEntry) {
			StringRefEntry ref = (StringRefEntry) entry;
			UTF8StringEntry str = (UTF8StringEntry) pool.getEntries()[ref.getStringIndex()];
			return str.getString();
		}
		
		if (entry instanceof IntegerEntry) {
			return ((IntegerEntry)entry).getValue();
		}
		
		if (entry instanceof DoubleEntry) {
			return ((DoubleEntry)entry).getValue();
		}
		
		if (entry instanceof FloatEntry) {
			return ((FloatEntry)entry).getValue();
		}
		
		if (entry instanceof LongEntry) {
			return ((LongEntry)entry).getValue();
		}
		return null;
	}

	public Object getConstant() {
		return constant;
	}

	@Override
	public String code() {
		return "LdcNode[const="+constant+", children="+children.size()+"]";
	}
}
