package io.eblock.eos4j.ecc;

import java.math.BigInteger;

/**
 * Point
 * 
 * @author espritblock  http://eblock.io
 *
 */
public class Point {

	private Curve curve;

	private FieldElement x;

	private FieldElement y;

	private boolean compressed;

	public Point(Curve curve, FieldElement x, FieldElement y, boolean compressed) {
		this.curve = curve;
		this.x = x;
		this.y = y;
		this.compressed = compressed;
	}

	public Point(Curve curve, FieldElement x, FieldElement y) {
		this(curve, x, y, true);
	}

	public Curve getCurve() {
		return curve;
	}

	public FieldElement getX() {
		return x;
	}

	public FieldElement getY() {
		return y;
	}

	public boolean isInfinity() {
		return x == null && y == null;
	}

	public boolean isCompressed() {
		return compressed;
	}

	public byte[] getEncoded() {

		if (this.isInfinity()) {
			return new byte[1];
		}

		int length = getByteLength(x.getFieldSize());

		if (compressed) {
			byte PC;
			if (this.getY().toBigInteger().testBit(0)) {
				PC = 0x03;
			} else {
				PC = 0x02;
			}
			byte[] X = integerToBytes(this.getX().toBigInteger(), length);
			byte[] PO = new byte[X.length + 1];
			PO[0] = PC;
			System.arraycopy(X, 0, PO, 1, X.length);
			return PO;
		} else {
			byte[] X = integerToBytes(this.getX().toBigInteger(), length);
			byte[] Y = integerToBytes(this.getY().toBigInteger(), length);
			byte[] PO = new byte[X.length + Y.length + 1];
			PO[0] = 0x04;
			System.arraycopy(X, 0, PO, 1, X.length);
			System.arraycopy(Y, 0, PO, X.length + 1, Y.length);
			return PO;
		}
	}

	public Point add(Point b) {
		if (this.isInfinity()) {
			return b;
		}
		if (b.isInfinity()) {
			return this;
		}
		if (this.x.equals(b.x)) {
			if (this.y.equals(b.y)) {
				return this.twice();
			}
			return this.curve.getInfinity();
		}
		FieldElement gamma = b.y.subtract(this.y).divide(b.x.subtract(this.x));
		FieldElement x3 = gamma.square().subtract(this.x).subtract(b.x);
		FieldElement y3 = gamma.multiply(this.x.subtract(x3)).subtract(this.y);
		return new Point(curve, x3, y3);
	}

	public Point twice() {
		if (this.isInfinity()) {
			return this;
		}
		if (this.y.toBigInteger().signum() == 0) {
			return this.curve.getInfinity();
		}
		FieldElement TWO = this.curve.fromBigInteger(BigInteger.valueOf(2));
		FieldElement THREE = this.curve.fromBigInteger(BigInteger.valueOf(3));
		FieldElement gamma = this.x.square().multiply(THREE).add(curve.getA()).divide(y.multiply(TWO));
		FieldElement x3 = gamma.square().subtract(this.x.multiply(TWO));
		FieldElement y3 = gamma.multiply(this.x.subtract(x3)).subtract(this.y);
		return new Point(curve, x3, y3, this.compressed);
	}

	public Point subtract(Point b) {
		if (b.isInfinity()) {
			return this;
		}
		return add(b.negate());
	}

	public Point negate() {
		return new Point(curve, this.x, this.y.negate(), this.compressed);
	}

	public Point multiply(BigInteger k) {
		BigInteger e = k;
		BigInteger h = e.multiply(BigInteger.valueOf(3));
		Point neg = this.negate();
		Point R = this;
		for (int i = h.bitLength() - 2; i > 0; --i) {
			R = R.twice();
			boolean hBit = h.testBit(i);
			boolean eBit = e.testBit(i);
			if (hBit != eBit) {
				R = R.add(hBit ? this : neg);
			}
		}
		return R;
	}

	public Point multiplyTwo(BigInteger j, Point x, BigInteger k) {
		int i = Math.max(j.bitLength(), k.bitLength()) - 1;
		Point R = this.curve.getInfinity();
		Point both = this.add(x);
		while (i >= 0) {
			Boolean jBit = j.testBit(i);
			Boolean kBit = k.testBit(i);

			R = R.twice();

			if (jBit) {
				if (kBit) {
					R = R.add(both);
				} else {
					R = R.add(this);
				}
			} else if (kBit) {
				R = R.add(x);
			}
			--i;
		}
		return R;
	}

	public static int getByteLength(int fieldSize) {
		return (fieldSize + 7) / 8;
	}

	public static byte[] integerToBytes(BigInteger s, int length) {
		byte[] bytes = s.toByteArray();
		if (length < bytes.length) {
			byte[] tmp = new byte[length];
			System.arraycopy(bytes, bytes.length - tmp.length, tmp, 0, tmp.length);
			return tmp;
		} else if (length > bytes.length) {
			byte[] tmp = new byte[length];
			System.arraycopy(bytes, 0, tmp, tmp.length - bytes.length, bytes.length);
			return tmp;
		}
		return bytes;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}

		if (!(other instanceof Point)) {
			return false;
		}

		Point o = (Point) other;

		if (this.isInfinity()) {
			return o.isInfinity();
		}

		return x.equals(o.x) && y.equals(o.y);
	}

	@Override
	public int hashCode() {
		if (this.isInfinity()) {
			return 0;
		}

		return x.hashCode() ^ y.hashCode();
	}

}
