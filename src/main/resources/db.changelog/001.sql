create table if not exists public.person
(
    id               integer not null
    primary key,
    age              integer,
    email            varchar(255),
    inn              varchar(255),
    name             varchar(255),
    organizationdata varchar(255)
    );

