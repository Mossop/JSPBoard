DROP TABLE IF EXISTS Login;
#DROP TABLE IF EXISTS Person;
#DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS Groups;
DROP TABLE IF EXISTS UserGroup;
DROP TABLE IF EXISTS Folder;
DROP TABLE IF EXISTS Thread;
DROP TABLE IF EXISTS Message;
DROP TABLE IF EXISTS File;
DROP TABLE IF EXISTS UnreadMessage;
DROP TABLE IF EXISTS EditedMessage;

CREATE TABLE Login (
	id		VARCHAR(15) NOT NULL,
	password	CHAR(32) NOT NULL,
	lastaccess	DATETIME,
	person		INTEGER NOT NULL,
	PRIMARY KEY (id)
);

#CREATE TABLE Person (
#	id		INTEGER AUTO_INCREMENT NOT NULL,
#	category	INTEGER,
#	title		VARCHAR(10),
#	firstnames	VARCHAR(30),
#	surname		VARCHAR(30),
#	email		VARCHAR(30),
#	nickname	VARCHAR(20),
#	homephone	VARCHAR(20),
#	mobilephone	VARCHAR(20),
#	workphone	VARCHAR(20),
#	fax		VARCHAR(20),
#	description	VARCHAR(100),
#	PRIMARY KEY (id)
#);

#CREATE TABLE Category (
#	id		INTEGER AUTO_INCREMENT NOT NULL,
#	description	VARCHAR(100),
#	PRIMARY KEY (id)
#);

CREATE TABLE Groups (
	id		VARCHAR(20) NOT NULL,
	description	VARCHAR(100),
	PRIMARY KEY (id)
);

CREATE TABLE UserGroup (
	id		VARCHAR(15) NOT NULL,
	group_id	VARCHAR(20) NOT NULL,
	PRIMARY KEY (id, group_id)
);

CREATE TABLE Folder (
	id		INTEGER AUTO_INCREMENT NOT NULL,
	parent		INTEGER NOT NULL,
	name		VARCHAR(50),
	restricted	TINYINT,
	PRIMARY KEY (id)
);

CREATE TABLE Thread (
	id		INTEGER AUTO_INCREMENT NOT NULL,
	folder		INTEGER NOT NULL,
	name		VARCHAR(40),
	created		DATETIME,
	owner		INTEGER NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE Message (
	id		INTEGER AUTO_INCREMENT NOT NULL,
	thread		INTEGER NOT NULL,
	owner		INTEGER NOT NULL,
	created		DATETIME,
	content		TEXT,
	PRIMARY KEY (id)
);

CREATE TABLE File (
	id		INTEGER AUTO_INCREMENT NOT NULL,
	name		VARCHAR(50) NOT NULL,
	filename	VARCHAR(50),
	message		INTEGER NOT NULL,
	description	VARCHAR(50),
	mimetype	VARCHAR(30),
	PRIMARY KEY (id)
);

CREATE TABLE UnreadMessage (
	message		INTEGER NOT NULL,
	person		VARCHAR(15) NOT NULL,
	PRIMARY KEY (message, person)
);

CREATE TABLE EditedMessage (
	message		INTEGER NOT NULL,
	person		INTEGER NOT NULL,
	altered		DATETIME NOT NULL,
	PRIMARY KEY (message, person, altered)
);
