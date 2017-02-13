package warmup;

import java.util.Set;
import java.util.HashSet;

public class Quadratic {

    /**
     * Find the integer roots of a quadratic equation, ax^2 + bx + c = 0.
     * @param a coefficient of x^2
     * @param b coefficient of x
     * @param c constant term.  Requires that a, b, and c are not ALL zero.
     * @return all integers x such that ax^2 + bx + c = 0.
     */
    public static Set<Integer> roots(int a, int b, int c) {
        long long_a = a;
        long long_b = b;
        long long_c = c;
        Set<Integer> set = new HashSet<Integer>();
        if(long_a == 0 && long_b == 0){
            return set;
        } else {
            if(long_a == 0){
                long x = long_c * (-1) / long_b;
                if((long_b * x + long_c) == 0){
                    set.add((int)x);
                }
                return set;
            } else {
                long delta_square = (long_b * long_b - 4 * long_a * long_c);
           
                if(delta_square < 0){
                    return set;
                }
           
                double delta = Math.sqrt(delta_square);
                double sol1 = (-1) * b + delta;
                double sol2 = (-1) * b - delta;
                long x1 = (long)(sol1/(2 * a));
                long x2 = (long)(sol2/(2 * a));
                if((long_a * x1 * x1 + long_b * x1 + long_c) == 0){
                    set.add((int)x1);
                }
                if((long_a * x2 * x2 + long_b * x2 + long_c) == 0){
                    set.add((int)x2);
                }
                return set;
            }
        }
    }

    
    /**
     * Main function of program.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("For the equation x^2 - 4x + 3 = 0, the possible solutions are:");
        Set<Integer> result = roots(1, -4, 3);
        System.out.println(result);
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
