-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-04-2020 a las 00:40:25
-- Versión del servidor: 10.4.11-MariaDB
-- Versión de PHP: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `socep`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulo`
--

CREATE TABLE `articulo` (
  `Id` int(11) NOT NULL,
  `Id_socios` int(11) NOT NULL,
  `Nombre` varchar(255) NOT NULL,
  `Descripcion` varchar(255) NOT NULL,
  `Precio` double(255,0) NOT NULL,
  `Imagen` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `direcciones_socios`
--

CREATE TABLE `direcciones_socios` (
  `Id` int(11) NOT NULL,
  `Direccion` varchar(200) NOT NULL,
  `Pais` varchar(50) NOT NULL,
  `Estado` varchar(50) NOT NULL,
  `Municipio` varchar(70) NOT NULL,
  `Codigo postal` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `direcciones_socios`
--

INSERT INTO `direcciones_socios` (`Id`, `Direccion`, `Pais`, `Estado`, `Municipio`, `Codigo postal`) VALUES
(1, 'wergf', 'dsfg', 'dsfgh', 'sdfghj', 32456);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `direccion_usuario`
--

CREATE TABLE `direccion_usuario` (
  `Id` int(11) NOT NULL,
  `Direccion_1` varchar(100) NOT NULL,
  `Direccion_2` varchar(100) DEFAULT NULL,
  `Pais` varchar(50) NOT NULL,
  `Estado_o_provincia` varchar(50) NOT NULL,
  `Ciudad` varchar(50) NOT NULL,
  `Codigo_postal` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `direccion_usuario`
--

INSERT INTO `direccion_usuario` (`Id`, `Direccion_1`, `Direccion_2`, `Pais`, `Estado_o_provincia`, `Ciudad`, `Codigo_postal`) VALUES
(1, 'ggd', 'bjds', 'jhgsd', 'ghds', 'ghgsd', 332);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `eventos`
--

CREATE TABLE `eventos` (
  `Id` int(11) NOT NULL,
  `Id_socio` int(11) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Descripcion` varchar(255) NOT NULL,
  `Direccion` varchar(100) NOT NULL,
  `Estado` varchar(50) NOT NULL,
  `Localidad` varchar(50) NOT NULL,
  `Fecha_inicio` datetime(6) NOT NULL,
  `Fecha_final` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `imagen_producto`
--

