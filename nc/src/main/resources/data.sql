-- Naucne Oblasti
insert into naucna_oblast (naziv) values ("Bioloske nauke");
insert into naucna_oblast (naziv) values ("Geo nauke");
insert into naucna_oblast (naziv) values ("Matematicke nauke");
insert into naucna_oblast (naziv) values ("Nauke o zastiti zivotne sredine");
insert into naucna_oblast (naziv) values ("Racunarske nauke");
insert into naucna_oblast (naziv) values ("Fizicke nauke");
insert into naucna_oblast (naziv) values ("Fizicko-hemijske nauke");
insert into naucna_oblast (naziv) values ("Hemijske nauke");

-- Permissions
insert into permission (id, name) values (1, 'CREATE');
insert into permission (id, name) values (2, 'READ');

-- Roles
insert into roles (id, name) values (1, 'USER');
insert into roles (id, name) values (2, 'REVIEWER');
insert into roles (id, name) values (3, 'EDITOR');
insert into roles (id, name) values (4, 'ADMIN');

-- Korisnici
insert into korisnik (username, password, aktiviran, drzava, email, enabled, grad, ime, non_locked, prezime, recenzent, titula)
values ("aut1", '$2a$10$DAcNYGf.8duU0iHRcuYw8uNJ8oeJULYlwZoM/H4eQXk2zdN4dn9oe', 1, "Srbija", "aut1@gmail.com", 1, "Novi Sad", "Autor", 1, "Docent", 1, "dr");
insert into korisnik (username, password, aktiviran, drzava, email, enabled, grad, ime, non_locked, prezime, recenzent, titula)
values ("rec1", '$2a$10$DAcNYGf.8duU0iHRcuYw8uNJ8oeJULYlwZoM/H4eQXk2zdN4dn9oe', 1, "Srbija", "rec1@gmail.com", 1, "Novi Sad", "Dime", 1, "Dimic", 1, "dr");
insert into korisnik (username, password, aktiviran, drzava, email, enabled, grad, ime, non_locked, prezime, recenzent, titula)
values ("rec2", '$2a$10$DAcNYGf.8duU0iHRcuYw8uNJ8oeJULYlwZoM/H4eQXk2zdN4dn9oe', 1, "Srbija", "rec2@gmail.com", 1, "Novi Sad", "Mile", 1, "Milic", 1, "dr");
insert into korisnik (username, password, aktiviran, drzava, email, enabled, grad, ime, non_locked, prezime, recenzent, titula)
values ("rec3", '$2a$10$DAcNYGf.8duU0iHRcuYw8uNJ8oeJULYlwZoM/H4eQXk2zdN4dn9oe', 1, "Srbija", "rec3@gmail.com", 1, "Novi Sad", "Ana", 1, "Nas", 1, "dr");
insert into korisnik (username, password, aktiviran, drzava, email, enabled, grad, ime, non_locked, prezime, recenzent, titula)
values ("ur1", '$2a$10$DAcNYGf.8duU0iHRcuYw8uNJ8oeJULYlwZoM/H4eQXk2zdN4dn9oe', 1, "Srbija", "ur1@gmail.com", 1, "Novi Sad", "Boban", 1, "Bob", 1, "dr");
insert into korisnik (username, password, aktiviran, drzava, email, enabled, grad, ime, non_locked, prezime, recenzent, titula)
values ("ur1", '$2a$10$DAcNYGf.8duU0iHRcuYw8uNJ8oeJULYlwZoM/H4eQXk2zdN4dn9oe', 1, "Srbija", "ur2@gmail.com", 1, "Novi Sad", "Stojke", 1, "Doktor", 1, "dr");
insert into korisnik (username, password, aktiviran, drzava, email, enabled, grad, ime, non_locked, prezime, recenzent, titula)
values ("admin", '$2a$10$DAcNYGf.8duU0iHRcuYw8uNJ8oeJULYlwZoM/H4eQXk2zdN4dn9oe', 1, "Srbija", "admin@gmail.com", 1, "Novi Sad", "Admin", 1, "Adam", 1, "dr");


-- Korisnici i Role
insert into korisnik_roles (korisnik_id, role_id) values (1, 3);
insert into korisnik_roles (korisnik_id, role_id) values (2, 2);
insert into korisnik_roles (korisnik_id, role_id) values (3, 2);
insert into korisnik_roles (korisnik_id, role_id) values (4, 2);
insert into korisnik_roles (korisnik_id, role_id) values (5, 3);
insert into korisnik_roles (korisnik_id, role_id) values (6, 3);
insert into korisnik_roles (korisnik_id, role_id) values (7, 4);

-- Casopisi
insert into casopis (aktiviran, issn, nacin_placanja, naziv, glavni_urednik_id) values (1, "123765", "pretplata", "Prvi Casopis", 1);

-- Casopisi i Naucne Oblasti
insert into casopis_naucna_oblast (casopis_id, naucna_oblast_id) values (1, 1);
insert into casopis_naucna_oblast (casopis_id, naucna_oblast_id) values (1, 2);

--Casopisi i Recenzenti
insert into casopis_recenzenti (casopis_id, korisnik_id) values (1, 2);
insert into casopis_recenzenti (casopis_id, korisnik_id) values (1, 3);
insert into casopis_recenzenti (casopis_id, korisnik_id) values (1, 4);

--Casopisi i Urednici
insert into casopis_urednici (casopis_id, korisnik_id) values (1, 5);
insert into casopis_urednici (casopis_id, korisnik_id) values (1, 6);