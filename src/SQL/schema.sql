CREATE TABLE employees (
                           id SERIAL PRIMARY KEY,
                           firstname VARCHAR(100),
                           lastname VARCHAR(100),
                           email VARCHAR(100) UNIQUE,
                           department VARCHAR(100),
                           salary DOUBLE PRECISION,
                           actif BOOLEAN DEFAULT true
);

CREATE TABLE interns (
                         id SERIAL PRIMARY KEY,
                         firstname VARCHAR(100),
                         lastname VARCHAR(100),
                         email VARCHAR(100) UNIQUE,
                         department VARCHAR(100),
                         salary DOUBLE PRECISION,
                         remunere BOOLEAN DEFAULT false,
                         actif BOOLEAN DEFAULT true,
                         manager_id INTEGER REFERENCES employees(id)
);