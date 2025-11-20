---

# 📈 Crypto Tracker – Spring Boot + PostgreSQL + TimescaleDB

A real-time Bitcoin price tracker powered by Spring Boot, Binance API, PostgreSQL, and TimescaleDB hypertables.

This application fetches and stores **BTC/USDT** price every 10 seconds, saving it into a TimescaleDB hypertable for optimized time-series storage and future analytics.

---

## 🚀 Features Implemented

### ✅ 1. Scheduled Price Fetching

A Spring `@Scheduled` task runs every **10 seconds**, calling the official **Binance API**:

```
GET https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT
```

The current BTC price is retrieved and stored in the database.

---

### ✅ 2. TimescaleDB Hypertable Storage

Your database is powered by **TimescaleDB**, which automatically optimizes time-series inserts.

Structure:

| Column | Type        | Description        |
| ------ | ----------- | ------------------ |
| time   | timestamptz | Primary key part 1 |
| symbol | text        | Primary key part 2 |
| price  | double      | Stored BTC price   |

---

### ✅ 3. Composite Primary Key

Each row is uniquely identified by:

* `Instant time`
* `String symbol`

This is implemented using `@Embeddable`:

```java
@Embeddable
public class CryptoPriceId implements Serializable {
    private Instant time;
    private String symbol;
}
```

And embedded in the entity:

```java
@Entity
public class CryptoPrice {
    @EmbeddedId
    private CryptoPriceId id;
    private double price;
}
```

Spring/Hibernate errors were fixed by adding:

* No-args constructor
* Serializable implementation
* Correct JPQL path (`c.id.symbol` instead of `c.symbol`)

---

### ✅ 4. JPA Repository with Custom Query

```java
@Query("SELECT c FROM CryptoPrice c WHERE c.id.symbol = :symbol ORDER BY c.id.time DESC")
CryptoPrice findTopBySymbolOrderByTimeDesc(String symbol);
```

Spring adds the `LIMIT 1` automatically.

---

### ✅ 5. REST Endpoints Implemented

| Method | Endpoint         | Description                       |
| ------ | ---------------- | --------------------------------- |
| GET    | `/prices`        | Get all stored prices             |
| GET    | `/prices/latest` | Get latest BTC/USDT price         |
| POST   | `/prices`        | Manually insert a price (testing) |

---

### ✅ 6. Dockerized PostgreSQL / TimescaleDB

Your running container:

```
docker run --name timescale_crypto ^
  -e POSTGRES_PASSWORD=postgres ^
  -e POSTGRES_DB=crypto_db ^
  -p 5432:5432 ^
  -d timescale/timescaledb-ha:pg15-latest
```

Inside psql:

```
CREATE DATABASE crypto_db;
\c crypto_db;

CREATE TABLE crypto_prices (
    time TIMESTAMPTZ NOT NULL,
    symbol TEXT NOT NULL,
    price DOUBLE PRECISION,
    PRIMARY KEY (time, symbol)
);

SELECT create_hypertable('crypto_prices', 'time', if_not_exists => TRUE);
```

---

## 🧱 Project Structure

```
src/main/java/com/fwkproject/crypto_tracker
│
├─ controller/
│   └── CryptoPriceController.java
│
├─ service/
│   └── CryptoPriceService.java
│
├─ repository/
│   └── CryptoPriceRepository.java
│
└─ model/
    ├── CryptoPrice.java
    └── CryptoPriceId.java
```

---

## 🔧 Technologies Used

* **Java 23**
* **Spring Boot 3.5**
* **Spring Data JPA**
* **TimescaleDB (PostgreSQL)**
* **RestTemplate (Binance API)**
* **Docker**
* **Lombok**

---

## 📊 Current State

You now have a **fully working backend** that:

✔ Fetches BTC price every 10 seconds
✔ Stores each data point efficiently
✔ Exposes REST endpoints
✔ Runs on Dockerized TimescaleDB
✔ Uses a correct composite primary key
✔ Avoids all JPA/Hibernate errors

---

## 🛠️ Next Steps (if you want)

I can help you implement:

### 🔥 1. Real-time WebSocket price updates

Allow frontend charts to update instantly.

### 🔥 2. Frontend dashboard (React or Vue)

Live price chart (line, candlestick), latest value, historical stats.

### 🔥 3. Support for multiple coins

BTC, ETH, SOL, DOGE, etc.

### 🔥 4. Price aggregation

* 1m, 5m, 1h candles
* OHLCV generation
* Moving averages (MA/EMA)

### 🔥 5. Alerts (email, SMS, Telegram)

Trigger alert when price crosses a threshold.

---

