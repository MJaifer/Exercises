package org.example.db.shard;

import java.util.concurrent.ExecutionException;

public interface Connection {
    public <T extends ShardableData> void save(T data) throws ExecutionException, InterruptedException;
    public <T extends ShardableData> ShardableData getData(int id, String type) throws ExecutionException, InterruptedException;
    public void close();
}
