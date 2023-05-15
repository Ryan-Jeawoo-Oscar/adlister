USE adlister_db;

DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS ad_categories;


CREATE TABLE categories (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);
CREATE TABLE ad_categories (
   ad_id INT UNSIGNED NOT NULL,
   category_id INT UNSIGNED NOT NULL,
   PRIMARY KEY (ad_id, category_id),
   FOREIGN KEY (ad_id) REFERENCES ads (id) ON DELETE CASCADE,
   FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);

