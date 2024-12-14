import java.util.Arrays;
/**
 * <h1>UInt</h1>
 * Represents an unsigned integer using a boolean array to store the binary representation.
 * Each bit is stored as a boolean value, where true represents 1 and false represents 0.
 *
 * @author Marisela Rivera
 * @version 1.0 (Sept 30, 2024)
 */
public class UInt {
    // The array representing the bits of the unsigned integer.
    protected boolean[] bits;
    // The number of bits used to represent the unsigned integer.
    protected int length;
    public UInt(boolean[] bits) {
        this.length = bits.length;
        this.bits = Arrays.copyOf(bits, this.length);
    }
    /**
     * Constructs a new UInt by cloning an existing UInt object.
     *
     * @param toClone The UInt object to clone.
     */
    public UInt(UInt toClone) {
        this.length = toClone.length;
        this.bits = Arrays.copyOf(toClone.bits, this.length);
    }
    /**
     * Constructs a new UInt from an integer value.
     * The integer is converted to its binary representation and stored in the bits array.
     *
     * @param i The integer value to convert to a UInt.
     */
    public UInt(int i) {
// Determine the number of bits needed to store i in binary format.
        if (i == 0) {
            length = 1;
            bits = new boolean[]{false};
            return;
        }
        length = (int) (Math.ceil(Math.log(i) / Math.log(2.0)) + 1);
        bits = new boolean[length];
// Convert the integer to binary and store each bit in the array.
        for (int b = length - 1; b >= 0; b--) {
// We use a ternary to decompose the integer into binary digits, starting with the 1s place.
            bits[b] = i % 2 == 1;
// Right shift the integer to process the next bit.
            i = i >> 1;
// Deprecated analog method
/*int p = 0;
while (Math.pow(2, p) < i) {
p++;
}
p--;
bits[p] = true;
i -= Math.pow(2, p);*/
        }
    }
    /**
     * Creates and returns a copy of this UInt object.
     *
     * @return A new UInt object that is a clone of this instance.
     */
    @Override
    public UInt clone() {
        return new UInt(this);
    }
    /**
     * Creates and returns a copy of the given UInt object.
     *
     * @param u The UInt object to clone.
     * @return A new UInt object that is a copy of the given object.
     */
    public static UInt clone(UInt u) {
        return new UInt(u);
    }
    /**
     * Converts this UInt to its integer representation.
     *
     * @return The integer value corresponding to this UInt.
     */
    public int toInt() {
        int t = 0;
// Traverse the bits array to reconstruct the integer value.
        for (int i = 0; i < length; i++) {
// Again, using a ternary to now re-construct the int value, starting with the most-significant bit.
            t = t + (bits[i] ? 1 : 0);
// Shift the value left for the next bit.
            t = t << 1;
        }
        return t >> 1; // Adjust for the last shift.
    }
    /**
     * Static method to retrieve the int value from a generic UInt object.
     *
     * @param u The UInt to convert.
     * @return The int value represented by u.
     */
    public static int toInt(UInt u) {
        return u.toInt();
    }
    /**
     * Returns a String representation of this binary object with a leading 0b.
     *
     * @return The constructed String.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("0b");
// Construct the String starting with the most-significant bit.
        for (int i = 0; i < length; i++) {
// Again, we use a ternary here to convert from true/false to 1/0
            s.append(bits[i] ? "1" : "0");
        }
        return s.toString();
    }
    /**
     * Performs a logical AND operation using this.bits and u.bits, with the result stored in this.bits.
     *
     * @param u The UInt to AND this against.
     */
    public void and(UInt u) {
// We want to traverse the bits arrays to perform our AND operation.
// But keep in mind that the arrays may not be the same length.
// So first we use Math.min to determine which is shorter.
// Then we need to align the two arrays at the 1s place, which we accomplish by indexing them at length-i-1.
        for (int i = 0; i < Math.min(this.length, u.length); i++) {
            this.bits[this.length - i - 1] =
                    this.bits[this.length - i - 1] &
                            u.bits[u.length - i - 1];
        }
// In the specific case that this.length is greater, there are additional elements of
// this.bits that are not getting ANDed against anything.
// Depending on the implementation, we may want to treat the operation as implicitly padding
// the u.bits array to match the length of this.bits, in which case what we actually
// perform is simply setting the remaining indices of this.bits to false.
// Note that while this logic is helpful for the AND operation if we want to use this
// implementation (implicit padding), it is never necessary for the OR and XOR operations.
        if (this.length > u.length) {
            for (int i = u.length; i < this.length; i++) {
                this.bits[this.length - i - 1] = false;
            }
        }
    }
    /**
     * Accepts a pair of UInt objects and uses a temporary clone to safely AND them together (without changing either).
     *
     * @param a The first UInt
     * @param b The second UInt
     * @return The temp object containing the result of the AND op.
     */
    public static UInt and(UInt a, UInt b) {
        UInt temp = a.clone();
        temp.and(b);
        return temp;
    }
    public void or(UInt u) {
// TODO Complete the bitwise logical OR method
// int maxLength = Math.max(this.length, u.length);
// this.bits = Arrays.copyOf(this.bits, maxLength);
// u.bits = Arrays.copyOf(u.bits, maxLength);
//
// for (int i = 0; i < maxLength; i++) {
// this.bits[maxLength - i - 1] |= u.bits[maxLength - i - 1];
        for (int i = 0; i < Math.min(this.bits.length, u.bits.length); i++) {
            this.bits[this.bits.length - i - 1] |= u.bits[u.bits.length - i - 1];
        }
    }
    public static UInt or(UInt a, UInt b) {
// TODO Complete the static OR method
        UInt result = new UInt(a);
        result.or(b);
        return result;
    }
    public void xor(UInt u) {
// TODO Complete the bitwise logical XOR method
// int maxLength = Math.max(this.length, u.length);
// this.bits = Arrays.copyOf(this.bits, maxLength);
// u.bits = Arrays.copyOf(u.bits, maxLength);
//for (int i = 0; i < maxLength; i++) {
        for (int i = 0; i < Math.min(this.bits.length, u.bits.length); i++) {
            this.bits[this.bits.length - i - 1] ^= u.bits[u.bits.length - i - 1];
        }
    }
    public static UInt xor(UInt a, UInt b) {
// TODO Complete the static XOR method
        UInt result = new UInt(a);
        result.xor(b);
        return result;
    }
    public void add(UInt u) {
//TODO Using a ripple-carry adder, perform addition using a passed UINT object
// The result will be stored in this.bits
// You will likely need to create a couple of helper methods for this.
// Note this one, like the bitwise ops, also needs to be aligned on the 1s place.
// Also note this may require increasing the length of this.bits to contain the result.
        int maxLength = Math.max(this.length, u.length) + 1;
        this.bits = Arrays.copyOf(this.bits, maxLength);
        u.bits = Arrays.copyOf(u.bits, maxLength);
        boolean carry = false;
        for (int i = 0; i < maxLength; i++) {
            boolean bit1 = i < this.length ? this.bits[this.length - i - 1] : false;
            boolean bit2 = i < u.length ? u.bits[u.length - i - 1] : false;
            boolean sum = bit1 ^ bit2 ^ carry;
            carry = (bit1 && bit2) || (carry && (bit1 ^ bit2));
            this.bits[maxLength - i - 1] = sum;
        }
        if (carry) {
            this.bits[maxLength - 1] = true;
        }
        if (!this.bits[0]) {
            this.bits = Arrays.copyOfRange(this.bits, 1, this.bits.length);
            this.length = maxLength - 1;
        } else {
            this.length = maxLength;
        }
    }
    public static UInt add(UInt a, UInt b) {
// TODO A static change-safe version of add, should return a temp UInt object like the bitwise ops.
        UInt result = new UInt(a);
        result.add(b);
        return result;
    }
    public void align(UInt u) {
        int maxLength = Math.max(this.length, u.length);
        this.bits = Arrays.copyOf(this.bits, maxLength);
        u.bits = Arrays.copyOf(u.bits, maxLength);
        this.length = maxLength;
        u.length = maxLength;
    }
    public void negate() {
// TODO You'll need a way to perform 2's complement negation
// The add() method will be helpful with this.
        for (int i = 0; i < this.length; i++) {
            this.bits[i] = !this.bits[i];
        }
        boolean carry = true;
        for (int i = this.length - 1; i >= 0; i--) {
            boolean sum = this.bits[i] ^ carry;
            carry = this.bits[i] && carry;
            this.bits[i] = sum;
            if (!carry) break;
        }
    }
    public void sub(UInt u) {
// TODO Using negate() and add(), perform in-place subtraction
// As this class is supposed to handle only unsigned values,
// if the result of the subtraction operation would be a negative number then it should be coerced to 0.
        int maxLength = Math.max(this.length, u.length);
        this.bits = Arrays.copyOf(this.bits, maxLength);
        u.bits = Arrays.copyOf(u.bits, maxLength);
        boolean borrow = false;
        for (int i = 0; i < maxLength; i++) {
            boolean bit1 = i < this.length ? this.bits[this.length - i - 1] : false;
            boolean bit2 = i < u.length ? u.bits[u.length - i - 1] : false;
            boolean diff = bit1 ^ bit2 ^ borrow;
            borrow = (!bit1 && (bit2 || borrow)) || (borrow && bit2);
            this.bits[maxLength - i - 1] = diff;
        }
        int newLength = maxLength;
        while (newLength > 1 && !this.bits[0]) {
            this.bits = Arrays.copyOfRange(this.bits, 1, this.bits.length);
            newLength--;
        }
        this.length = newLength;
    }
    // // Check if the result is a negative value due to underflow.
// if (this.bits[0]) {
// this.bits = new boolean[this.length];// Reset to zero
// }
    public static UInt sub(UInt a, UInt b) {
// TODO And a static change-safe version of sub
        UInt result = new UInt(a);
        result.sub(b);
        return result;
    }
    public void mul(UInt u) {
// TODO Using Booth's algorithm, perform multiplication
// This one will require that you increase the length of bits, up to a maximum of X+Y.
// Having negate() and add() will obviously be useful here.
// Also note the Booth's always treats binary values as if they are signed,
// while this class is only intended to use unsigned values.
// This means that you may need to pad your bits array with a leading 0 if it's not already long enough.
        int x = this.length;
        int y = u.length;
        int n = Math.max(x, y) + 1; // Add 1 for sign bit or padding
        this.bits = Arrays.copyOf(this.bits, n);
        u.bits = Arrays.copyOf(u.bits, n);

        boolean[] A = new boolean[n]; // Accumulator initialized to 0
        boolean[] Q = Arrays.copyOf(this.bits, n); // Copy of this.bits
        boolean[] M = Arrays.copyOf(u.bits, n); // Copy of u.bits
        boolean Q_1 = false; // Initialize Q_1 to 0 (no previous bit)

        // Booth's Algorithm
        for (int i = 0; i < n; i++) {
            if (Q[n - 1] && !Q_1) {
                UInt temp = new UInt(A);
                temp.sub(new UInt(M)); // Perform A = A - M
                A = Arrays.copyOf(temp.bits, n);
            } else if (!Q[n - 1] && Q_1) {
                UInt temp = new UInt(A);
                temp.add(new UInt(M)); // Perform A = A + M
                A = Arrays.copyOf(temp.bits, n);
            }
            Q_1 = rightShift(A, Q); // Perform arithmetic right shift
        }

        // Combine A and Q into the result
        boolean[] result = new boolean[2 * n - 1]; // Result array to hold 2*n-1 bits
        System.arraycopy(A, 0, result, 0, n);
        System.arraycopy(Q, 0, result, n, n - 1);

        this.bits = result;
        this.length = result.length;

    }
    private boolean rightShift(boolean[] A, boolean[] Q) {
        boolean newQ_1 = Q[Q.length - 1];
        for (int i = Q.length - 1; i > 0; i--) {
            Q[i] = Q[i - 1];
        }
        Q[0] = A[A.length - 1];
        boolean signBit = A[0];
        for (int i = A.length - 1; i > 0; i--){
            A[i] = A[i - 1];
        }
        A[0] = signBit;
        return newQ_1;
    }
    public static UInt mul(UInt a, UInt b) {
// TODO A static, change-safe version of mul
        UInt result = new UInt(a);
        result.mul(b);
        return result;
    }
    public static void main(String[] args) {
        int aInt = 5;
        int bInt = 3;
        UInt a = new UInt(aInt);
        UInt b = new UInt(bInt);
        System.out.println("a: " + a.toString());
        System.out.println("b: " + b.toString());
        UInt sum = UInt.add(a, b);
        System.out.println("a + b: " + sum.toString());
        UInt diff = UInt.sub(a, b);
        System.out.println("a - b: " + diff.toString());
        UInt product = UInt.mul(a, b);
        System.out.println("a * b: " + product.toString());
    }
}
