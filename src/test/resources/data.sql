-- 1. Données de référence (Catégories) - Nécessaire pour créer des articles
INSERT INTO CATEGORY (name) VALUES ('Informatique');
INSERT INTO CATEGORY (name) VALUES ('Ameublement');
INSERT INTO CATEGORY (name) VALUES ('Vêtement');

-- 2. Données de référence (Lieux de retrait) - Nécessaire pour créer des articles
INSERT INTO WITHDRAWAL (street, postalCode, city) VALUES ('1 rue du Test', '75000', 'TestCity');

-- 3. Insertion des USERS
-- Note : On laisse H2 générer les ID (1, 2, 3, 4) séquentiellement
INSERT INTO USERS (username, lastName, firstName, email, numPhone, street, postalCode, city, password, credit, admin, active)
VALUES
    ('annelise', 'Durand', 'Annelise', 'a.durand@email.fr', '0601010101', '10 Rue de la Paix', '75000', 'Paris', '{bcrypt}$2a$10$KaSHgvH9p/cUnsOVPzYvzunWDAIv68whrOxmui1S.0AjzbP5RX7yO', 100, true, true),
    ('stephane', 'Martin', 'Stephane', 's.martin@email.fr', '0602020202', '20 Av. du Code', '44000', 'Nantes', '{bcrypt}$2a$10$B5U29ajHsIKd8aY3c/JNn.xTJpOCAeoXvT9XvfzbbHGP4iIFV9Lkm', 100, false, true),
    ('julien', 'Bernard', 'Julien', 'j.bernard@email.fr', '0603030303', '30 Bd du Java', '35000', 'Rennes', '{bcrypt}$2a$10$VwQ7gMUPLeQnFC6vCsOoTevzdPe.JPu0L/7cwPGdJ6TjSpipGwY.y', 50, false, true),
    ('servane', 'Petit', 'Servane', 's.petit@email.fr', '0604040404', '40 Impasse SQL', '69000', 'Lyon', '{bcrypt}$2a$10$qHGnMJYcTx1Vr.UTpnD.OuZHTbRS0O0N6SSn2BZMVbFekKOgjHTvu', 0, false, false);

-- 4. Insertion des ROLES
-- Pour les tests, il est préférable d'utiliser les ID explicites (1=annelise, 2=stephane, etc.)
-- C'est plus rapide et plus sûr que des sous-requêtes SELECT
INSERT INTO ROLES (user_id, "role") VALUES (1, 'ROLE_ADMIN'); -- Annelise
INSERT INTO ROLES (user_id, "role") VALUES (2, 'ROLE_USER');  -- Stephane
INSERT INTO ROLES (user_id, "role") VALUES (2, 'ROLE_ADMIN'); -- Stephane
INSERT INTO ROLES (user_id, "role") VALUES (3, 'ROLE_USER');  -- Julien
INSERT INTO ROLES (user_id, "role") VALUES (4, 'ROLE_USER');  -- Servane