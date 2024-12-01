package org.example.db.factory;

import org.example.db.shard.Connection;
import org.example.db.shard.ShardManager;

public class DBConnectionFactory {
    private static ShardManager connection;

    /**
     * Returns Singleton instance of ShardManager implementation if Connection
     */
    public static Connection getConnection() {
        if (connection != null) return connection;
        return connection = new ShardManager();
    }
}
