package com.kollus;

public class ZeroFilter {
    @Override
    public boolean equals(Object obj) {
        if(obj != null){
            return (Long) obj == 0;
        }
        else return true;
    }
}
