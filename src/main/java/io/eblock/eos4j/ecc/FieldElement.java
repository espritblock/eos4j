package io.eblock.eos4j.ecc;

import java.math.BigInteger;
import java.util.Random;

import io.eblock.eos4j.utils.EException;

/**
 * FieldElement
 * 
 * @author espritblock http://eblock.io
 *
 */
public class FieldElement {

	private BigInteger q;

	private BigInteger x;

	private static final BigInteger TWO = BigInteger.valueOf(2);

	public FieldElement(BigInteger q, BigInteger x) {
		this.x = x;
		if (x.compareTo(q) >= 0) {
			throw new EException("error", "x value too large in field element");
		}
		this.q = q;
	}

	public FieldElement add(FieldElement b) {
		return new FieldElement(q, x.add(b.toBigInteger()).mod(q));
	}

	public FieldElement subtract(FieldElement b) {
		return new FieldElement(q, x.subtract(b.toBigInteger()).mod(q));
	}

	public FieldElement multiply(FieldElement b) {
		return new FieldElement(q, x.multiply(b.toBigInteger()).mod(q));
	}

	public FieldElement divide(FieldElement b) {
		return new FieldElement(q, x.multiply(b.toBigInteger().modInverse(q)).mod(q));
	}

	public FieldElement negate() {
		return new FieldElement(q, x.negate().mod(q));
	}

	public FieldElement square() {
		return new FieldElement(q, x.multiply(x).mod(q));
	}

	public FieldElement invert() {
		return new FieldElement(q, x.modInverse(q));
	}

	@Override
	public String toString() {
		return this.toBigInteger().toString(16);
	}

	public FieldElement sqrt() {
		if (!q.testBit(0)) {
			throw new RuntimeException("not done yet");
		}

		if (q.testBit(1)) {
			FieldElement z = new FieldElement(q, x.modPow(q.shiftRight(2).add(BigInteger.ONE), q));
			return z.square().equals(this) ? z : null;
		}
		BigInteger qMinusOne = q.subtract(BigInteger.ONE);
		BigInteger legendreExponent = qMinusOne.shiftRight(1);
		if (!(x.modPow(legendreExponent, q).equals(BigInteger.ONE))) {
			return null;
		}
		BigInteger u = qMinusOne.shiftRight(2);
		BigInteger k = u.shiftLeft(1).add(BigInteger.ONE);
		BigInteger Q = this.x;
		BigInteger fourQ = Q.shiftLeft(2).mod(q);
		BigInteger U, V;
		Random rand = new Random();
		do {
			BigInteger P;
			do {
				P = new BigInteger(q.bitLength(), rand);
			} while (P.compareTo(q) >= 0
					|| !(P.multiply(P).subtract(fourQ).modPow(legendreExponent, q).equals(qMinusOne)));
			BigInteger[] result = lucasSequence(q, P, Q, k);
			U = result[0];
			V = result[1];
			if (V.multiply(V).mod(q).equals(fourQ)) {
				if (V.testBit(0)) {
					V = V.add(q);
				}
				V = V.shiftRight(1);
				return new FieldElement(q, V);
			}
		} while (U.equals(BigInteger.ONE) || U.equals(qMinusOne));
		return null;
	}

	private static BigInteger[] lucasSequence(BigInteger p, BigInteger P, BigInteger Q, BigInteger k) {
		int n = k.bitLength();
		int s = k.getLowestSetBit();
		BigInteger Uh = BigInteger.ONE;
		BigInteger Vl = TWO;
		BigInteger Vh = P;
		BigInteger Ql = BigInteger.ONE;
		BigInteger Qh = BigInteger.ONE;
		for (int j = n - 1; j >= s + 1; --j) {
			Ql = Ql.multiply(Qh).mod(p);
			if (k.testBit(j)) {
				Qh = Ql.multiply(Q).mod(p);
				Uh = Uh.multiply(Vh).mod(p);
				Vl = Vh.multiply(Vl).subtract(P.multiply(Ql)).mod(p);
				Vh = Vh.multiply(Vh).subtract(Qh.shiftLeft(1)).mod(p);
			} else {
				Qh = Ql;
				Uh = Uh.multiply(Vl).subtract(Ql).mod(p);
				Vh = Vh.multiply(Vl).subtract(P.multiply(Ql)).mod(p);
				Vl = Vl.multiply(Vl).subtract(Ql.shiftLeft(1)).mod(p);
			}
		}
		Ql = Ql.multiply(Qh).mod(p);
		Qh = Ql.multiply(Q).mod(p);
		Uh = Uh.multiply(Vl).subtract(Ql).mod(p);
		Vl = Vh.multiply(Vl).subtract(P.multiply(Ql)).mod(p);
		Ql = Ql.multiply(Qh).mod(p);
		for (int j = 1; j <= s; ++j) {
			Uh = Uh.multiply(Vl).mod(p);
			Vl = Vl.multiply(Vl).subtract(Ql.shiftLeft(1)).mod(p);
			Ql = Ql.multiply(Ql).mod(p);
		}
		return new BigInteger[] { Uh, Vl };
	}

	public BigInteger toBigInteger() {
		return x;
	}

	public int getFieldSize() {
		return q.bitLength();
	}

	public BigInteger getQ() {
		return q;
	}

	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (!(other instanceof FieldElement)) {
			return false;
		}
		FieldElement o = (FieldElement) other;
		return q.equals(o.q) && x.equals(o.x);
	}

	public int hashCode() {
		return q.hashCode() ^ x.hashCode();
	}

}
