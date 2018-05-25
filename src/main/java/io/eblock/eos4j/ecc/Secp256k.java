package io.eblock.eos4j.ecc;

import java.math.BigInteger;

import io.eblock.eos4j.utils.Hex;

/**
 * Curve
 * 
 * @author espritblock  http://eblock.io
 *
 */
public class Secp256k {

	public static final String P = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F";
	public static final String A = "0";
	public static final String B = "7";
	public static final String N = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141";
	public static final String GX = "79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798";
	public static final String GY = "483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8";

	private final Curve curve;

	private final Point G;

	private final BigInteger n;

	private final BigInteger HALF_CURVE_ORDER;

	public Secp256k() {
		n = new BigInteger(N, 16);
		HALF_CURVE_ORDER = n.shiftRight(1);
		curve = new Curve(new BigInteger(P, 16), new BigInteger(A, 16), new BigInteger(B, 16));
		G = curve.decodePoint(Hex.toBytes("04" + GX + GY));
	}

	public Point G() {
		return this.G;
	}

	public BigInteger n() {
		return this.n;
	}

	public BigInteger halfCurveOrder() {
		return HALF_CURVE_ORDER;
	}

	public Curve getCurve() {
		return curve;
	}
}
