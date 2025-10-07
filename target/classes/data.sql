
INSERT INTO SALA (codigo, nombre, precio, porcentajelinea, porcentajebingo, porcentajebote, BOLAMAXBOTE)
VALUES ('Sala_1', 'Sala nombre', 10, 10, 60, 30, 44);

INSERT INTO SALA (codigo, nombre, precio, porcentajelinea, porcentajebingo, porcentajebote, BOLAMAXBOTE)
VALUES ('Sala_2', 'Sala nombre 2', 20, 10, 60, 30, 44);

INSERT INTO SALA (codigo, nombre, precio, porcentajelinea, porcentajebingo, porcentajebote, BOLAMAXBOTE)
VALUES ('Sala_3', 'Sala nombre 3', 50, 10, 60, 30, 44);


INSERT INTO USUARIO (nombreusuario, saldo, password, salt, fechaultimoacceso, activo)
VALUES ('admin', 0, '$2a$10$WJQ659lSblerAiT5kBXydOwvNVd57S8Q6TyY1WHWga1mJvNKzCwcO', null, SYSDATE, 1);