DROP TABLE business_categories;
DROP TABLE business_photos;
DROP TABLE business_perfil;
DROP TABLE business_map;
DROP TABLE business_contact;
DROP TABLE business;

DROP TABLE user_info;
DROP TABLE user;

DROP TABLE base_business_photos;
DROP TABLE base_business_categories;

CREATE TABLE user (
	username 	VARCHAR(100) NOT NULL, 
	password 	VARCHAR(60) NOT NULL, 

	PRIMARY KEY (username)
);

CREATE TABLE user_info (
	username 	VARCHAR(100) NOT NULL, 
	firstname 	VARCHAR(80) NOT NULL, 
    lastname 	VARCHAR(80), 
    email 		VARCHAR(255), 
    date_at		DATE NOT NULL, 
    date_unix	VARCHAR(100), 
    
    FOREIGN KEY (username) REFERENCES user(username) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO user (username, password) VALUE ('SideMaster', '$2y$10$AMbMdxu2ZlQegiTnh7g.8..VVGZhLKKp5GzwaDxe/b77Zi/lO9HqS');
INSERT INTO user_info (username, firstname, lastname, email, date_at, date_unix) VALUE ('Side Master','Jerson','Martínez', 'jersonmartinezsm@gmail.com','2017-11-24', '480340342');

CREATE TABLE business (
	id_business	INT UNSIGNED AUTO_INCREMENT NOT NULL, 
    username 	VARCHAR(100) NOT NULL, 
	title 		VARCHAR(255) NOT NULL, 
    description VARCHAR(1000), 
    cod_ruc		VARCHAR(50), 
    date_at		VARCHAR(100), 
    date_unix	VARCHAR(100), 
    
    PRIMARY KEY (id_business),
    FOREIGN KEY (username) REFERENCES user(username) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE business_contact (
	id_business INT UNSIGNED NOT NULL, 
    location 	VARCHAR(255) NOT NULL, 
    phone_home	VARCHAR(20), 
    phone_self	VARCHAR(20),
    
    FOREIGN KEY (id_business) REFERENCES business(id_business) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE business_map (
	id_business INT UNSIGNED NOT NULL, 
    longitud 	VARCHAR(50), 
    latitud		VARCHAR(50),
    
    FOREIGN KEY (id_business) REFERENCES business(id_business) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE business_perfil (
	id_business 	INT UNSIGNED NOT NULL, 
    folder			VARCHAR(300),
    src_img_perfil 	VARCHAR(1000), 
    src_img_front	VARCHAR(1000), 
    
    FOREIGN KEY (id_business) REFERENCES business(id_business) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE business_photos (
	id_business INT UNSIGNED NOT NULL, 
    id_img 		INT UNSIGNED AUTO_INCREMENT NOT NULL, 
	folder		VARCHAR(300),
    src_img 	VARCHAR(1000), 
    date_at		VARCHAR(100), 
    date_unix	VARCHAR(100),
    
    PRIMARY KEY (id_img),
    FOREIGN KEY (id_business) REFERENCES business(id_business) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE business_categories (
	id_category INT UNSIGNED AUTO_INCREMENT NOT NULL, 
	id_business INT UNSIGNED NOT NULL, 
    category VARCHAR(60) NOT NULL,
    
    PRIMARY KEY (id_category),
    FOREIGN KEY (id_business) REFERENCES business(id_business) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE base_business_photos (
    id 			INT UNSIGNED AUTO_INCREMENT NOT NULL, 
	folder		VARCHAR(300),
    src_img 	VARCHAR(1000), 
    date_at		VARCHAR(100), 
    date_unix	VARCHAR(100),
    
    PRIMARY KEY (id)
);

CREATE TABLE base_business_categories (
	id INT UNSIGNED AUTO_INCREMENT NOT NULL, 
    category VARCHAR(60) NOT NULL,
    
    PRIMARY KEY (id)
);