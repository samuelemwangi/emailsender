CREATE DATABASE IF NOT EXISTS mail_manager;

USE mail_manager;

CREATE TABLE IF NOT EXISTS email_types (
  id INT AUTO_INCREMENT PRIMARY KEY,
  type VARCHAR(50),
  description VARCHAR(150),
  created_at TIMESTAMP,
  created_by VARCHAR(36),
  updated_at TIMESTAMP,
  updated_by VARCHAR(36),
  deleted_at TIMESTAMP
);
