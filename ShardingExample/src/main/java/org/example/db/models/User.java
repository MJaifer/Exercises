package org.example.db.models;

import org.example.db.shard.ShardableData;

/**
 * Data model for User
 */
public class User implements ShardableData {
    private int id;
    private String name;
    private String location;

    public User(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + ' ' +
                ", location='" + location + ' ' +
                '}';
    }
}
