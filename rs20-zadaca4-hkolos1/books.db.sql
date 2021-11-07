BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "books" (
	"id"	INTEGER,
	"author"	TEXT,
	"title"	TEXT,
	"isbn"	TEXT,
	"pagecount"	INTEGER,
	"publishdate"	TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "books" ("id","author","title","isbn","pagecount","publishdate") VALUES (1,'Meša Selimović','Tvrđava','abcd',500,'2019-5-23');
INSERT INTO "books" ("id","author","title","isbn","pagecount","publishdate") VALUES (2,'Ivo Andrić','Travnička hronika','abcd',500,'2019-5-23');
INSERT INTO "books" ("id","author","title","isbn","pagecount","publishdate") VALUES (3,'J. K. Rowling','Harry Potter','abcd',500,'2019-5-23');
COMMIT;
