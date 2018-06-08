package io.eblock.eos4j.ecc;

import java.math.BigInteger;

/**
 * Curve
 * 
 * @author espritblock http://eblock.io
 *
 */
public class Curve {

	private FieldElement a;

	private FieldElement b;

	private BigInteger q;

	private Point infinity;

	private BigInteger pOverFour;

	public Curve(BigInteger q, BigInteger a, BigInteger b) {
		this.q = q;
		this.a = fromBigInteger(a);
		this.b = fromBigInteger(b);
		this.infinity = new Point(this, null, null);
		this.pOverFour = q.add(BigInteger.ONE).shiftRight(2);
	}

	public FieldElement getA() {
		return a;
	}

	public FieldElement getB() {
		return b;
	}

	public BigInteger getQ() {
		return q;
	}

	public Point getInfinity() {
		return infinity;
	}

	public int getFieldSize() {
		return q.bitLength();
	}

	public FieldElement fromBigInteger(BigInteger x) {
		return new FieldElement(this.q, x);
	}

	public Point pointFromX(int isOdd, BigInteger x) {
		FieldElement f = this.a.multiply(fromBigInteger(x));
		BigInteger alpha = x.pow(3).add(f.toBigInteger()).add(this.b.toBigInteger()).mod(this.q);
		BigInteger beta = alpha.modPow(this.pOverFour, this.q);
		BigInteger y = beta;
		if (beta.intValue() % 2 == 0 ^ isOdd == 0) {
			y = this.q.subtract(y);
		}
		return new Point(this, new FieldElement(this.q, x), new FieldElement(this.q, y), false);
	}

	public Point decodePoint(byte[] encodedPoint) {
		Point p = null;
		switch (encodedPoint[0]) {
		case 0x00:
			p = getInfinity();
			break;
		case 0x02:
		case 0x03:
			int ytilde = encodedPoint[0] & 1;
			byte[] i = new byte[encodedPoint.length - 1];
			System.arraycopy(encodedPoint, 1, i, 0, i.length);
			FieldElement x = new FieldElement(this.q, new BigInteger(1, i));
			FieldElement alpha = x.multiply(x.square().add(a)).add(b);
			FieldElement beta = alpha.sqrt();
			if (beta == null) {
				throw new RuntimeException("Invalid compression");
			}
			int bit0 = (beta.toBigInteger().testBit(0) ? 1 : 0);
			if (bit0 == ytilde) {
				p = new Point(this, x, beta, true);
			} else {
				p = new Point(this, x, new FieldElement(this.q, q.subtract(beta.toBigInteger())), true);
			}
			break;
		case 0x04:
		case 0x06:
		case 0x07:
			byte[] xEnc = new byte[(encodedPoint.length - 1) / 2];
			byte[] yEnc = new byte[(encodedPoint.length - 1) / 2];
			System.arraycopy(encodedPoint, 1, xEnc, 0, xEnc.length);
			System.arraycopy(encodedPoint, xEnc.length + 1, yEnc, 0, yEnc.length);
			p = new Point(this, new FieldElement(this.q, new BigInteger(1, xEnc)),
					new FieldElement(this.q, new BigInteger(1, yEnc)));
			break;
		default:
			throw new RuntimeException("Invalid encoding 0x" + Integer.toString(encodedPoint[0], 16));
		}
		return p;
	}
}
