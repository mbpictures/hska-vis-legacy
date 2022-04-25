CREATE USER 'productuser' IDENTIFIED BY 'dj2342kjdlkfjl2j234';
CREATE USER 'categoryuser' IDENTIFIED BY 'akj34k3jdjfk3435jd3';

CREATE DATABASE product;

CREATE DATABASE category;

GRANT ALL PRIVILEGES ON 'product'.* TO 'productuser';
GRANT ALL PRIVILEGES ON 'category'.* TO 'categoryuser';

CREATE TABLE category.category (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE role (
	id INT NOT NULL AUTO_INCREMENT,
	level1 INT,
	type VARCHAR(255),
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE product.product (
	id INT NOT NULL AUTO_INCREMENT,
	details VARCHAR(255),
	name VARCHAR(255),
	price DOUBLE,
	category_id INT,
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE customer (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	lastname VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL,
	role INT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_mufchskagt7e1w4ksmt9lum5l ON customer (username ASC);

CREATE INDEX FK74aoh99stptslhotgf41fitt0 ON customer (role ASC);

CREATE INDEX FK1mtsbur82frn64de7balymq9s ON product (category_id ASC);
