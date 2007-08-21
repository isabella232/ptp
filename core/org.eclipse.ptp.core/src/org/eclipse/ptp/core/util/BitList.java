/*******************************************************************************
 * Copyright (c) 2005 The Regents of the University of California. 
 * This material was produced under U.S. Government contract W-7405-ENG-36 
 * for Los Alamos National Laboratory, which is operated by the University 
 * of California for the U.S. Department of Energy. The U.S. Government has 
 * rights to use, reproduce, and distribute this software. NEITHER THE 
 * GOVERNMENT NOR THE UNIVERSITY MAKES ANY WARRANTY, EXPRESS OR IMPLIED, OR 
 * ASSUMES ANY LIABILITY FOR THE USE OF THIS SOFTWARE. If software is modified 
 * to produce derivative works, such modified software should be clearly marked, 
 * so as not to confuse it with the version available from LANL.
 * 
 * Additionally, this program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * LA-CC 04-115
 *******************************************************************************/

package org.eclipse.ptp.core.util;

public class BitList {
	private byte[]	bits;
	private int		nBits;
	private int		nBytes;
	/** Cache the hash code for the BitList */
	private int hash = 0;

	private void init(int nbits) throws NegativeArraySizeException {
		if (nbits < 0) 
			throw new NegativeArraySizeException();
		
		if (nbits == 0) {
			this.nBits = 0;
			this.nBytes = 0;
		} else {
			this.nBits = nbits;
			this.nBytes = ((nbits-1) >> 3) + 1;
			this.bits = new byte[this.nBytes];
		}
	}
	
	public BitList(int nbits, String str) {
		this.init(nbits);
		this.fromString(str);
	}
	
	public BitList(int nbits) {
		this.init(nbits);
	}
	
	private BitList(int nbits, byte[] bits) {
		this.init(nbits);
		for (int i = 0; i < this.nBytes; i++)
			this.bits[i] = bits[i];
	}
	
	public int size() {
		return this.nBits;
	}
	
	private int countBits(byte b) {
		int n = 0;
		
		while (b != 0) {
			n++;
			b &= (b-1);
		}
		
		return n;
	}
	
	public int cardinality() {
		int n = 0;
		
		for (int i = 0; i < this.nBytes; i++) {
			n += countBits(this.bits[i]);
		}
		
		return n;
	}
	
	public void clear(int index) throws IndexOutOfBoundsException {
		if (index >= this.nBits || index < 0)
			throw new IndexOutOfBoundsException();
		
		int b = bytePos(index);
		this.bits[b] &= ~(byte)(1 << bitInByte(index));
	}

	public void clear(int[] indexs) {
		for (int i = 0; i < indexs.length; i++)
			this.clear(indexs[i]);
	}
	
	public void set(int index) throws IndexOutOfBoundsException {
		if (index >= this.nBits || index < 0)
			throw new IndexOutOfBoundsException();

		int b = bytePos(index);
		this.bits[b] |= (byte)(1 << bitInByte(index));
	}

	public void set(int[] indexs) {
		for (int i = 0; i < indexs.length; i++)
			this.set(indexs[i]);
	}
	
	public void set(int from, int to) {
		if (from >= this.nBits || from < 0 || to > this.nBits || from >= to)
			throw new IndexOutOfBoundsException();
		
		int last = to - 1; 
		
		/*
		 * Deal with single bit case
		 */
		if (from == last) {
			this.set(from);
			return;
		}
		
		int p1 = bytePos(from);
		int p2 = bytePos(last);
		int b1 = bitInByte(from);
		int b2 = bitInByte(last);
		
		/*
		 * Need to do something special if 'from' and 'to' refer
		 * to a single byte
		 */
		if (p1 == p2) {
			byte mask = (byte) (0xff >> (7 - b2));
			this.bits[p1] = (byte) (mask & (0xff << b1));
			return;
		}
		
		byte hiMask =  (byte) (0xff >> (7 - b2));
		byte lowMask = (byte) (0xff << b1);
		
		for (int p = p1+1; p < p2; p++)
			this.bits[p] = (byte) 0xff;
		
		this.bits[p1] |= lowMask;
		this.bits[p2] |= hiMask;
	}
	
	public boolean get(int index) throws IndexOutOfBoundsException {
		if (index >= this.nBits || index < 0)
			throw new IndexOutOfBoundsException();

		int b = bytePos(index);
		byte mask = (byte)(1 << bitInByte(index));
		return (this.bits[b] & mask) == mask;
	}
	
	public int nextSetBit(int index) {
		if (index < 0)
			throw new IndexOutOfBoundsException();

		if (index >= this.nBits)
			return -1;
		
		int i = index;
		int start = bitInByte(index);
		
		for (int p = bytePos(index); p < this.nBytes; p++) {
			byte val = this.bits[p];
			if (val != 0) {
				for (int b = start; b < 8; b++, i++) {
					byte mask = (byte) (1 << b);
					if ((val & mask) == mask)
						return i;
				}
			} else
				i += 8;
			
			start = 0;
		}
		
		return -1;
	}
	