CREATE TABLE `imagen_producto` (
  `Id` int(11) NOT NULL,
  `Nombre_imagen` varchar(255) NOT NULL,
  `Id_articulos` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `miembros`
--

CREATE TABLE `miembros` (
  `Id_miembro` int(11) NOT NULL,
  `Nombre` varchar(255) DEFAULT NULL,
  `Correo` varchar(255) DEFAULT NULL,
  `Lada` int(11) DEFAULT NULL,
  `Telefono` varchar(255) DEFAULT NULL,
  `Socios_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `promociones`
--

CREATE TABLE `promociones` (
  `Id_promocio` int(11) NOT NULL,
  `Nombre` varchar(255) DEFAULT NULL,
  `Descripcion` longtext DEFAULT NULL,
  `Imagen` varchar(255) DEFAULT NULL,
  `Descuento` int(20) DEFAULT NULL,
  `Socio_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `redes_sociales`
--

CREATE TABLE `redes_sociales` (
  `Id` int(11) NOT NULL,
  `Facebook` varchar(255) DEFAULT NULL,
  `Instagram` varchar(255) DEFAULT NULL,
  `Twitter` varchar(255) DEFAULT NULL,
  `Youtube` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `redes_sociales`
--

INSERT INTO `redes_sociales` (`Id`, `Facebook`, `Instagram`, `Twitter`, `Youtube`) VALUES
(1, 'efrdgf', 'fd', 'sdfgh', 'dsfgh');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `Id_rol` int(11) NOT NULL,
  `Nombre` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`Id_rol`, `Nombre`) VALUES
(1, 'Cliente'),
(2, 'Socio');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicios`
--

CREATE TABLE `servicios` (
  `ID_Servicios` int(255) NOT NULL,
  `Precio` double(20,20) DEFAULT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `Descripcion` longtext DEFAULT NULL,
  `Imagen` varchar(255) DEFAULT NULL,
  `MiSocio` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `socios`
--

CREATE TABLE `socios` (
  `Id` int(11) NOT NULL,
  `lada` varchar(5) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `Mision` longtext DEFAULT NULL,
  `Nombre` varchar(255) DEFAULT NULL,
  `Id_Direcciones` int(11) DEFAULT NULL,
  `Id_Redes_sociales` int(11) DEFAULT NULL,
  `Contraseña` varchar(200) DEFAULT NULL,
  `Correo` varchar(100) DEFAULT NULL,
  `NombreSocio` varchar(100) DEFAULT NULL,
  `Id_rol` int(11) NOT NULL,
  `Vision` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `socios`
--

INSERT INTO `socios` (`Id`, `lada`, `Telefono`, `logo`, `Mision`, `Nombre`, `Id_Direcciones`, `Id_Redes_sociales`, `Contraseña`, `Correo`, `NombreSocio`, `Id_rol`, `Vision`) VALUES
(1, '52', '2231124470', NULL, 'Hola mundo', 'DARGG', 1, 1, 'admin', 'duke.coba2@gmail.com', 'Erick Coba', 2, 'Hola Mundo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `Id` int(11) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Apellidos` varchar(50) NOT NULL,
  `Id_Direccion` int(11) DEFAULT NULL,
  `Correo electronico` varchar(100) NOT NULL,
  `lada` varchar(5) NOT NULL,
  `Telefono` int(15) NOT NULL,
  `Contraseña` varchar(50) NOT NULL,
  `Id_roles` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `Id` int(11) NOT NULL,
  `Id_articulo` int(11) NOT NULL,
  `Id_usuario` int(11) NOT NULL,
  `Fecha_venta` datetime(6) NOT NULL,
  `Cantidad` int(255) NOT NULL,
  `Precio_total` double(255,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `articulo`
--
ALTER TABLE `articulo`
  ADD PRIMARY KEY (`Id`) USING BTREE,
  ADD KEY `Id_socios` (`Id_socios`) USING BTREE;

--
-- Indices de la tabla `direcciones_socios`
--
ALTER TABLE `direcciones_socios`
  ADD PRIMARY KEY (`Id`) USING BTREE;

--
-- Indices de la tabla `direccion_usuario`
--
ALTER TABLE `direccion_usuario`
  ADD PRIMARY KEY (`Id`) USING BTREE;

--
-- Indices de la tabla `eventos`
--
ALTER TABLE `eventos`
  ADD PRIMARY KEY (`Id`) USING BTREE,
  ADD KEY `Id_socio` (`Id_socio`) USING BTREE;

--
-- Indices de la tabla `imagen_producto`
--
ALTER TABLE `imagen_producto`
  ADD PRIMARY KEY (`Id`) USING BTREE,
  ADD KEY `Id_articulos` (`Id_articulos`) USING BTREE;

--
-- Indices de la tabla `miembros`
--
ALTER TABLE `miembros`
  ADD PRIMARY KEY (`Id_miembro`),
  ADD KEY `Socios_ID` (`Socios_ID`);

--
-- Indices de la tabla `promociones`
--
ALTER TABLE `promociones`
  ADD PRIMARY KEY (`Id_promocio`),
  ADD KEY `Socio_ID` (`Socio_ID`);

--
-- Indices de la tabla `redes_sociales`
--
ALTER TABLE `redes_sociales`
  ADD PRIMARY KEY (`Id`) USING BTREE;

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`Id_rol`);

--
-- Indices de la tabla `servicios`
--
ALTER TABLE `servicios`
  ADD PRIMARY KEY (`ID_Servicios`),
  ADD KEY `MiSocio` (`MiSocio`);

--
-- Indices de la tabla `socios`
--
ALTER TABLE `socios`
  ADD PRIMARY KEY (`Id`) USING BTREE,
  ADD KEY `Id_Direcciones` (`Id_Direcciones`) USING BTREE,
  ADD KEY `Id_Redes_sociales` (`Id_Redes_sociales`) USING BTREE,
  ADD KEY `Id_rol` (`Id_rol`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`Id`) USING BTREE,
  ADD KEY `Id_Direccion` (`Id_Direccion`) USING BTREE,
  ADD KEY `Id_roles` (`Id_roles`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`Id`) USING BTREE,
  ADD KEY `Id_articulo` (`Id_articulo`) USING BTREE,
  ADD KEY `Id_usuario` (`Id_usuario`) USING BTREE;

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `articulo`
--
ALTER TABLE `articulo`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `direcciones_socios`
--
ALTER TABLE `direcciones_socios`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `direccion_usuario`
--
ALTER TABLE `direccion_usuario`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `miembros`
--
ALTER TABLE `miembros`
  MODIFY `Id_miembro` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `redes_sociales`
--
ALTER TABLE `redes_sociales`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `Id_rol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `servicios`
--
ALTER TABLE `servicios`
  MODIFY `ID_Servicios` int(255) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `socios`
--
ALTER TABLE `socios`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `articulo`
--
ALTER TABLE `articulo`
  ADD CONSTRAINT `Id_socios` FOREIGN KEY (`Id_socios`) REFERENCES `socios` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `eventos`
--
ALTER TABLE `eventos`
  ADD CONSTRAINT `Id_socio` FOREIGN KEY (`Id_socio`) REFERENCES `socios` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `imagen_producto`
--
ALTER TABLE `imagen_producto`
  ADD CONSTRAINT `Id_articulos` FOREIGN KEY (`Id_articulos`) REFERENCES `articulo` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `miembros`
--
ALTER TABLE `miembros`
  ADD CONSTRAINT `Socios_ID` FOREIGN KEY (`Socios_ID`) REFERENCES `socios` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `promociones`
--
ALTER TABLE `promociones`
  ADD CONSTRAINT `Socio_ID` FOREIGN KEY (`Socio_ID`) REFERENCES `socios` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `servicios`
--
ALTER TABLE `servicios`
  ADD CONSTRAINT `MiSocio` FOREIGN KEY (`MiSocio`) REFERENCES `socios` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `socios`
--
ALTER TABLE `socios`
  ADD CONSTRAINT `Id_Direcciones` FOREIGN KEY (`Id_Direcciones`) REFERENCES `direcciones_socios` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `Id_Redes_sociales` FOREIGN KEY (`Id_Redes_sociales`) REFERENCES `redes_sociales` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `Id_rol` FOREIGN KEY (`Id_rol`) REFERENCES `roles` (`Id_rol`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `Id_Direccion` FOREIGN KEY (`Id_Direccion`) REFERENCES `direccion_usuario` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `Id_roles` FOREIGN KEY (`Id_roles`) REFERENCES `roles` (`Id_rol`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `Id_articulo` FOREIGN KEY (`Id_articulo`) REFERENCES `articulo` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `Id_usuario` FOREIGN KEY (`Id_usuario`) REFERENCES `usuario` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
