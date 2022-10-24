CREATE database if not exists Hospital_db;
use Hospital_db;

CREATE TABLE tb_hospital(
id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
name varchar(30) NOT NULL,
address varchar(20) NOT NULL
);
CREATE TABLE tb_pet_owners(
id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
owner varchar(30) NOT NULL,
id_document int NOT NULL,
document_type varchar(15) NOT NULL,
document varchar(30) NOT NULL,
contact varchar (30) NOT NULL,
gender varchar(10) NOT NULL
);
CREATE TABLE tb_pet(
id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
name varchar(30) NOT NULL,
breed varchar (30) NOT NULL,
id_owner_pet int NOT NULL,
FOREIGN KEY (id_owner_pet) REFERENCES tb_pet_owners(id) ON DELETE CASCADE ON UPDATE
CASCADE
);
CREATE TABLE tb_pet_hospital(
id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
id_pet int NOT NULL,
id_hospital int NOT NULL,
FOREIGN KEY (id_pet) REFERENCES tb_pet(id) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (id_hospital) REFERENCES tb_hospital(id) ON DELETE CASCADE ON UPDATE CASCADE
);

#INSERT 

INSERT INTO tb_pet_owners(owner, id_document, document_type, document, contact, gender) 
VALUES ('Juan',1,'C.C.',10538181,3045458490,'Masculino'),
('Manuel',1,'C.C.',10539292,3045458490,'Masculino'),
('Valeria',2,'C.E.',10537373,3045458490,'Femenino'),
('Federico',3,'Pasaporte',10536464,3045458490,'Masculino');
INSERT INTO tb_hospital(name, address)
VALUES ('San Miguel','Calle 5A'),
('Mascoticas','Calle 6B');

INSERT INTO tb_pet(name, breed,id_owner_pet)
VALUES ('Juano','Bulldog', 1),
('Poli','Pastor Alemán',2),
('Dante','Pincher',3),
('Choko','Boxer',4),
('Hades','Criollo',1);

INSERT INTO tb_pet_hospital(id_pet, id_hospital)
VALUES
(1,2),
(2,1),
(3,1),
(4,2),
(5,2),
(3,2),
(5,1);

