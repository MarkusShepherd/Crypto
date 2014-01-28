package info.riemannhypothesis.crypto;

import info.riemannhypothesis.crypto.tools.ByteSequence;

public class Week4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ByteSequence iv = ByteSequence.fromHexString("20814804c1767293b99f1d9cab3bc3e7");
		byte[] zeros = new byte[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		ByteSequence m1 = new ByteSequence("Pay Bob 100$").append(zeros);
		ByteSequence m2 = new ByteSequence("Pay Bob 500$").append(zeros);
		System.out.println("Result IV: " + iv.xor(m1).xor(m2).toHexString());
	}

}
