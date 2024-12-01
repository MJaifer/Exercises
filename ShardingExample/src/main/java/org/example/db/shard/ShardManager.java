package org.example.db.shard;

import org.example.db.database.InMemoryDatabaseServer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Implementation of Connection
 */
public class ShardManager implements Connection {
    private int numServers = 3;     // No:of DB Nodes (hardcoded value)
    private final List<ExecutorService> serverPools;    // one thread for each DB Node
    private final List<InMemoryDatabaseServer> dbServers;   // DB Nodes

    public ShardManager() {
        serverPools = new ArrayList<>();
        dbServers = new ArrayList<>();

        /**
         * Instantiating DB Nodes and corresponding threads
         */
        for (int i = 0; i < numServers; i++) {
            serverPools.add(Executors.newSingleThreadExecutor());
            dbServers.add(new InMemoryDatabaseServer(i));
        }
    }

    /**
     * objectId capped at total no of servers
     */
    private int getShardId(int shardHash) {
        return shardHash % dbServers.size();
    }

    @Override
    public <T extends ShardableData> void save(T data) throws ExecutionException, InterruptedException {
        // get id of DB Node using hash function
        int shardId = getShardId(data.getId());
        // save data using dedicated thread
        Future future = serverPools.get(shardId).submit(() -> {
            dbServers.get(shardId).saveData(data);
        });
        future.get();
    }

    @Override
    public <T extends ShardableData> ShardableData getData(int id, String type) throws ExecutionException, InterruptedException {
        // get id of DB Node using hash function
        int shardId = getShardId(id);
        // retrieve data using dedicated thread
        Future<ShardableData> future = serverPools.get(shardId).submit(() -> dbServers.get(shardId).getData(id, type));
        return future.get();
    }

    @Override
    public void close() {
        for (ExecutorService pool : serverPools) {
            pool.shutdown();
        }
    }
}
