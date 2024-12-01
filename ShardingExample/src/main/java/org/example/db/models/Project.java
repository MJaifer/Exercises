package org.example.db.models;

import org.example.db.shard.ShardableData;

/**
 * Data model for Project
 */
public class Project implements ShardableData {
    int id;
    String label;
    User owner;

    public Project(int id, String label, User owner) {
        this.id = id;
        this.label = label;
        this.owner = owner;
    }

    public String getLabel() {
        return label;
    }

    public User getOwner() {
        return owner;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", label=" + label +
                ", owner=" + owner.getName() +
                '}';
    }
}
