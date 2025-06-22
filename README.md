# InstaVerse
## Description

A distributed, highly available large-scale microservices blog system, which covers a wide range of popular technology stacks and personally written components, featuring note publishing, likes, favorites, following, and more. The platform is designed to handle massive user traffic with high-concurrency read/write and strong data consistency, ensuring real-time user interactions. High availability and scalability are achieved through a distributed microservices architecture.

---

## Architecture

- **Microservices Distributed Architecture**
- **Maven Multi-Module**
- **Spring Boot Starter Components**
- **Frontend-Backend Separation**

---

## Tech Stack

- **Spring Cloud Alibaba**
- **Nacos**
- **Gateway**
- **Feign**
- **Mybatis**
- **MySQL**
- **Redis**
- **RocketMQ**
- **SaToken**
- **Minio**
- **Cassandra**
- **Zookeeper**


## Core Responsibilities

### Platform Design
- Led requirements analysis, technology selection, architecture design, project setup, API and database schema design.
- Built a microservices architecture from scratch, ensuring high scalability, availability, and performance to support massive user operations.

### Component & Utility Packaging
- Led the modularization of common functionalities to improve code reusability and maintainability.
- Developed user context components, API aspect logging, Jackson auto-configuration, global exception handling, custom parameter validation annotations, response utilities, etc.

### Service Decomposition
- Split services by business domain and responsibility boundaries: gateway, authentication, user, note, user relationship, counter, OSS storage, distributed ID, KV short text storage, etc.
- Ensured each microservice has a single responsibility, low coupling, and supports independent deployment and horizontal scaling.

### Gateway Service
- Built and developed the Gateway service for unified routing and load balancing.
- Implemented unified authentication with SaToken; global filters parse tokens and pass user IDs downstream.

### Authentication Service
- Built authentication service based on RBAC, using SaToken for JWT login (account/password & SMS), logout, self-registration, password change.
- Integrated Aliyun SMS, custom thread pool for async SMS sending, and anti-abuse for SMS APIs.

### Distributed ID Generation Service
- Integrated Meituan Leaf for distributed ID generation, providing both segment and Snowflake ID modes via Feign.
- JMeter tested: 22,000+ QPS per node, 4ms average latency, 1.9+ billion IDs/day.

### OSS Object Storage Service
- Built object storage for images/videos using factory + strategy patterns for extensibility (Aliyun OSS, Minio).
- Used Nacos for distributed config and dynamic registration of file strategy beans.

### KV Short Text Storage Service
- Built KV storage service on Apache Cassandra (RocksDB engine) for short text (notes, comments).

### User Service
- Designed and implemented user service for profile management, registration, query, password update (BCrypt with random salt).
- Built Redis + Caffeine two-level cache for high-concurrency reads, preventing cache avalanche, penetration, and breakdown.

### Note Service
- Designed and implemented note service for publishing, editing, querying, pinning, permission changes.
- Used CompletableFuture for concurrent downstream calls to reduce response time.
- Two-level cache for high-concurrency note detail queries.
- Used RocketMQ for cache invalidation on update/delete to ensure data consistency.
- High-concurrency like: Redis Bloom filter (later Roaring Bitmap), Redis ZSET + MQ async persistence, RateLimiter for traffic shaping.

### User Relationship Service
- Designed and implemented user relationship service for follow/unfollow with high-concurrency writes.
- Used Redis cache + ordered MQ for async DB writes, Lua scripts for atomicity, and unique index for idempotency.
- Follower/following list queries use Redis ZSET with different caching strategies for high-concurrency paginated reads.

### Counter Service
- Designed and implemented high-concurrency counter service for user follows, fans, likes, etc.
- Consumed MQ user actions, used BufferTrigger for batch aggregation (e.g., 1000 per batch, 1s interval), wrote to Redis then async to DB.

### Data Alignment Service
- Designed and implemented compensation-based data alignment for eventual consistency.
- Used XXL-Job for scheduled tasks, pre-created daily increment tables, consumed MQ for daily changes, and sharded broadcast tasks for efficient large-scale processing.

### Search Service
- Designed note/user indexes, built full indexes with logstash.
- Used Canal to listen to MySQL, custom Binlog event handling for real-time Elasticsearch index updates.
- Developed search APIs with Chinese word segmentation, keyword highlighting, dimension filtering, and custom scoring.

### Comment Service
- Table design with redundant fields for efficient queries (e.g., earliest reply ID, sub-comment count).
- MQ producer reliability: Spring Retry, fallback DB write, async failed message handling.
- Efficient async comment publishing, triggers for hotness and count updates.
- Batch MQ consumption and DB writes for high-throughput.
- Paginated comment queries with Redis/local two-level cache.
- Like/unlike/delete for comments: MQ async, batch consumption/writes, in-memory merge for high-performance.

---
