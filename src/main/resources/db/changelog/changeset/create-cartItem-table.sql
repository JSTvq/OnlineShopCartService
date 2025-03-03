create table if not exists cart_item (
    id serial primary key,
    product_id integer,
    quantity integer,
    user_id integer
);