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

    public static int STRCount(String sequence, String STR) {
        int dna = sequence.length();
        int str = STR.length();

        // If the str is longer than dna sequence, then return 0
        if(str > dna){
            return 0;
        }

        int radix = 256;
        long p = 54321102419L;

        long strHash = 0;
        long dnaHash = 0;
        long RM = 1;

        for(int i = 1; i <= str -1; i++){
            RM = (RM * radix) % p;
        }



        return 0;
    }
}
