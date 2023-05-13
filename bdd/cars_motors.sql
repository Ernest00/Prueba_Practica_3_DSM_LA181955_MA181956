-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-05-2023 a las 09:17:50
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `cars_motors`
--
CREATE DATABASE IF NOT EXISTS `cars_motors` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `cars_motors`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `automovil`
--

CREATE TABLE `automovil` (
  `idautomovil` int(11) NOT NULL,
  `modelo` varchar(45) NOT NULL,
  `numero_vin` varchar(45) NOT NULL,
  `numero_chasis` varchar(45) NOT NULL,
  `numero_motor` varchar(45) NOT NULL,
  `numero_asientos` int(11) NOT NULL,
  `anio` year(4) NOT NULL,
  `capacidad_asientos` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `URI_IMG` varchar(256) NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  `idmarcas` int(11) NOT NULL,
  `idtipoautomovil` int(11) NOT NULL,
  `idcolores` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `automovil`
--

INSERT INTO `automovil` VALUES
(1, 'Civic', '1HGCM82633A123456', 'HC123456789', '12345ABCD', 5, '2022', 5, 25.00, 'https://acnews.blob.core.windows.net/imgnews/medium/NAZ_d7effd7327af44769be1822d07979028.jpg', 'Automóvil sedán compacto con características ', 4, 1, 1),
(2, 'Corolla', '2T1BR32E8DC123456', 'TC987654321', 'XYZ987654', 5, '2021', 5, 22.50, 'https://i.ytimg.com/vi/WJOZQsZ01XI/maxresdefault.jpg', 'Automóvil compacto con excelente eficiencia d', 1, 1, 2),
(3, 'Mustang', '1FA6P8JZ7H1234567', 'FMX654321', '789XYZ123', 4, '2023', 4, 40.00, 'https://cdn.autobild.es/sites/navi.axelspringer.es/public/media/image/2022/09/ford-mustang-2023-2814415.jpg', 'Automóvil deportivo con motor potente y estil', 2, 5, 3),
(4, 'XC90', 'YV4A22PL0K1234567', 'VXC987654321', '456ABC789', 7, '2022', 7, 55.00, 'https://www.autonocion.com/wp-content/uploads/2015/06/Volvo-XC90-35-930x577.jpg', 'SUV de lujo con amplio espacio interior y tec', 11, 6, 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `colores`
--

CREATE TABLE `colores` (
  `idcolores` int(11) NOT NULL,
  `descripcion` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `colores`
--

INSERT INTO `colores` VALUES
(1, 'Rojo'),
(2, 'Azul'),
(3, 'Amarillo'),
(4, 'Verde'),
(5, 'Naranja'),
(6, 'Rosa'),
(7, 'Morado'),
(8, 'Blanco'),
(9, 'Negro'),
(10, 'Gris'),
(11, 'Marrón'),
(12, 'Turquesa'),
(13, 'Dorado'),
(14, 'Plateado'),
(15, 'Beige');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `favoritos_automovil`
--

CREATE TABLE `favoritos_automovil` (
  `idfavoritosautomovil` int(11) NOT NULL,
  `idusuario` int(11) NOT NULL,
  `idfavoritoautomovil` int(11) NOT NULL,
  `fecha_agregado` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `favoritos_automovil`
--

INSERT INTO `favoritos_automovil` VALUES
(1, 1, 3, '2023-05-13 07:16:40'),
(2, 2, 1, '2023-05-13 07:16:52');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marcas`
--

CREATE TABLE `marcas` (
  `idmarcas` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `marcas`
--

INSERT INTO `marcas` VALUES
(1, 'Toyota'),
(2, 'Ford'),
(3, 'Volkswagen'),
(4, 'Honda'),
(5, 'BMW'),
(6, 'Mercedes-Benz'),
(7, 'Chevrolet'),
(8, 'Audi'),
(9, 'Nissan'),
(10, 'Hyundai'),
(11, 'Volvo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_automovil`
--

CREATE TABLE `tipo_automovil` (
  `idtipoautomovil` int(11) NOT NULL,
  `descripcion` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tipo_automovil`
--

INSERT INTO `tipo_automovil` VALUES
(1, 'Tipo Sedan'),
(2, 'Tipo Camión'),
(3, 'Tipo Pick up'),
(4, 'Tipo Camioneta'),
(5, 'Coupé'),
(6, 'SUV');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idusuario` int(11) NOT NULL,
  `nombres` varchar(45) NOT NULL,
  `apellidos` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `user` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `tipo` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` VALUES
(1, 'Roberto Carlos', 'Lopez Abarca', 'carlos@gmail.com', 'carlos', '12345', 'ADMIN'),
(2, 'Bryan Ernesto', 'Marroquin Anaya', 'ernesto@gmail.com', 'ernesto', '12345', 'CLIENT');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `automovil`
--
ALTER TABLE `automovil`
  ADD PRIMARY KEY (`idautomovil`),
  ADD KEY `idmarcas` (`idmarcas`),
  ADD KEY `idtipoautomovil` (`idtipoautomovil`),
  ADD KEY `idcolores` (`idcolores`);

--
-- Indices de la tabla `colores`
--
ALTER TABLE `colores`
  ADD PRIMARY KEY (`idcolores`);

--
-- Indices de la tabla `favoritos_automovil`
--
ALTER TABLE `favoritos_automovil`
  ADD PRIMARY KEY (`idfavoritosautomovil`),
  ADD KEY `fk_favoritos_automovil_usuario1_idx` (`idusuario`),
  ADD KEY `fk_favoritos_automovil_automovil1_idx` (`idfavoritoautomovil`);

--
-- Indices de la tabla `marcas`
--
ALTER TABLE `marcas`
  ADD PRIMARY KEY (`idmarcas`);

--
-- Indices de la tabla `tipo_automovil`
--
ALTER TABLE `tipo_automovil`
  ADD PRIMARY KEY (`idtipoautomovil`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idusuario`),
  ADD UNIQUE KEY `user` (`user`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `automovil`
--
ALTER TABLE `automovil`
  MODIFY `idautomovil` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `colores`
--
ALTER TABLE `colores`
  MODIFY `idcolores` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `favoritos_automovil`
--
ALTER TABLE `favoritos_automovil`
  MODIFY `idfavoritosautomovil` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `marcas`
--
ALTER TABLE `marcas`
  MODIFY `idmarcas` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `tipo_automovil`
--
ALTER TABLE `tipo_automovil`
  MODIFY `idtipoautomovil` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idusuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `automovil`
--
ALTER TABLE `automovil`
  ADD CONSTRAINT `fk_automovil_colores1_idx` FOREIGN KEY (`idcolores`) REFERENCES `colores` (`idcolores`),
  ADD CONSTRAINT `fk_automovil_marcas_idx` FOREIGN KEY (`idmarcas`) REFERENCES `marcas` (`idmarcas`),
  ADD CONSTRAINT `fk_automovil_tipo_automovil1_idx` FOREIGN KEY (`idtipoautomovil`) REFERENCES `tipo_automovil` (`idtipoautomovil`);

--
-- Filtros para la tabla `favoritos_automovil`
--
ALTER TABLE `favoritos_automovil`
  ADD CONSTRAINT `fk_favoritos_automovil_automovil1_idx` FOREIGN KEY (`idfavoritoautomovil`) REFERENCES `automovil` (`idautomovil`),
  ADD CONSTRAINT `fk_favoritos_automovil_usuario1_idx` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
