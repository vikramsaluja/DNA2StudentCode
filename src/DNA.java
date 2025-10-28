/**
 * DNA
 * <p>
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *</p>
 * <p>
 * Completed by: Vikram Saluja
 *</p>
 */

public class DNA {

    // Large prime mod to minimize collisions
    private static final long p = 2305843009213693951L;
    private static final int radix = 256;

    // Returns the longest run of consecutive STR repeats
    public static int STRCount(String sequence, String STR) {

        int dna = sequence.length();
        int str = STR.length();

        // If the str is longer than dna sequence, then return 0
        if (str > dna) {
            return 0;
        }

        // Compute radix for rolling hash calculations
        long RM = 1;
        for (int i = 0; i < str - 1; i++) {
            RM = (RM * radix) % p;
        }

        // Compute initial STR and the first substring of sequence
        long strHash = hash(STR);
        long seqHash = hash(sequence.substring(0, str));

        // Longest run found
        int longest = 0;
        // Current number of repeats
        int current = 0;

        // If the hash of STR and current substring are equal, treat it as a match
        for (int i = 0; i <= dna - str; ) {

            // Monte Carlo comparison
            if (seqHash == strHash) {
                // Increase current streak count
                current++;
                // jump ahead by STR length
                i += str;

                // Prevent index out of bounds before recalculating hash
                if (!indexError(i, str, dna)) {
                    seqHash = hash(sequence.substring(i, i + str));
                }

            }
            // Hashes do not match, so we reached the end of a run or had no match
            else
            {
                if (current > 0) {
                    // Compare current run length to longest run so far
                    longest = Math.max(longest, current);
                    // Reset current run count
                    current = 0;

                    // Move index back into previous region to check for new start
                    // Ensure we don't skip overlapping potential STRs
                    i = i - str + 1;

                    // Compute new hash for substring starting at new index
                    if (!indexError(i, str, dna)) {
                        seqHash = hash(sequence.substring(i, i + str));
                    }
                }
                // No current repeat, so just shift the window forward by one
                else
                {
                    i++;
                    // Update rolling hash for next substring window within bounds
                    if (!indexError(i, str, dna)) {
                        // Char leaving windown
                        char oldC = sequence.charAt(i - 1);
                        // Char entering window
                        char newC = sequence.charAt(i + str - 1);
                        // Compute the updated hash
                        seqHash = rollHash(seqHash, oldC, newC, RM);
                    }
                }
            }
        }

        // Final check in case the longest run ends exactly at the end of the sequence
        if (current > longest) {
            longest = current;
        }

        // Return the longest run of consective repeats
        return longest;
    }

    // Computes the hash value of a string using Horner's method
    private static long hash(String sequence){
        long hashVal = 0;
        for(int i = 0; i < sequence.length(); i++){
            // Multiply previous hash by radix, add next char, mod p to avoid overflow
            hashVal = (hashVal * radix + sequence.charAt(i)) %p;
        }
        return hashVal;
    }

    // Update hash when sliding window by one char
    private static long rollHash(long currentHash, char oldC, char newC,long RM){
        long newHash = (currentHash + p - ((oldC *RM) %p)) % p;
        newHash = ((newHash * radix) + newC) % p;
        return newHash;
    }

    // Helper function to check if substring window would go out of bounds
    private static boolean indexError(int index, int str, int dna){
        return index + str > dna;
    }
}

