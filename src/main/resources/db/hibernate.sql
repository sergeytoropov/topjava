drop table if exists contact_hobby_detail;
drop table if exists contact_tel_detail;
drop table if exists hobby;
drop table if exists contact;

drop sequence if exists hibernate_seq;
create sequence hibernate_seq start 1;

create table contact
(
  id integer primary key default nextval('hibernate_seq'),
  first_name varchar not null,
  last_name varchar not null,
  birth_date date,
  version integer default 0 not null
  --  CONSTRAINT UQ_CONTACT UNIQUE (FIRST_NAМE, LAST_NAМE),
  --  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
create unique index unique_contact on contact (first_name, last_name);

create table hobby (
  hobby_id varchar primary key
);

create table contact_tel_detail (
  id integer primary key default nextval('hibernate_seq'),
  contact_id integer not null,
  tel_type varchar not null,
  tel_number varchar not null,
  version integer default 0 not null,
  foreign key (contact_id) REFERENCES contact (id) on delete cascade
);
create unique index uq_contact_tel_detail on contact_tel_detail (contact_id, tel_type);

create table CONTACT_HOBBY_DETAIL
(
  contact_id integer not null,
  hobby_id varchar not null,
  FOREIGN KEY (contact_id) REFERENCES contact (id) on delete cascade,
  FOREIGN KEY (hobby_id) REFERENCES hobby(hobby_id) on delete CASCADE
);
create UNIQUE INDEX uq_contact_hobby_detail on CONTACT_HOBBY_DETAIL (contact_id, hobby_id);

insert into contact(first_name, last_name, birth_date)
  VALUES ('Chris', 'Schaefer', '1981-05-03');
insert into contact(first_name, last_name, birth_date)
  VALUES ('Scott', 'Tiger', '1990-11-02');
insert into contact(first_name, last_name, birth_date)
  VALUES ('John', 'Smith', '1964-02-28');


insert into hobby (hobby_id) values ('Swimming');
insert into hobby (hobby_id) values ('Jogging');
insert into hobby (hobby_id) values ('Programming');
insert into hobby (hobby_id) values ('Movies');
insert into hobby (hobby_id) values ('Reading');

insert into contact_tel_detail (contact_id, tel_type, tel_number)
  values (1, 'Mobile', '79502874589');
insert into contact_tel_detail (contact_id, tel_type, tel_number)
  values (1, 'Home', '79502874589');
insert into contact_tel_detail (contact_id, tel_type, tel_number)
  values (2, 'Home', '79502874589');

insert into CONTACT_HOBBY_DETAIL(contact_id, hobby_id)
  values(1, 'Swimming');
insert into CONTACT_HOBBY_DETAIL(contact_id, hobby_id)
  values(1, 'Movies');
insert into CONTACT_HOBBY_DETAIL(contact_id, hobby_id)
  values(2, 'Swimming');

