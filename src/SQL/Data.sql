INSERT INTO employees (firstname, lastname, email, department, salary, actif) VALUES
                                                                                  ('Alice', 'Martin', 'alice@company.com', 'Informatique', 2800, true),
                                                                                  ('Bob', 'Dupont', 'bob@company.com', 'Marketing', 2400, true),
                                                                                  ('Clara', 'Nguyen', 'clara@company.com', 'RH', 2600, false),
                                                                                  ('Andraina', 'ANDRIAMAHENINA', 'andraina@company.com', 'Informatique', 7000, true),
                                                                                  ('Jean', 'Marc', 'jean@company.com', 'Marketing', 2200, false);

INSERT INTO interns (firstname, lastname, email, department, salary, remunere, actif, manager_id) VALUES
                                                                                                      ('David', 'Lemoine', 'david@gmail.com', 'Informatique', 0, false, true, 1),
                                                                                                      ('Emma', 'Moreau', 'emma@gmail.com', 'Marketing', 1200, true, true, 2),
                                                                                                      ('Noah', 'Garnier', 'noah@gmail.com', 'Informatique', 500, true, true, 1),
                                                                                                      ('Paul', 'Jean', 'paul@gmail.com', 'Finance', 0, false, true, 4),
                                                                                                      ('Mathieu', 'Michel', 'mathieu@gmail.com', 'Marketing', 600, true, true, 2);