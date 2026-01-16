CREATE TABLE IF NOT EXISTS products
(
    product_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(250)                          NOT NULL,
    description VARCHAR(500)                          NOT NULL,
    price       DECIMAL(10, 2)                        NOT NULL,
    popularity  INT                                   NOT NULL,
    image_url   VARCHAR(500),
    created_at  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by  VARCHAR(20)                           NOT NULL,
    updated_at  TIMESTAMP   DEFAULT NULL,
    updated_by  VARCHAR(20) DEFAULT NULL
    );

CREATE TABLE if not exists contacts(
                                       contact_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL ,
    phone       VARCHAR(15) NOT NULL ,
    message     VARCHAR(500) NOT NULL ,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    created_by  VARCHAR(20) NOT NULL ,
    updated_by  VARCHAR(100) DEFAULT NULL,
    updated_at  varchar(20) DEFAULT NULL
    );