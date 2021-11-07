BEGIN TRANSACTION;
DROP TABLE IF EXISTS "place";
CREATE TABLE IF NOT EXISTS "place" (
	"id"	int,
	"name"	text,
	"postal_number"	text,
	PRIMARY KEY("id")
);
DROP TABLE IF EXISTS "manufacturer";
CREATE TABLE IF NOT EXISTS "manufacturer" (
	"id"	int,
	"name"	text,
	PRIMARY KEY("id")
);
DROP TABLE IF EXISTS "vehicle";
CREATE TABLE IF NOT EXISTS "vehicle" (
	"id"	int,
	"manufacturer"	int,
	"model"	text,
	"chasis_number"	text,
	"plate_number"	text,
	"owner"	int,
	PRIMARY KEY("id")
);
DROP TABLE IF EXISTS "owner";
CREATE TABLE IF NOT EXISTS "owner" (
	"id"	int,
	"name"	text,
	"surname"	text,
	"parent_name"	text,
	"date_of_birth"	date,
	"place_of_birth"	int,
	"living_address"	text,
	"living_place"	int,
	"jmbg"	text,
	PRIMARY KEY("id"),
	CONSTRAINT "vlasnik_mjesto_id_fk_2" FOREIGN KEY("living_place") REFERENCES "place"("id"),
	CONSTRAINT "vlasnik_mjesto_id_fk" FOREIGN KEY("place_of_birth") REFERENCES "place"("id")
);
INSERT INTO "place" ("id","name","postal_number") VALUES (1,'Sarajevo','71000');
INSERT INTO "place" ("id","name","postal_number") VALUES (2,'Tuzla','72000');
INSERT INTO "place" ("id","name","postal_number") VALUES (3,'Zenica','73000');
INSERT INTO "manufacturer" ("id","name") VALUES (1,'Volkswagen');
INSERT INTO "manufacturer" ("id","name") VALUES (2,'Renault');
INSERT INTO "manufacturer" ("id","name") VALUES (3,'Ford');
INSERT INTO "vehicle" ("id","manufacturer","model","chasis_number","plate_number","owner") VALUES (1,1,'Golf','1234154123','A12-O-123',2);
INSERT INTO "owner" ("id","name","surname","parent_name","date_of_birth","place_of_birth","living_address","living_place","jmbg") VALUES (2,'Test','Testović','Te',1623103200000,1,'Prva ulica 1',3,'1234567890');
COMMIT;
