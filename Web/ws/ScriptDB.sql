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

INSERT INTO user (username, password) VALUE ('Side Master', '$2y$10$AMbMdxu2ZlQegiTnh7g.8..VVGZhLKKp5GzwaDxe/b77Zi/lO9HqS');
INSERT INTO user_info (username, firstname, lastname, email, date_at, date_unix) VALUE ('Side Master','Jerson','Martínez', 'jersonmartinezsm@gmail.com','2017-11-24', '480340342');
