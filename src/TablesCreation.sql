DROP TABLE VENTA;

DROP TABLE SUCURSAL;
DROP TABLE PRODUCTO;
DROP TABLE VENDEDOR;

DROP TABLE MARCA;
DROP TABLE GREMIO;
DROP TABLE CIUDAD;

DROP TABLE DPTO;

DROP TABLE PAIS;

CREATE TABLE PAIS(
 nombre VARCHAR2(20) PRIMARY KEY,
 moneda VARCHAR2(20) NOT NULL
);

CREATE TABLE DPTO(
 codigo VARCHAR2(20) PRIMARY KEY,
 nombre VARCHAR2(20) NOT NULL,
 nombre_pais VARCHAR2(20) NOT NULL,
 CONSTRAINT clave_foranea_nombre_pais FOREIGN KEY (nombre_pais) REFERENCES PAIS(nombre)
);

CREATE TABLE MARCA(
 nombre VARCHAR2(20) PRIMARY KEY,
 descripcion VARCHAR2(20) NOT NULL
);

CREATE TABLE GREMIO(
 codigo VARCHAR(20) PRIMARY KEY,
 nombre VARCHAR2(20) NOT NULL
);

CREATE TABLE CIUDAD(
 codigo VARCHAR2(20) PRIMARY KEY,
 nombre VARCHAR2(20) NOT NULL,
 poblacion NUMBER(8) NOT NULL,
 codigo_dpto VARCHAR2(20) NOT NULL,
 CONSTRAINT clave_foranea_codigo_dpto FOREIGN KEY (codigo_dpto) REFERENCES DPTO(codigo)
);

CREATE TABLE SUCURSAL(
 codigo VARCHAR2(20) PRIMARY KEY,
 nombre VARCHAR2(20) NOT NULL,
 direccion VARCHAR2(20) NOT NULL,
 codigo_ciudad VARCHAR2(20) NOT NULL,
 CONSTRAINT clave_foranea_codigo_ciudad FOREIGN KEY (codigo_ciudad) REFERENCES CIUDAD(codigo)
);

CREATE TABLE PRODUCTO(
 codbarras VARCHAR(20) PRIMARY KEY,
 nombre VARCHAR2(20) NOT NULL,
 tipo VARCHAR2(20) NOT NULL,
 nombre_marca VARCHAR2(20) NOT NULL,
 CONSTRAINT clave_foranea_nombre_marca FOREIGN KEY (nombre_marca) REFERENCES MARCA(nombre)
);

CREATE TABLE VENDEDOR(
 codigo VARCHAR2(20) PRIMARY KEY,
 nombre VARCHAR2(20) NOT NULL,
 salario NUMBER(8) NOT NULL,
 codigo_gremio VARCHAR2(20) NOT NULL,
 CONSTRAINT clave_foranea_codigo_gremio FOREIGN KEY (codigo_gremio) REFERENCES GREMIO(codigo)
);

CREATE TABLE VENTA(
 codigo VARCHAR2(20) PRIMARY KEY,
 valor NUMBER(8) NOT NULL,
 codigo_sucursal VARCHAR2(20) NOT NULL,
 codbarras_producto VARCHAR2(20) NOT NULL,
 codigo_vendedor VARCHAR2(20) NOT NULL,
 CONSTRAINT clave_foranea_codigo_sucursal FOREIGN KEY (codigo_sucursal) REFERENCES SUCURSAL(codigo),
 CONSTRAINT clave_foranea_codbarras_producto FOREIGN KEY (codbarras_producto) REFERENCES PRODUCTO(codbarras),
 CONSTRAINT clave_foranea_codigo_vendedor FOREIGN KEY (codigo_vendedor) REFERENCES VENDEDOR(codigo)
);

INSERT INTO PAIS VALUES('Colombia', 'COP');
INSERT INTO DPTO VALUES('A', 'Antioquia', 'Colombia');
INSERT INTO CIUDAD VALUES('M', 'Medellin', 4000000, 'A');
INSERT INTO SUCURSAL VALUES('S1', 'Sucursal UNO', 'Carrera esa Numero la otra', 'M');

INSERT INTO GREMIO VALUES('LM', 'Los mejores (y)');
INSERT INTO VENDEDOR VALUES('V1', 'Pedro', 2000000, 'LM');

INSERT INTO MARCA VALUES('Adidas', 'Ropita en general :v');
INSERT INTO PRODUCTO VALUES('PKi9', 'Tenis deportivos', 'Calzado', 'Adidas');

INSERT INTO VENTA VALUES('Venta1', 1000, 'S1', 'PKi9', 'V1');

SELECT * FROM PAIS;
SELECT * FROM DPTO;
SELECT * FROM CIUDAD;
SELECT * FROM SUCURSAL;

SELECT * FROM GREMIO;
SELECT * FROM VENDEDOR;

SELECT * FROM MARCA;
SELECT * FROM PRODUCTO;