	public int[] toArray() {
		int[] retValue = new int[this.cardinality()];
		for(int i = this.nextSetBit(0), j = 0; i >= 0; i = this.nextSetBit(i+1), j++) {
			retValue[j] = i;
		}
		return retValue;
	}

	public BitList copy() {
		return new BitList(this.nBits, this.bits);
	}
	
	public Object clone() {
		return (Object)this.copy();
	}
	
	/*
	 * If sets are different lengths, we assume missing bits are zero
	 */
	public void and(BitList s) {
		for (int i = 0; i < this.nBytes; i++) {
			if (i >= s.nBytes)
				this.bits[i] = 0;
			else
				this.bits[i] &= s.bits[i];
		}
	}
	
	public void andNot(BitList s) {
		for (int i = 0; i < this.nBytes; i++) {
			if (i < s.nBytes)
				this.bits[i] &= ~s.bits[i];
		}
	}
	
	public boolean intersects(BitList s) {
		for (int i = 0; i < this.nBytes; i++) {
			if (i >= s.nBytes)
				break;

			if ((this.bits[i] & s.bits[i]) != 0)
				return true;
		}
		
		return false;
	}
	
	public boolean isEmpty() {
		boolean res = true;
		
		for (int i = 0; i < this.nBytes; i++) {
			if (this.bits[i] != 0)
				res = false;
		}
		
		return res;
	}
	
	public void or(BitList s) {
		for (int i = 0; i < this.nBytes; i++) {
			if (i >= s.nBytes)
				break;

			this.bits[i] |= s.bits[i];
		}
	}
	
	private void fromString(String str) {
		if (this.nBytes == 0)
			return;
		
		byte[] strBits = str.getBytes();
		
		int last = strBits.length - this.nBytes * 2;
		if (last < 0)
			last = 0;
		
		byte mask = (byte) (0xff >> (7 - bitInByte(this.nBits - 1)));
		
		for (int i = strBits.length-1, b = 0; i >= last; i -= 2, b++) {
			byte c = fromHex(strBits[i]);
			if (i-1 >= 0)
				c |= fromHex(strBits[i-1]) << 4;
			this.bits[b] = c;
		}
		
		/*
		 * Mask out high bits of last byte
		 */
		this.bits[this.nBytes-1] &= mask;
	}
	
	public String toString() {
		String res = "";
		boolean nonzero = false;
		
		if (this.nBits == 0) {
			res = "0";
		} else {
			for (int i = this.nBytes-1 ; i >= 0; i--) {
				nonzero |= this.bits[i] != 0;
				if (nonzero) {
					res += Integer.toHexString((this.bits[i] >> 4) & 0xf);
					res += Integer.toHexString(this.bits[i] & 0xf);
				}
			}
			if (!nonzero)
				res = "0";
		}
		
		return res;
	}
	
	private byte fromHex(byte b) {
		if (b >= 48 && b <= 57)
			return (byte) (b - 48);
		if (b >= 65 && b <= 70)
			return (byte) (b - 65 + 10);
		if (b >= 97 && b <= 102)
			return (byte) (b - 97 + 10);
		
		return (byte)0;
	}

	private int bytePos(int idx) {
		return idx >> 3;
	}
	
	private int bitInByte(int idx) {
		return idx - (bytePos(idx) << 3);
	}
	
    /**
     * Returns a hash code for this BitList. 
     * (The hash value of the empty BitList is zero.)
     * @return  a hash code value for this object.
     */
	public int hashCode() {
		int h = hash;
		if (h == 0) {
			for (int i = 0; i < nBytes; i++) {
				h = 31*h + bits[i];
	        }
	        hash = h;
		}
		return h;
	}
    /**
     * Compares this BitList to the specified object.
     * @param  obj The object to compare this {@code BitList} against
     * @return  {@code true} if the given object represents a {@code BitList} equivalent to this BitList, {@code false} otherwise
     */
	public boolean equals(Object obj) {
		if (this == obj) {
		    return true;
		}
		if (obj instanceof BitList) {
			BitList anotherBitList = (BitList)obj;
			if (nBits != anotherBitList.nBits || nBytes != anotherBitList.nBytes)
				return false;
			
			for (int i = 0; i < nBytes; i++) {
				if (bits[i] != anotherBitList.bits[i])
					return false;
			}
			return true;
		}
		return false;
	}

	public static String showBitList(BitList tasks) {
		if (tasks == null) {
			return "";
		}
		int[] array = tasks.toArray();
		if (array.length == 0)
			return "";
		
		String msg = "";
		int preTask = array[0];
		msg += preTask;
		boolean isContinue = false;
		for (int i = 1; i < array.length; i++) {
			if (preTask == (array[i] - 1)) {
				preTask = array[i];
				isContinue = true;
				if (i == (array.length - 1)) {
					msg += "-" + array[i];
					break;
				}
				continue;
			}
			if (isContinue)
				msg += "-" + preTask;
			msg += "," + array[i];
			isContinue = false;
			preTask = array[i];
		}
		return msg;
	}
}
