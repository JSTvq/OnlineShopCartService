create table if not exists outbox_events (
    id serial primary key,
    aggregate_type varchar(100),
    aggregate_id integer,
    topic varchar(100),
    type varchar(100),
    payload JSONB,
    status varchar(50)
);