SELECT SUCURSAL.codigo, SUCURSAL.nombre, SUM(valor) AS valor_total
FROM VENTA
INNER JOIN SUCURSAL
ON VENTA.codigo_sucursal = SUCURSAL.codigo
GROUP BY SUCURSAL.codigo;

SELECT VENDEDOR.codigo, VENDEDOR.nombre, SUM(valor) AS valor_total
FROM VENTA
INNER JOIN VENDEDOR
ON VENTA.codigo_vendedor = VENDEDOR.codigo
GROUP BY VENDEDOR.codigo;

SELECT PRODUCTO.codbarras, PRODUCTO.nombre, SUM(valor) AS valor_total
FROM VENTA
INNER JOIN PRODUCTO
ON VENTA.codbarras_producto = PRODUCTO.codbarras
GROUP BY PRODUCTO.codbarras;

SELECT SUCURSAL_CIUDAD.codigo_ciudad, SUCURSAL_CIUDAD.nombre_ciudad, SUM(valor) AS valor_total
FROM VENTA
INNER JOIN (
SELECT SUCURSAL.codigo AS codigo_sucursal, CIUDAD.codigo AS codigo_ciudad, CIUDAD.nombre AS nombre_ciudad
FROM SUCURSAL
INNER JOIN CIUDAD
ON SUCURSAL.codigo_ciudad = CIUDAD.codigo
) AS SUCURSAL_CIUDAD
ON VENTA.codigo_sucursal = SUCURSAL_CIUDAD.codigo_sucursal
GROUP BY SUCURSAL_CIUDAD.codigo_ciudad;

SELECT VENDEDOR_GREMIO.codigo_gremio, VENDEDOR_GREMIO.nombre_gremio, SUM(valor) AS valor_total
FROM VENTA
INNER JOIN (
SELECT VENDEDOR.codigo AS codigo_vendedor, GREMIO.codigo AS codigo_gremio, GREMIO.nombre AS nombre_gremio
FROM VENDEDOR
INNER JOIN GREMIO
ON VENDEDOR.codigo_gremio = GREMIO.codigo
) AS VENDEDOR_GREMIO
ON VENTA.codigo_vendedor = VENDEDOR_GREMIO.codigo_vendedor
GROUP BY VENDEDOR_GREMIO.codigo_gremio;

SELECT PRODUCTO_MARCA.nombre_marca, PRODUCTO_MARCA.descripcion_marca, SUM(valor) AS valor_total
FROM VENTA
INNER JOIN (
SELECT PRODUCTO.codbarras AS codbarras_producto, MARCA.nombre AS nombre_marca, MARCA.descripcion AS descripcion_marca
FROM PRODUCTO
INNER JOIN MARCA
ON PRODUCTO.nombre_marca = MARCA.nombre
) AS PRODUCTO_MARCA
ON VENTA.codbarras_producto = PRODUCTO_MARCA.codbarras_producto
GROUP BY PRODUCTO_MARCA.nombre_marca;

SELECT SUCURSAL_DPTO.codigo_dpto, SUCURSAL_DPTO.nombre_dpto, SUM(valor) AS valor_total
FROM VENTA
INNER JOIN (
SELECT SUCURSAL.codigo AS codigo_sucursal, CIUDAD_DPTO.codigo_dpto AS codigo_dpto, CIUDAD_DPTO.nombre_dpto AS nombre_dpto
FROM SUCURSAL
INNER JOIN (
SELECT CIUDAD.codigo AS codigo_ciudad, DPTO.codigo AS codigo_dpto, DPTO.nombre AS nombre_dpto
FROM CIUDAD
INNER JOIN DPTO
ON CIUDAD.codigo_dpto = DPTO.codigo
) AS CIUDAD_DPTO
ON SUCURSAL.codigo_ciudad = CIUDAD_DPTO.codigo_ciudad
) AS SUCURSAL_DPTO
ON VENTA.codigo_sucursal = SUCURSAL_DPTO.codigo_sucursal
GROUP BY SUCURSAL_DPTO.codigo_dpto;

SELECT SUCURSAL_PAIS.nombre_pais, SUM(valor) AS valor_total
FROM VENTA
INNER JOIN (
SELECT SUCURSAL.codigo AS codigo_sucursal, CIUDAD_PAIS.nombre_pais AS nombre_pais
FROM SUCURSAL
INNER JOIN (
SELECT CIUDAD.codigo AS codigo_ciudad, DPTO.nombre_pais AS nombre_pais
FROM CIUDAD
INNER JOIN DPTO
ON CIUDAD.codigo_dpto = DPTO.codigo
) AS CIUDAD_PAIS
ON SUCURSAL.codigo_ciudad = CIUDAD_PAIS.codigo_ciudad
) AS SUCURSAL_PAIS
ON VENTA.codigo_sucursal = SUCURSAL_PAIS.codigo_sucursal
GROUP BY SUCURSAL_PAIS.nombre_pais;
