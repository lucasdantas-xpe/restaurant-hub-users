create table if not exists users (
  id uuid primary key,
  name varchar(120) not null,
  email varchar(160) not null unique,
  login varchar(60)  not null unique,
  password_hash varchar(255) not null,
  address varchar(200),
  role varchar(16) not null,
  last_modified_at timestamp not null default now(),
  active boolean not null default true
);
