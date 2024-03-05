create table book (
        guid varchar(36) not null,
        title varchar(255) not null,
        year_publication timestamp(6) not null,
        person_registration_guid varchar(36) not null,
        primary key (guid)
    );

create table person (
        guid varchar(36) not null,
        email varchar(255) not null,
        enabled boolean not null,
        name varchar(255) not null,
        notification_type varchar(255) not null check (notification_type in ('EMAIL','SMS')),
        phone varchar(255) not null,
        password varchar(255) not null,
        primary key (guid)
    );

create table review (
        guid varchar(36) not null,
        rating integer not null,
        book_guid varchar(36) not null,
        person_review_guid varchar(36) not null,
        primary key (guid)
    );

create table reviews_to_book (
        book_guid varchar(36) not null,
        reviews_guid varchar(36) not null,
        primary key (book_guid, reviews_guid)
    );

alter table if exists person
       drop constraint if exists UK_fwmwi44u55bo4rvwsv0cln012;

alter table if exists person
       add constraint UK_fwmwi44u55bo4rvwsv0cln012 unique (email);

alter table if exists reviews_to_book
       drop constraint if exists UK_o9nehbm910dwovaqn5biquj6n;

alter table if exists reviews_to_book
       add constraint UK_o9nehbm910dwovaqn5biquj6n unique (reviews_guid);

alter table if exists book
       add constraint FKoq2ofln07ufkrctwdreab0ese
       foreign key (person_registration_guid)
       references person;

alter table if exists review
       add constraint FKs2qtwi394o5kmempajj3kcb7i
       foreign key (book_guid)
       references book;

alter table if exists review
       add constraint FKjosvxwvnn5o6fpknmwqo66h5q
       foreign key (person_review_guid)
       references person;

alter table if exists reviews_to_book
       add constraint FKpnk10pliki9mx58isx815ptg4
       foreign key (reviews_guid)
       references review;

alter table if exists reviews_to_book
       add constraint FK6l4v74egvmjd52fgk959r3j7x
       foreign key (book_guid)
       references book;

insert
       into
           person
           (email, enabled, name, notification_type, phone, password, guid)
       values
           ('user@test.com', true, 'User 01', 'EMAIL', '48-99999-9999', '$2a$10$5V7LCv97Ch5.MZVqOrkXjusdHVT6nyY3K/fHOJGk5IApkMFs8eO.G', '0b063e50-b4bc-464a-b001-4dd2fb643495');

insert
    into
        book
        (person_registration_guid, title, year_publication, guid)
    values
        ('0b063e50-b4bc-464a-b001-4dd2fb643495', 'Title 01', '2024-12-25 12:00:00', 'cc760628-b5b7-4a9b-85f1-3652c1302e1d');

insert
    into
        book
        (person_registration_guid, title, year_publication, guid)
    values
        ('0b063e50-b4bc-464a-b001-4dd2fb643495', 'Title 02', '2000-12-25 12:00:00', '20853a36-a374-4f89-8596-1e51e4af1d26');