PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS laundry_orders;

CREATE TABLE laundry_orders (
                                id            INTEGER PRIMARY KEY AUTOINCREMENT,
                                customer_name TEXT NOT NULL,
                                weight_kg     REAL NOT NULL,
                                total_amount  REAL NOT NULL,
                                status        TEXT NOT NULL
                                    CHECK (status IN ('RECEIVED', 'WASHING', 'READY'))
);

INSERT INTO laundry_orders (customer_name, weight_kg, total_amount, status)
VALUES ('Juan Dela Cruz', 8.5, 250.00, 'WASHING');

INSERT INTO laundry_orders (customer_name, weight_kg, total_amount, status)
VALUES ('Maria Santos', 5.0, 160.00, 'READY');

INSERT INTO laundry_orders (customer_name, weight_kg, total_amount, status)
VALUES ('Pedro Penduko', 12.0, 360.00, 'RECEIVED');

SELECT * FROM laundry_orders;