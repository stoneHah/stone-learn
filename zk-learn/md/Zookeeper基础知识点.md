## 基础知识点

### 用途

`Zookeeper作为一个分布式服务框架，主要用来解决分布式数据一致性的问题`

### 节点类型

+ 持久节点
+ 临时节点
+ 持久有序节点
+ 临时有序节点



### 增删改查

+ 创建

  create [-s] [-e] path data acl

  -s 顺序节点

  -e 临时节点

  data参数需要制定，不然节点创建不了。ps：不指定data控制台也没提示创建失败信息

+ 读取

  + ls path [watch]

  + get path [watch]

    ```properties
    123  # 节点数据
    cZxid = 0x2cf  # 创建节点的事务id
    ctime = Fri Jun 28 21:20:34 CST 2019
    mZxid = 0x2cf  # 更新节点的事务id
    mtime = Fri Jun 28 21:20:34 CST 2019
    pZxid = 0x2cf  
    cversion = 0   
    dataVersion = 0   # 数据版本号
    aclVersion = 0
    ephemeralOwner = 0x0
    dataLength = 3
    numChildren = 0
    
    ```

+ 更新

  set path data [version]

  ​	这边的version类似乐观锁的概念，如果不指定version，则会制定对数据进行更新

+ 删除

  delete path [version]

  ​	需要注意的是，这里指定的path是在其下没有子节点的情况下才可以进行删除