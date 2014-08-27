/* CipherDesTest.java Copyright (c) 2013 by Dr. Herong Yang, herongyang.com
 * Source:
 * http://www.herongyang.com/Cryptography/DES-Java-Implementation-Program
 * -Test-Case.html */
package herong;

class CipherDesTest {
    public static void main(String[] a) {
        if (a.length < 1) {
            System.out.println("Usage:");
            System.out.println("java CipherDesTest 1");
            return;
        }
        String test = a[0];
        try {
            byte[] theKey = null;
            byte[] theMsg = null;
            byte[] theExp = null;
            if (test.equals("1")) {
                theKey = hexToBytes("133457799BBCDFF1");
                theMsg = hexToBytes("0123456789ABCDEF");
                theExp = hexToBytes("85E813540F0AB405");
            } else if (test.equals("2")) {
                theKey = hexToBytes("38627974656B6579"); // "8bytekey"
                theMsg = hexToBytes("6D6573736167652E"); // "message."
                theExp = hexToBytes("7CF45E129445D451");
            } else {
                System.out.println("Usage:");
                System.out.println("java CipherDesTest 1");
                return;
            }
            byte[][] subKeys = CipherDES.getSubkeys(theKey);
            byte[] theCph = CipherDES.cipher(theMsg, subKeys, "encrypt");
            System.out.println("Key     : " + bytesToHex(theKey));
            System.out.println("Message : " + bytesToHex(theMsg));
            System.out.println("Cipher  : " + bytesToHex(theCph));
            System.out.println("Expected: " + bytesToHex(theExp));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public static byte[] hexToBytes(String str) {
        if (str == null) {
            return null;
        } else if (str.length() < 2) {
            return null;
        } else {
            int len = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(
                        str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }

    }

    public static String bytesToHex(byte[] data) {
        if (data == null) {
            return null;
        } else {
            int len = data.length;
            String str = "";
            for (int i = 0; i < len; i++) {
                if ((data[i] & 0xFF) < 16)
                    str = str + "0"
                            + java.lang.Integer.toHexString(data[i] & 0xFF);
                else
                    str = str + java.lang.Integer.toHexString(data[i] & 0xFF);
            }
            return str.toUpperCase();
        }
    }
}