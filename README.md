

# 🚀 Crypto Tracker – Spring Boot · Binance API · TimescaleDB · Alerts

A production-grade crypto price monitoring service built with **Spring Boot 3**, **Java 17+**, **TimescaleDB**, **Binance API**, **feature flags**, and **email alerting**.
It tracks selected cryptocurrencies, stores historical prices efficiently, and triggers configurable alerts.

---

# 📊 Features

### ✅ **1. Real-time price fetching (Binance API)**

Fetches live prices using:

```
GET https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT
```

Implemented in:
`BinancePriceService.java`

---

### ✅ **2. Track specific coins (BTC, ETH, SOL...)**

Add/remove/update tracked coins via:

```
POST /api/tracked-coins
GET  /api/tracked-coins
PUT  /api/tracked-coins/{id}
DELETE /api/tracked-coins/{id}
```

Controller:
`TrackedCoinController.java`

---

### ✅ **3. Scheduled alert checker**

Runs every **30 seconds** (configurable):

```
alerts.check-interval-ms=30000
feature.alerts.enabled=true
```

If `currentPrice >= alertPrice`, an alert is fired once and persisted.

Service:
`TrackedCoinAlertService.java`

---

### ✅ **4. Email notifications**

When an alert triggers, an email is sent:

```
alertNotificationService.sendPriceAlert(coin, currentPrice);
```

Configured via:

```
spring.mail.host
spring.mail.username
spring.mail.password
alerts.email.to
```

Service:
`EmailAlertNotificationService.java`

---

### ✅ **5. Historical price storage (TimescaleDB Hypertable)**

Entity:
`CryptoPriceHistory.java`

Repository:
`CryptoPriceHistoryRepository.java`

Spring saves a new row every check, enabling:

✔ fast queries
✔ compression
✔ aggregation
✔ analytics

Hypertable creation:

```sql
SELECT create_hypertable('price_history', 'timestamp', if_not_exists => TRUE);
```

---

### ✅ **6. Full REST API for price history**

```
GET /api/prices/all
GET /api/prices/latest
GET /api/prices/recent
```

Controller:
`CryptoPriceController.java`

---

### ✅ **7. OpenAPI / Swagger UI enabled**

Launch:

```
http://localhost:8080/v3/api-docs
http://localhost:8080/swagger-ui/index.html
```

---

### ✅ **8. Ready for Observability**

Using:

✔ OpenTelemetry (traces)
✔ Micrometer
✔ Logging exporter (dev mode)

Configuration:

```
management.tracing.enabled=true
management.tracing.sampling.probability=1.0
logging.level.io.micrometer.tracing=DEBUG
```

---

### ✅ **9. Dockerized TimescaleDB**

Run:

```powershell
docker run -d ^
  --name timescaledb ^
  -p 5432:5432 ^
  -e POSTGRES_PASSWORD=postgres ^
  -e POSTGRES_DB=crypto_db ^
  timescale/timescaledb:latest-pg16
```

---

# 🧱 Architecture

```
src/main/java/com.fwkproject.crypto_tracker
│
├── controller/
│   ├── CryptoController
│   ├── CryptoPriceController
│   ├── TrackedCoinController
│   └── HomeController
│
├── model/
│   ├── TrackedCoin
│   ├── CryptoPriceHistory
│   ├── CryptoPrice
│   └── CryptoPriceId
│
├── service/
│   ├── BinancePriceService
│   ├── TrackedCoinAlertService
│   ├── AlertNotificationService
│   ├── EmailAlertNotificationService
│   ├── CryptoPriceHistoryService
│   └── CryptoPriceService
│
└── repository/
    ├── TrackedCoinRepository
    ├── CryptoPriceRepository
    └── CryptoPriceHistoryRepository
```

---

# ⚙️ Configuration Highlights

### **Application properties**

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/crypto_db
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Alerts
feature.alerts.enabled=true
alerts.check-interval-ms=30000
alerts.email.to=your-email@example.com

# Email (example Gmail)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=YOUR_SMTP_USERNAME
spring.mail.password=YOUR_SMTP_PASSWORD
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

# ▶️ Running the App

### **Start backend**

```
mvn spring-boot:run
```

### **Start TimescaleDB**

```
docker start timescaledb
```

### **Access Swagger UI**

```
http://localhost:8080/swagger-ui/index.html
```

---

# 🚀 Next Features

Here are the next steps we can add:

### 🔥 1. WebSockets for real-time updates

### 🔥 2. Frontend dashboard (Vue, React, or Angular)

### 🔥 3. Candlestick aggregation (1m, 5m, 1h)

### 🔥 4. Telegram bot alerts

### 🔥 5. CPU/memory tracing with OpenTelemetry Exporter

### 🔥 6. Native AOT build + Docker optimized

---
