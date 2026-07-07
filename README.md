# WiFi Subscription Management

Aplikasi desktop berbasis JavaFX untuk mengelola langganan WiFi. Dibuat sebagai project UAS.

## Fitur

- **Login** — autentikasi pengguna dengan role `admin` dan `customer`
- **Dashboard** — menampilkan data customer beserta langganannya
- **Manajemen Customer** — CRUD data customer (nama, email, telepon, alamat)
- **Manajemen Subscription** — CRUD langganan WiFi yang terhubung dengan data customer

## Tech Stack

| Komponen    | Teknologi                     |
|-------------|-------------------------------|
| Bahasa      | Java                          |
| UI          | JavaFX + FXML + CSS           |
| Database    | MariaDB / MySQL               |
| Build       | Apache Ant (NetBeans)         |
| Container   | Docker Compose                |

## Struktur Project

```
src/
├── main/
│   └── App.java              # Entry point & navigasi
├── controllers/
│   ├── LoginController.java
│   ├── DashboardController.java
│   ├── DashboardContentController.java
│   ├── CustomerController.java
│   └── SubscriptionController.java
├── models/
│   ├── User.java
│   ├── Customer.java
│   ├── Subscription.java
│   └── Dashboard.java
├── views/
│   ├── login.fxml
│   ├── dashboard.fxml
│   ├── dashboard_content.fxml
│   ├── customers.fxml
│   └── subscriptions.fxml
├── database/
│   └── DBHelper.java         # Koneksi DB & auto-create schema
└── styles/
    ├── login_style.css
    ├── dashboard_style.css
    ├── customer_style.css
    └── subscription_style.css
```

## Database

Tabel dibuat otomatis saat aplikasi pertama kali dijalankan:

- **users** — `user_id`, `username`, `password`, `role`
- **customers** — `customer_id`, `name`, `email`, `phone`, `address`
- **subscriptions** — `subscription_id`, `customer_id` (FK), `plan_name`, `price`, `start_date`, `end_date`

### Akun Default

| Username | Password  | Role     |
|----------|-----------|----------|
| admin    | admin123  | admin    |
| cust     | cust123   | customer |

## Cara Menjalankan

### 1. Jalankan Database

```bash
docker-compose up -d
```

Database akan tersedia di `localhost:31230`.

### 2. Jalankan Aplikasi

Buka project di **NetBeans** atau IDE lain yang mendukung JavaFX, lalu jalankan `App.java`.

> **Catatan:** Pastikan koneksi database di `src/database/DBHelper.java` sesuai dengan konfigurasi Docker.
