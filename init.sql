CREATE TABLE IF NOT EXISTS users (
    id bigint PRIMARY KEY NOT NULL,
    name text NOT NULL,
    surname text NOT NULL,
    email text NOT NULL,
    address text NOT NULL,
    password text NOT NULL,
    cvu text NOT NULL,
    walletAddress text NOT NULL
);

INSERT INTO users (id, name, surname, email, address, password, cvu, walletAddress) VALUES
(1,'Jorge','Flores','jf@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678','876543210'),
(2,'Juan','Contardo','jc@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678','876543210'),
(3,'Gonzalo','Zarate','gz@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678','876543210'),
(4,'Cristian','Gomez','cg@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678','876543210'),
(5,'Franco','Sepulveda','fs@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678','876543210'),
(6,'Vanesa','Martinez','vm@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678','876543210'),
(7,'Florencia', 'De La V','fdv@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678','876543210'),
(8,'Julieta','Garay','jg@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678','876543210'),
(9,'Carla','Pose','cp@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678','876543210'),
(10,'Daiana','Cabral','dc@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678','876543210');