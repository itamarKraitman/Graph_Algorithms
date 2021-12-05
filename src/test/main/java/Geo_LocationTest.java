package main.java;

import main.java.GraphClass.Geo_Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Geo_LocationTest {

    Geo_Location p1 = new Geo_Location(0,0,0);
    Geo_Location p2 = new Geo_Location(1,1,0);
    Geo_Location p3 = new Geo_Location(2,2,0);

    @Test
    void isConstructed(){
        assertNotNull(p1);
        assertNotNull(p2);
        assertNotNull(p3);
    }
    @Test
    void copyConstructor(){
        Geo_Location g1 = new Geo_Location(p1);
        Geo_Location g2 = new Geo_Location(p2);
        Geo_Location g3 = new Geo_Location(p3);
        assertEquals(p1.x(),g1.x());
        assertEquals(p1.y(),g1.y());
        assertEquals(p1.z(),g1.z());
        assertEquals(p2.x(),g2.x());
        assertEquals(p2.y(),g2.y());
        assertEquals(p2.z(),g2.z());
        assertEquals(p3.x(),g3.x());
        assertEquals(p3.y(),g3.y());
        assertEquals(p3.z(),g3.z());
    }
    @Test
    void x() {
        assertEquals(0,p1.x());
        assertEquals(1,p2.x());
        assertEquals(2,p3.x());
    }

    @Test
    void y() {
        assertEquals(0,p1.y());
        assertEquals(1,p2.y());
        assertEquals(2,p3.y());
    }

    @Test
    void z() {
        assertEquals(0,p1.z());
        assertEquals(0,p2.z());
        assertEquals(0,p3.z());
    }

    @Test
    void distance() {
        assertEquals(Math.sqrt(2),p1.distance(p2));
        assertEquals(Math.sqrt(2),p2.distance(p3));
        assertEquals(Math.sqrt(8),p1.distance(p3));
    }

    @Test
    void testToString() {
        String s1 = "(0.0,0.0,0.0)";
        String s2 = "(1.0,1.0,0.0)";
        String s3 = "(2.0,2.0,0.0)";
        assertEquals(s1,p1.toString());
        assertEquals(s2,p2.toString());
        assertEquals(s3,p3.toString());
    }
}