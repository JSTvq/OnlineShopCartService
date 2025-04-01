create table if not exists cart (
    id serial primary key,
    user_id integer,
    version integer,
    created_at timestamp,
    updated_at timestamp
);