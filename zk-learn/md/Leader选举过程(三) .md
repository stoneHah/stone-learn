## Leader选举的实现细节

### 服务器状态

`org.apache.zookeeper.server.quorum.QuorumPeer.ServerState`类中描述了服务器的状态

+ LOOKING
+ FOLLOWING
+ LEADING
+ OBSERVING

### 投票数据结构

