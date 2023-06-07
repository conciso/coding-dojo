package de.conciso.codingdojo;

/**
 * Interface for a simple letter substitution cipher
 */
public interface RotCipher {

    /**
     * Encrypt the given text using a simple substitution cipher the with the help of the given alphabet
     * and the the given rotation factor. The following must be implemented
     * <ul>
     *     <li>The algorithm should exchange the all characters of the text with the ones
     *      &lt;factor&gt; postions after the it. The given alphabet is used for this.</li>
     *     <li>If the new character is not in the alphabet any more counting restarts at the beginning</li>
     * </ul>
     *
     * If the text parameter is illegal a RuntimeException should be thrown.
     *
     * @param text text which should be encrypted
     * @return result text of the encryption algorithm
     *
     */
    String encrypt(String text);

    /**
     * Decrypt the given text with the inverse operation of the {@link #encrypt(String)} function.
     * If the given parameter is illegal a RuntimeException should be thrown
     *
     * @param encryptedText text to be decrypted
     * @return original text
     */
    String decrypt(String encryptedText);

    /**
     * Get the alphabet used of encryption and decryption
     * @return alphabet as character array
     */
    default char[] getAlphabet() {
        return "abcdefghijklmnopqrstuvwxyzäöü1234567890".toCharArray();
    }

    /**
     * Get the rotation factor for the cipher algorithms
     * @return rotation factor as integer
     */
    default int getRotationFactor() {
        return 13;
    }
}
