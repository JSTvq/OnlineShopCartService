create table if not exists cart_item (
    id serial primary key,
    product_id bigint,
    quantity integer,
    cart_id bigint,
    created_at timestamp,
    updated_at timestamp
);