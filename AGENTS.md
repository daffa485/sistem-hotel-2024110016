# Konteks Project: Aplikasi Perhotelan (Scope UTS - CRUD Master)

## Tech Stack & Environment
- **IDE & Build System**: Apache NetBeans (Java dengan Maven/Ant sesuai contoh project)
- **UI Framework**: JavaFX (FXML + Scene Builder + CSS)
- **Database**: MySQL / MariaDB (JDBC Driver)
- **Fokus UTS**: CRUD 4 Tabel Master (`tipe_kamar`, `rate_kamar`, `kamar`, `customer`)

## Aturan Referensi Kode (WAJIB DIIKUTI SEMUA AGENT)
Sebelum membuat atau memodifikasi file apa pun, setiap agent WAJIB memeriksa pola kode pada project referensi di:
- Direktori Contoh Project: C:\Daffa\Kuliah\Semester 5\Pemrograman Berorientasi Objek Lanjut\ProjectPenjualan\src\projectpenjualan
- Skema Database: C:\Daffa\Kuliah\Semester 5\Pemrograman Berorientasi Objek Lanjut\SistemHotel\sql\query-hotel.sql

Ikuti 100% gaya arsitektur dari project referensi `ProjectPenjualan`, meliputi:
1. Struktur penamaan package (misal: `config`, `model`, `dao`, `controller`, `view`).
2. Cara penulisan kelas koneksi database (`Koneksi.java` / `DatabaseConnection.java`).
3. Pola penulisan metode CRUD (`insert`, `update`, `delete`, `getAll`, `search`) dan penanganan `SQLException`.
4. Pola pendeklarasian komponen `@FXML`, inisialisasi `TableView` (`PropertyValueFactory`), dan dialog `Alert` JavaFX.
