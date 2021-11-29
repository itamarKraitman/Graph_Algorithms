package main.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    Geo_Location pos1 = new Geo_Location(10, 10, 0);
    Geo_Location pos2 = new Geo_Location(5, 5, 0);
    Geo_Location pos3 = new Geo_Location(2, 2, 0);
    Node n1 = new Node(0, pos1);
    Node n2 = new Node(1, pos2);
    Node n3 = new Node(2, pos3);

    @Test
    void isConstructed() {
        assertNotNull(n1);
        assertNotNull(n2);
        assertNotNull(n3);
    }

    @Test
    void copyConstructor() {
        Node c_n1 = new Node(n1);
        Node c_n2 = new Node(n2);
        Node c_n3 = new Node(n3);
        assertNotNull(c_n1);
        assertNotNull(c_n2);
        assertNotNull(c_n3);
        assertEquals(n1.getKey(), c_n1.getKey());
        assertEquals(n1.getPosition().x(), c_n1.getPosition().x());
        assertEquals(n1.getPosition().y(), c_n1.getPosition().y());
        assertEquals(n1.getPosition().z(), c_n1.getPosition().z());
        assertEquals(n1.getInfo(), c_n1.getInfo());
        assertEquals(n1.getWeight(), c_n1.getWeight());
        assertEquals(n1.getTag(), c_n1.getTag());
        assertEquals(n2.getKey(), c_n2.getKey());
        assertEquals(n2.getPosition().x(), c_n2.getPosition().x());
        assertEquals(n2.getPosition().y(), c_n2.getPosition().y());
        assertEquals(n2.getPosition().z(), c_n2.getPosition().z());
        assertEquals(n2.getInfo(), c_n2.getInfo());
        assertEquals(n2.getWeight(), c_n2.getWeight());
        assertEquals(n2.getTag(), c_n2.getTag());
        assertEquals(n3.getKey(), c_n3.getKey());
        assertEquals(n3.getPosition().x(), c_n3.getPosition().x());
        assertEquals(n3.getPosition().y(), c_n3.getPosition().y());
        assertEquals(n3.getPosition().z(), c_n3.getPosition().z());
        assertEquals(n3.getWeight(), c_n3.getWeight());
        assertEquals(n3.getInfo(), c_n3.getInfo());
        assertEquals(n3.getTag(), c_n3.getTag());
    }

    @Test
    void getKey() {
        assertEquals(0, n1.getKey());
        assertEquals(1, n2.getKey());
        assertEquals(2, n3.getKey());
    }

    @Test
    void getPosition() {
        assertTrue(n1.getPosition() instanceof Geo_Location);
        assertEquals(10, n1.getPosition().x());
        assertEquals(10, n1.getPosition().y());
        assertEquals(0, n1.getPosition().z());
        assertTrue(n2.getPosition() instanceof Geo_Location);
        assertEquals(5, n2.getPosition().x());
        assertEquals(5, n2.getPosition().y());
        assertEquals(0, n2.getPosition().z());
        assertTrue(n3.getPosition() instanceof Geo_Location);
        assertEquals(2, n3.getPosition().x());
        assertEquals(2, n3.getPosition().y());
        assertEquals(0, n3.getPosition().z());
    }

    @Test
    void setPosition() {
        Geo_Location pos4 = new Geo_Location(2, 2, 0);
        Geo_Location pos5 = new Geo_Location(10, 10, 0);
        Geo_Location pos6 = new Geo_Location(5, 5, 0);
        n1.setPosition(pos4);
        n2.setPosition(pos5);
        n3.setPosition(pos6);
        assertTrue(n1.getPosition() instanceof Geo_Location);
        assertEquals(2, n1.getPosition().x());
        assertEquals(2, n1.getPosition().y());
        assertEquals(0, n1.getPosition().z());
        assertTrue(n2.getPosition() instanceof Geo_Location);
        assertEquals(10, n2.getPosition().x());
        assertEquals(10, n2.getPosition().y());
        assertEquals(0, n2.getPosition().z());
        assertTrue(n3.getPosition() instanceof Geo_Location);
        assertEquals(5, n3.getPosition().x());
        assertEquals(5, n3.getPosition().y());
        assertEquals(0, n3.getPosition().z());
    }

    @Test
    void getWeight() {
        assertEquals(0, n1.getWeight());
        assertEquals(0, n2.getWeight());
        assertEquals(0, n3.getWeight());
    }

    @Test
    void setWeight() {
        n1.setWeight(2);
        n2.setWeight(3);
        n3.setWeight(4);
        assertEquals(2, n1.getWeight());
        assertEquals(3, n2.getWeight());
        assertEquals(4, n3.getWeight());
    }

    @Test
    void getInfo() {
        String s1 = "\"pos:\" \"10.0,10.0,0.0\",\n\"id:\" 0";
        String s2 = "\"pos:\" \"5.0,5.0,0.0\",\n\"id:\" 1";
        String s3 = "\"pos:\" \"2.0,2.0,0.0\",\n\"id:\" 2";
        assertEquals(s1, n1.getInfo());
        assertEquals(s2, n2.getInfo());
        assertEquals(s3, n3.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(0, n1.getTag());
        assertEquals(0, n2.getTag());
        assertEquals(0, n3.getTag());
    }

    @Test
    void setTag() {
        n1.setTag(1);
        n2.setTag(2);
        n3.setTag(3);
        assertEquals(1, n1.getTag());
        assertEquals(2, n2.getTag());
        assertEquals(3, n3.getTag());
    }
}