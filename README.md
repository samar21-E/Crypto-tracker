# Crypto Tracker

A robust, cloud-native crypto tracking application built with **Spring Boot 3** and **Pyramidal Architecture**.

## 🚀 Features

*   **Real-time Tracking**: Monitor crypto prices (e.g., BTCUSDT) via Binance API.
*   **Price Alerts**: Set thresholds and receive email notifications when triggered.
*   **Time-Series Data**: Efficiently store price history in **TimescaleDB**.
*   **Observability**: Full tracing with **OpenTelemetry**.
*   **Feature Flags**: Enable/Disable features dynamically using **Togglz**.
*   **Cloud Native**: **K0s** / Kubernetes ready with native manifests.
*   **AOT Ready**: Compiles to a native executable using GraalVM.

## 🏗 Architecture

The project follows a **Pyramidal (Clean) Architecture**:

*   **`domain`**: Pure business entities (`TrackedCoin`, `CryptoPrice`) and repository interfaces. No Spring dependencies.
*   **`application`**: Business logic services (`CryptoPriceService`, `TrackedCoinService`) and ports (`NotificationPort`).
*   **`infrastructure`**: Adapters for persistence (JPA), external APIs (Binance), and configuration.

## 🛠 Technology Stack

1.  **OpenTelemetry**: Distributed tracing.
2.  **K0s / Kubernetes**: Deployment manifests included.
3.  **TimescaleDB**: PostgreSQL optimized for time-series data.
4.  **Feature Flags**: Managed via Togglz.
5.  **AOT Native Compiler**: Built with GraalVM.

## 🏃‍♂️ How to Run

### Prerequisites
*   Java 17+
*   Maven 3.8+
*   PostgreSQL / TimescaleDB running on port 5432.

### 1. Start Database
```bash
docker run -d --name timescaledb -p 5432:5432 -e POSTGRES_PASSWORD=password timescale/timescaledb:latest-pg14
```

### 2. Configure Email (Optional)
Add to `application.properties` or environment variables:
```properties
spring.mail.host=smtp.gmail.com
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
alerts.email.to=recipient@example.com
```

### 3. Run Locally
```bash
mvn spring-boot:run
```

### 4. Build Native Image
```bash
mvn -Pnative native:compile
```

## 🧪 API Endpoints

*   **POST** `/api/tracked-coins`: Track a new coin `{ "symbol": "ETHUSDT", "alertPrice": 3000.0 }`.
*   **GET** `/api/crypto-prices/latest?symbol=ETHUSDT`: Get latest price.
*   **GET** `/actuator/togglz`: Manage feature flags.

## ☸️ Deploy to K0s
```bash
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```
