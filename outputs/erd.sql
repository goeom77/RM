use service;
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    login_id VARCHAR(13) NOT NULL,
    password VARCHAR(255) NOT NULL,
	name VARCHAR(255) NOT NULL,
    uuid VARCHAR(36) NOT NULL
);

CREATE TABLE rm_service (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_type VARCHAR(10) NOT NULL,
    storage_size DOUBLE NOT NULL,
	limit_user INT NOT NULL,
    created_date DATETIME ,
    expiration_date DATETIME,
	nick_name VARCHAR(30) NOT NULL,
    company VARCHAR(100),
    phone_number VARCHAR(15),
    email VARCHAR(255),
    address VARCHAR(255)
);

CREATE TABLE data_usage (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    manager_id BIGINT NOT NULL,
    status BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (service_id) REFERENCES rm_service(id)
);

CREATE TABLE log_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    storage_usage DOUBLE,
    expiration_date DATETIME,
    service_id BIGINT NOT NULL,
    created_date DATETIME,
    updated_date DATETIME,
	FOREIGN KEY (service_id) REFERENCES rm_service(id)
);

CREATE TABLE measured_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_storage_data DOUBLE,
    total_expiration_date DATETIME,
    service_id BIGINT,
    payable BOOLEAN DEFAULT FALSE,
    created_date DATETIME,
    updated_date DATETIME,
    FOREIGN KEY (service_id) REFERENCES rm_service(id)
);