CREATE TABLE IF NOT EXISTS users (
    email text PRIMARY KEY NOT NULL,
    wallet_address text NOT NULL,
    name text NOT NULL,
    surname text NOT NULL,
    address text NOT NULL,
    password text NOT NULL,
    cvu text NOT NULL
);

INSERT INTO users (wallet_address, name, surname, email, address, password, cvu) VALUES
('00000001','Jorge','Flores','jf@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678'),
('00000002','Juan','Contardo','jc@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678'),
('00000003','Gonzalo','Zarate','gz@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678'),
('00000004','Cristian','Gomez','cg@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678'),
('00000005','Franco','Sepulveda','fs@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678'),
('00000006','Vanesa','Martinez','vm@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678'),
('00000007','Florencia', 'De La V','fdv@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678'),
('00000008','Julieta','Garay','jg@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678'),
('00000009','Carla','Pose','cp@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678'),
('00000010','Daiana','Cabral','dc@gmail.com','Avenida Siempre Viva 123','asdfghjk','012345678');