package com.maple.services.thread;

/**
 * desc: Expresses
 *
 * @author hz.lei
 * @since 2018年08月03日 下午11:30
 */
public class Expresses {

    private int id;
    private String name;

    public Expresses(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Expresses{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
