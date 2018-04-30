//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ib.client;

import java.util.Vector;

public class Util {
    public Util() {
    }

    public static boolean StringIsEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String NormalizeString(String str) {
        return str != null?str:"";
    }

    public static int StringCompare(String lhs, String rhs) {
        return NormalizeString(lhs).compareTo(NormalizeString(rhs));
    }

    public static int StringCompareIgnCase(String lhs, String rhs) {
        return NormalizeString(lhs).compareToIgnoreCase(NormalizeString(rhs));
    }

    public static boolean VectorEqualsUnordered(Vector lhs, Vector rhs) {
        if(lhs == rhs) {
            return true;
        } else {
            int lhsCount = lhs == null?0:lhs.size();
            int rhsCount = rhs == null?0:rhs.size();
            if(lhsCount != rhsCount) {
                return false;
            } else if(lhsCount == 0) {
                return true;
            } else {
                boolean[] matchedRhsElems = new boolean[rhsCount];

                for(int lhsIdx = 0; lhsIdx < lhsCount; ++lhsIdx) {
                    Object lhsElem = lhs.get(lhsIdx);

                    int rhsIdx;
                    for(rhsIdx = 0; rhsIdx < rhsCount; ++rhsIdx) {
                        if(!matchedRhsElems[rhsIdx] && lhsElem.equals(rhs.get(rhsIdx))) {
                            matchedRhsElems[rhsIdx] = true;
                            break;
                        }
                    }

                    if(rhsIdx >= rhsCount) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    public static String IntMaxString(int value) {
        return value == 2147483647?"":"" + value;
    }

    public static String DoubleMaxString(double value) {
        return value == 1.7976931348623157E308D?"":"" + value;
    }
}
