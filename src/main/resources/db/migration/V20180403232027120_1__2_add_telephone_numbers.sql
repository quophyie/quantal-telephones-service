CREATE TABLE IF NOT EXISTS telephone_numbers
(
  id BIGSERIAL NOT NULL PRIMARY KEY,
  company_id bigint NOT NULL,
  telephone_num character varying(300),
  type character varying(300),
  CONSTRAINT telephones_telephone_num_types_fk FOREIGN KEY (type) REFERENCES telephone_num_types(type)
)