-- 1. Table Master: Tipe Kamar
CREATE TABLE IF NOT EXISTS tipe_kamar (
    id_tipe VARCHAR(10) PRIMARY KEY,
    nama_tipe VARCHAR(50) NOT NULL,
    kapasitas INT NOT NULL DEFAULT 2,
    deskripsi_fasilitas TEXT,
    gambar VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Table Master: Rate Kamar (Rencana Tarif)
CREATE TABLE IF NOT EXISTS rate_kamar (
    id_rate VARCHAR(10) PRIMARY KEY,
    id_tipe VARCHAR(10) NOT NULL,
    nama_rate VARCHAR(50) NOT NULL,
    harga_per_malam DECIMAL(12, 2) NOT NULL,
    keterangan VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_rate_tipe FOREIGN KEY (id_tipe) REFERENCES tipe_kamar(id_tipe) ON DELETE CASCADE
);

-- 3. Table Master: Kamar
CREATE TABLE IF NOT EXISTS kamar (
    nomor_kamar VARCHAR(10) PRIMARY KEY,
    id_tipe VARCHAR(10) NOT NULL,
    lantai INT NOT NULL,
    status ENUM('AVAILABLE', 'OCCUPIED', 'CLEANING', 'MAINTENANCE') NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_kamar_tipe FOREIGN KEY (id_tipe) REFERENCES tipe_kamar(id_tipe) ON DELETE RESTRICT
);

-- 4. Table Master: Customer (Data Tamu)
CREATE TABLE IF NOT EXISTS customer (
    id_customer VARCHAR(20) PRIMARY KEY,
    nomor_identitas VARCHAR(50) NOT NULL UNIQUE,
    nama_lengkap VARCHAR(100) NOT NULL,
    no_telepon VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    alamat TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. Table Transaksi: Check-in & Check-out
CREATE TABLE IF NOT EXISTS transaksi_checkin (
    id_transaksi VARCHAR(20) PRIMARY KEY,
    id_customer VARCHAR(20) NOT NULL,
    nomor_kamar VARCHAR(10) NOT NULL,
    id_rate VARCHAR(10) NOT NULL,
    waktu_checkin DATETIME NOT NULL,
    rencana_checkout DATETIME NOT NULL,
    aktual_checkout DATETIME NULL,
    tarif_per_malam DECIMAL(12, 2) NOT NULL,
    deposit DECIMAL(12, 2) DEFAULT 0.00,
    total_biaya DECIMAL(12, 2) NULL,
    status_transaksi ENUM('ACTIVE', 'COMPLETED', 'CANCELLED') NOT NULL DEFAULT 'ACTIVE',
    catatan VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_trans_customer FOREIGN KEY (id_customer) REFERENCES customer(id_customer),
    CONSTRAINT fk_trans_kamar FOREIGN KEY (nomor_kamar) REFERENCES kamar(nomor_kamar),
    CONSTRAINT fk_trans_rate FOREIGN KEY (id_rate) REFERENCES rate_kamar(id_rate)
);

-- ========================================================
-- DATA AWAL (SEED DATA UNTUK PENGUJIAN)
-- ========================================================
INSERT INTO tipe_kamar (id_tipe, nama_tipe, kapasitas, deskripsi_fasilitas, gambar) VALUES
('T01', 'Standard Room', 2, 'Single/Double Bed, AC, TV 32 inch, Kamar Mandi Dalam', 'standard-room.jpg'),
('T02', 'Deluxe Room', 2, 'King Bed, AC, Smart TV 43 inch, Minibar, Bathtub', 'deluxe-room.jpg'),
('T03', 'Suite Room', 4, 'Super King Bed, Ruang Tamu, Kitchenette, Jacuzzi, Balcony', 'suite-room.jpg')
ON DUPLICATE KEY UPDATE nama_tipe=VALUES(nama_tipe), kapasitas=VALUES(kapasitas), deskripsi_fasilitas=VALUES(deskripsi_fasilitas), gambar=VALUES(gambar);

INSERT INTO rate_kamar (id_rate, id_tipe, nama_rate, harga_per_malam, keterangan) VALUES
('R01', 'T01', 'Standard Publish Rate', 350000.00, 'Termasuk sarapan untuk 1 orang'),
('R02', 'T02', 'Deluxe Publish Rate', 550000.00, 'Termasuk sarapan untuk 2 orang'),
('R03', 'T03', 'Suite Luxury Rate', 1200000.00, 'Termasuk breakfast buffet & free spa')
ON DUPLICATE KEY UPDATE nama_rate=VALUES(nama_rate), harga_per_malam=VALUES(harga_per_malam), keterangan=VALUES(keterangan);

INSERT INTO kamar (nomor_kamar, id_tipe, lantai, status) VALUES
('101', 'T01', 1, 'AVAILABLE'),
('102', 'T01', 1, 'OCCUPIED'),
('201', 'T02', 2, 'AVAILABLE'),
('202', 'T02', 2, 'CLEANING'),
('301', 'T03', 3, 'AVAILABLE')
ON DUPLICATE KEY UPDATE status=VALUES(status);

INSERT INTO customer (id_customer, nomor_identitas, nama_lengkap, no_telepon, email, alamat) VALUES
('C001', '3273012345670001', 'Budi Santoso', '081234567890', 'budi.santoso@example.com', 'Jl. Merdeka No. 10, Bandung'),
('C002', '3273012345670002', 'Siti Aminah', '081398765432', 'siti.aminah@example.com', 'Jl. Sudirman No. 45, Jakarta'),
('C003', '3273012345670003', 'Ahmad Dhani', '081987654321', 'ahmad.dhani@example.com', 'Jl. Diponegoro No. 8, Surabaya')
ON DUPLICATE KEY UPDATE nama_lengkap=VALUES(nama_lengkap), no_telepon=VALUES(no_telepon), email=VALUES(email), alamat=VALUES(alamat);

-- ========================================================
-- REFERENSI QUERY CRUD & JOIN PREPARED STATEMENT
-- ========================================================
-- 1. Tipe Kamar:
--    SELECT: SELECT id_tipe, nama_tipe, kapasitas, deskripsi_fasilitas, gambar FROM tipe_kamar
--    SEARCH: SELECT id_tipe, nama_tipe, kapasitas, deskripsi_fasilitas, gambar FROM tipe_kamar WHERE id_tipe LIKE ? OR nama_tipe LIKE ?
--    INSERT: INSERT INTO tipe_kamar (id_tipe, nama_tipe, kapasitas, deskripsi_fasilitas, gambar) VALUES (?, ?, ?, ?, ?)
--    UPDATE: UPDATE tipe_kamar SET nama_tipe = ?, kapasitas = ?, deskripsi_fasilitas = ?, gambar = ? WHERE id_tipe = ?
--    DELETE: DELETE FROM tipe_kamar WHERE id_tipe = ?

-- 2. Rate Kamar (JOIN tipe_kamar):
--    SELECT: SELECT r.id_rate, r.id_tipe, t.nama_tipe, r.nama_rate, r.harga_per_malam, r.keterangan FROM rate_kamar r JOIN tipe_kamar t ON r.id_tipe = t.id_tipe
--    SEARCH: SELECT r.id_rate, r.id_tipe, t.nama_tipe, r.nama_rate, r.harga_per_malam, r.keterangan FROM rate_kamar r JOIN tipe_kamar t ON r.id_tipe = t.id_tipe WHERE r.id_rate LIKE ? OR r.nama_rate LIKE ? OR t.nama_tipe LIKE ?
--    INSERT: INSERT INTO rate_kamar (id_rate, id_tipe, nama_rate, harga_per_malam, keterangan) VALUES (?, ?, ?, ?, ?)
--    UPDATE: UPDATE rate_kamar SET id_tipe = ?, nama_rate = ?, harga_per_malam = ?, keterangan = ? WHERE id_rate = ?
--    DELETE: DELETE FROM rate_kamar WHERE id_rate = ?

-- 3. Kamar (JOIN tipe_kamar):
--    SELECT: SELECT k.nomor_kamar, k.id_tipe, t.nama_tipe, k.lantai, k.status FROM kamar k JOIN tipe_kamar t ON k.id_tipe = t.id_tipe
--    SEARCH: SELECT k.nomor_kamar, k.id_tipe, t.nama_tipe, k.lantai, k.status FROM kamar k JOIN tipe_kamar t ON k.id_tipe = t.id_tipe WHERE k.nomor_kamar LIKE ? OR t.nama_tipe LIKE ? OR k.status LIKE ?
--    INSERT: INSERT INTO kamar (nomor_kamar, id_tipe, lantai, status) VALUES (?, ?, ?, ?)
--    UPDATE: UPDATE kamar SET id_tipe = ?, lantai = ?, status = ? WHERE nomor_kamar = ?
--    DELETE: DELETE FROM kamar WHERE nomor_kamar = ?

-- 4. Customer:
--    SELECT: SELECT id_customer, nomor_identitas, nama_lengkap, no_telepon, email, alamat FROM customer
--    SEARCH: SELECT id_customer, nomor_identitas, nama_lengkap, no_telepon, email, alamat FROM customer WHERE id_customer LIKE ? OR nomor_identitas LIKE ? OR nama_lengkap LIKE ? OR no_telepon LIKE ?
--    INSERT: INSERT INTO customer (id_customer, nomor_identitas, nama_lengkap, no_telepon, email, alamat) VALUES (?, ?, ?, ?, ?, ?)
--    UPDATE: UPDATE customer SET nomor_identitas = ?, nama_lengkap = ?, no_telepon = ?, email = ?, alamat = ? WHERE id_customer = ?
--    DELETE: DELETE FROM customer WHERE id_customer = ?