package main.java;

import main.java.GraphClass.Edge;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    Edge e1 = new Edge(0,1,1.5);
    Edge e2 = new Edge(1,0,1.2);
    Edge e3 = new Edge(2,1,1.35);

    @Test
    void isConstructed(){
        assertNotNull(e1);
        assertNotNull(e2);
        assertNotNull(e3);
    }
    @Test
    void copyConstructor(){
        Edge c_e1 = new Edge(e1);
        Edge c_e2 = new Edge(e2);
        Edge c_e3 = new Edge(e3);
        assertNotNull(c_e1);
        assertNotNull(c_e2);
        assertNotNull(c_e3);
        assertEquals(e1.getSrc(),c_e1.getSrc());
        assertEquals(e1.getDest(),c_e1.getDest());
        assertEquals(e1.getWeight(),c_e1.getWeight());
        assertEquals(e1.getInfo(),c_e1.getInfo());
        assertEquals(e1.getTag(),c_e1.getTag());
        assertEquals(e2.getSrc(),c_e2.getSrc());
        assertEquals(e2.getDest(),c_e2.getDest());
        assertEquals(e2.getWeight(),c_e2.getWeight());
        assertEquals(e2.getInfo(),c_e2.getInfo());
        assertEquals(e2.getTag(),c_e2.getTag());
        assertEquals(e3.getSrc(),c_e3.getSrc());
        assertEquals(e3.getDest(),c_e3.getDest());
        assertEquals(e3.getWeight(),c_e3.getWeight());
        assertEquals(e3.getInfo(),c_e3.getInfo());
        assertEquals(e3.getTag(),c_e3.getTag());

    }
    @Test
    void getSrc() {
        assertEquals(0,e1.getSrc());
        assertEquals(1,e2.getSrc());
        assertEquals(2,e3.getSrc());
    }

    @Test
    void getDest() {
        assertEquals(1,e1.getDest());
        assertEquals(0,e2.getDest());
        assertEquals(1,e3.getDest());
    }

    @Test
    void getWeight() {
        assertEquals(1.5,e1.getWeight());
        assertEquals(1.2,e2.getWeight());
        assertEquals(1.35,e3.getWeight());
    }

    @Test
    void getInfo() {
        String s1 = "\"src\": 0,\n\"w\": 1.5,\n\"dest\": 1";
        String s2 = "\"src\": 1,\n\"w\": 1.2,\n\"dest\": 0";
        String s3 = "\"src\": 2,\n\"w\": 1.35,\n\"dest\": 1";
        assertEquals(s1, e1.getInfo());
        assertEquals(s2, e2.getInfo());
        assertEquals(s3, e3.getInfo());
    }
    @Test
    void getTag() {
        assertEquals(0,e1.getTag());
        assertEquals(0,e2.getTag());
        assertEquals(0,e3.getTag());
    }

    @Test
    void setTag() {
        e1.setTag(1);
        e2.setTag(2);
        e3.setTag(3);
        assertEquals(1,e1.getTag());
        assertEquals(2,e2.getTag());
        assertEquals(3,e3.getTag());
    }
}