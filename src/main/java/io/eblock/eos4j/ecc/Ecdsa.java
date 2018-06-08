package io.eblock.eos4j.ecc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import io.eblock.eos4j.utils.ByteUtils;
import io.eblock.eos4j.utils.EException;
import io.eblock.eos4j.utils.Hex;
import io.eblock.eos4j.utils.Sha;

/**
 * Ecdsa
 * 
 * @author espritblock http://eblock.io
 *
 */
public class Ecdsa {

	Secp256k curve = null;

	public Ecdsa(Secp256k curve) {
		this.curve = curve;
	}

	/**
	 * Ç©Ãû
	 * 
	 * @param dataHash
	 * @param d
	 * @param nonce
	 * @return
	 */
	public SignBigInt sign(String dataHash, BigInteger d, int nonce) {
		BigInteger n = curve.n();
		SignBigInt big = new SignBigInt();
		deterministicGenerateK(curve, dataHash, d, nonce, big);
		BigInteger N_OVER_TWO = n.shiftRight(1);
		if (big.getS().compareTo(N_OVER_TWO) > 0) {
			big.setS(n.subtract(big.getS()));
		}
		big.setDer(toDER(big));
		return big;
	}

	/**
	 * toDER
	 * 
	 * @param big
	 * @return
	 */
	private byte[] toDER(SignBigInt big) {
		byte[] rBa = big.getR().toByteArray();
		byte[] sBa = big.getS().toByteArray();
		ArrayList<Byte> sequence = new ArrayList<Byte>();
		sequence.add(new Byte(((byte) 0x02)));
		sequence.add(new Byte(((byte) rBa.length)));
		for (int i = 0; i < rBa.length; i++) {
			sequence.add(rBa[i]);
		}
		sequence.add(new Byte(((byte) 0x02)));
		sequence.add(new Byte(((byte) sBa.length)));
		for (int i = 0; i < sBa.length; i++) {
			sequence.add(sBa[i]);
		}
		int len = sequence.size();
		sequence.add(0, (byte) 0x30);
		sequence.add(1, (byte) len);
		byte[] bf = new byte[sequence.size()];
		for (int i = 0; i < bf.length; i++) {
			bf[i] = sequence.get(i).byteValue();
		}
		return bf;
	}

	private BigInteger deterministicGenerateK(Secp256k curve, String dataHash, BigInteger d, int nonce,
			SignBigInt big) {
		byte[] hash = Hex.hexStringToBytes(dataHash);
		if (nonce > 0) {
			hash = Sha.SHA256(ByteUtils.concat(hash, new byte[nonce]));
		}
		byte[] x = null;
		if (d.toByteArray()[0] == 0) {
			x = ByteUtils.copy(d.toByteArray(), 1, d.toByteArray().length - 1);
		} else {
			x = d.toByteArray();
		}
		// int padding = d.toByteArray().length-tmp.length;
		// byte[] zeros = null;
		// if(padding>0) {
		// zeros = new byte[padding];
		// for(int i =0;i<padding;i++) {
		// zeros[i] = 0 ;
		// }
		// }
		//
		// byte[] x = ByteUtils.copy(d.toByteArray(), 0, d.toByteArray().length-1) ;
		//
		// if(zeros==null) {
		// x = tmp;
		// }else {
		// x = ByteUtils.concat(zeros, tmp);
		// }

		byte[] k = new byte[32];
		byte[] v = new byte[32];

		// b
		Arrays.fill(v, (byte) 0x01);
		// c
		Arrays.fill(k, (byte) 0x00);

		// d
		byte[] db = ByteUtils.concat(ByteUtils.concat(ByteUtils.concat(v, new byte[] { 0 }), x), hash);

		k = Sha.HmacSHA256(db, k);

		// e
		v = Sha.HmacSHA256(v, k);
		// f
		byte[] fb = ByteUtils.concat(ByteUtils.concat(ByteUtils.concat(v, new byte[] { 1 }), x), hash);
		k = Sha.HmacSHA256(fb, k);
		// g
		v = Sha.HmacSHA256(v, k);
		// Step H1/H2a, ignored as tlen === qlen (256 bit)
		// Step H2b
		v = Sha.HmacSHA256(v, k);

		BigInteger T = new BigInteger(Hex.bytesToHexString(v), 16);

		BigInteger e = new BigInteger(dataHash, 16);

		Boolean check = checkSig(T, d, e, big);
		while (T.signum() <= 0 || T.compareTo(curve.n()) >= 0 || !check) {
			k = Sha.HmacSHA256(ByteUtils.concat(v, new byte[] { 0 }), k);
			v = Sha.HmacSHA256(v, k);
			v = Sha.HmacSHA256(v, k);
			T = new BigInteger(v);
		}

		big.setK(T);
		return T;
	}

