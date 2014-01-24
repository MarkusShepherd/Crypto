package info.riemannhypothesis.crypto;

import info.riemannhypothesis.crypto.tools.BlockSequence;
import info.riemannhypothesis.crypto.tools.VideoHash;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Week3 {

	public static void main(String[] args) throws IOException {
		String path = "/Users/MarkusSchepke/Downloads/video-task.mp4";
		File file = new File(path);

		FileInputStream fileInputStream = new FileInputStream(file);

		BlockSequence blocks = new BlockSequence(1024, fileInputStream, (int) file.length());
		VideoHash videoHash = new VideoHash();

		// System.out.println("Beginning of the file: " +
		// bytes.toHexString().substring(0, 100));
		System.out.println("Hash: " + videoHash.hash(blocks).toHexString());
		fileInputStream.close();
	}

}
