//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ib.client;

public class TagValue {
    public String m_tag;
    public String m_value;

    public TagValue() {
    }

    public TagValue(String p_tag, String p_value) {
        this.m_tag = p_tag;
        this.m_value = p_value;
    }

    public boolean equals(Object p_other) {
        if(this == p_other) {
            return true;
        } else if(p_other == null) {
            return false;
        } else {
            TagValue l_theOther = (TagValue)p_other;
            return Util.StringCompare(this.m_tag, l_theOther.m_tag) == 0 && Util.StringCompare(this.m_value, l_theOther.m_value) == 0;
        }
    }
}
