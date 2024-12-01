package org.example.db.database;

import org.example.db.shard.ShardableData;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabaseServer<T extends ShardableData> {
    private final Map<String, Map<Integer, T>> storage = new HashMap<>();
    private int id;

    public InMemoryDatabaseServer(int id) {
        this.id = id;
    }

    public synchronized void saveData(T data) {
        System.out.println("saveData --> DBNode: " + this.id);
        System.out.println("Saving data: " + data + "\n");
        String type = data.getClass().getName();
        storage.putIfAbsent(type, new HashMap<>());
        storage.get(type).put(data.getId(), data);
    }

    public synchronized T getData(int id, String type) {
        System.out.println("getData --> DBNode: " + this.id);
        System.out.println("id: " + id + ", type: " + type);
        if (storage.containsKey(type)) {
            T data = storage.get(type).get(id);
            System.out.println("Retrieving data: " + data + "\n");
            return data;
        }
        System.out.println("Data not found!\n");
        return null;
    }
}
