package org.fatmansoft.teach.util;

import lombok.Data;

@Data
public class Pair<V,K> {
    private V first;
    private K second;
    public Pair(V first, K second){
        this.first = first;
        this.second = second;
    }
}
