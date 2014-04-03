public class CipherTest {
  public static void main(String[] args) {


    String encode = Cipher.encodeLine("Hello");
    System.out.println("Expected: Xueea \t Actual: " + encode); 
 
    //Add 5 more test cases here for encodeLine method
    encode = Cipher.encodeLine("... ABC");
    System.out.println("Expected: ... CKD \t Actual: " + encode);

    encode = Cipher.encodeLine("abc!");
    System.out.println("Expected: ckd! \t Actual: " + encode);

    encode = Cipher.encodeLine("12 abc 34");
    System.out.println("Expected: 12 ckd 34 \t Actual: " + encode);

    encode = Cipher.encodeLine("A1a B2b");
    System.out.println("Expected: C1c K2k \t Actual: " + encode);

    encode = Cipher.encodeLine("!@#$%^&*()_zyx");
    System.out.println("Expected: !@#$%^&*()_pts \t Actual: " + encode);

      
    String decode = Cipher.decodeLine("Xueea");
    System.out.println("Expected: Hello \t Actual: " + decode); 

    //Add 5 more test cases here for decodeLine method
    decode = Cipher.decodeLine("ckdlurmxnwvehbayoifgqzjstp");
    System.out.println("Expected: abcdefghijklmnopqrstuvwxyz \t Actual: " + decode);

    decode = Cipher.decodeLine("CKDLURMXNWVEHBAYOIFGQZJSTP");
    System.out.println("Expected: ABCDEFGHIJKLMNOPQRSTUVWXYZ \t Actual: " + decode);

    decode = Cipher.decodeLine("Ludalul");
    System.out.println("Expected: Decoded \t Actual: " + decode);

    decode = Cipher.decodeLine(" d k c ");
    System.out.println("Expected:  c b a  \t Actual: " + decode);

    decode = Cipher.decodeLine("_123_PsT");
    System.out.println("Expected: _123_ZyX \t Actual: " + decode);
  }
}