	/**
	 * checkSig
	 * 
	 * @param k
	 * @param d
	 * @param e
	 * @param big
	 * @return
	 */
	public Boolean checkSig(BigInteger k, BigInteger d, BigInteger e, SignBigInt big) {
		Point Q = curve.G().multiply(k);
		if (Q.isInfinity())
			return false;
		BigInteger r = Q.getX().toBigInteger().mod(curve.n());
		big.setR(r);
		if (r.signum() == 0)
			return false;
		BigInteger s = k.modInverse(curve.n()).multiply(e.add(d.multiply(r))).mod(curve.n());
		big.setS(s);
		if (s.signum() == 0)
			return false;
		return true;
	}

	/**
	 * 
	 * @author espritblock http://eblock.io
	 *
	 */
	public static class SignBigInt {
		private BigInteger k;
		private BigInteger r;
		private BigInteger s;
		private byte[] der;

		public BigInteger getK() {
			return k;
		}

		public void setK(BigInteger k) {
			this.k = k;
		}

		public BigInteger getR() {
			return r;
		}

		public void setR(BigInteger r) {
			this.r = r;
		}

		public BigInteger getS() {
			return s;
		}

		public void setS(BigInteger s) {
			this.s = s;
		}

		public byte[] getDer() {
			return der;
		}

		public void setDer(byte[] der) {
			this.der = der;
		}
	}

	public int calcPubKeyRecoveryParam(BigInteger e, SignBigInt sign, Point Q) {
		for (int i = 0; i < 4; i++) {
			Point Qprime = recoverPubKey(e, sign, i);
			if (Qprime.equals(Q)) {
				return i;
			}
		}
		throw new EException("sign_error", "Unable to find valid recovery factor");
	}

	public Point recoverPubKey(BigInteger e, SignBigInt big, int i) {

		BigInteger n = curve.n();
		Point G = curve.G();

		BigInteger r = big.getR();
		BigInteger s = big.getS();

		if (!(r.signum() > 0 && r.compareTo(n) < 0)) {
			throw new EException("recover_pubkey_error", "Invalid r value");
		}
		if (!(s.signum() > 0 && s.compareTo(n) < 0)) {
			throw new EException("recover_pubkey_error", "Invalid r value");
		}

		// A set LSB signifies that the y-coordinate is odd
		int isYOdd = i & 1;

		// The more significant bit specifies whether we should use the
		// first or second candidate key.
		int isSecondKey = i >> 1;

		// 1.1 Let x = r + jn
		BigInteger x = isSecondKey == 1 ? r.add(n) : r;

		Point R = curve.getCurve().pointFromX(isYOdd, x);

		// // 1.4 Check that nR is at infinity
		Point nR = R.multiply(n);

		if (!nR.isInfinity()) {
			throw new EException("sign_error", "nR is not a valid curve point");
		}

		BigInteger eNeg = e.negate().mod(n);

		BigInteger rInv = r.modInverse(n);

		Point Q = R.multiplyTwo(s, G, eNeg).multiply(rInv);

		if (Q.isInfinity()) {
			throw new EException("sign_error", "Point is at infinity");
		}

		return Q;
	}
}
