package com.darkcraft.bookmarket.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "association")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Association {

    public MyKey getMyKey() {
        return myKey;
    }

    public void setMyKey(MyKey myKey) {
        this.myKey = myKey;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    @EmbeddedId
    private MyKey myKey;
    @Column(name = "confidence")
    float confidence;
}
@Embeddable
class MyKey implements Serializable {

    @Column(name = "item1", nullable = false)
    private int item1;

    @Column(name = "item2", nullable = false)
    private int item2;

    public int getItem1() {
        return item1;
    }

    public void setItem1(int item1) {
        this.item1 = item1;
    }

    public int getItem2() {
        return item2;
    }

    public void setItem2(int item2) {
        this.item2 = item2;
    }
/** getters and setters **/
}
