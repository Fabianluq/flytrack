-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3308
-- Tiempo de generación: 26-04-2026 a las 22:18:09
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `flytrack`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aerolinea`
--

CREATE TABLE `aerolinea` (
                             `id_aerolinea` int(11) NOT NULL,
                             `nombre` varchar(100) NOT NULL,
                             `codigo_iata` varchar(3) NOT NULL,
                             `pais` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aeropuerto`
--

CREATE TABLE `aeropuerto` (
                              `id_aeropuerto` int(11) NOT NULL,
                              `nombre` varchar(100) NOT NULL,
                              `codigo_iata` varchar(3) NOT NULL,
                              `ciudad` varchar(100) NOT NULL,
                              `pais` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipaje`
--

CREATE TABLE `equipaje` (
                            `id_equipaje` int(11) NOT NULL,
                            `id_reserva` int(11) NOT NULL,
                            `descripcion` varchar(255) DEFAULT NULL,
                            `peso_kg` decimal(5,2) NOT NULL,
                            `estado` enum('registrado','en_bodega','entregado','perdido','danado') NOT NULL DEFAULT 'registrado',
                            `reporte_incidente` text DEFAULT NULL,
                            `fecha_reporte` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notificacion`
--

CREATE TABLE `notificacion` (
                                `id_notificacion` int(11) NOT NULL,
                                `id_usuario` int(11) NOT NULL,
                                `id_vuelo` int(11) NOT NULL,
                                `titulo` varchar(150) NOT NULL,
                                `mensaje` text NOT NULL,
                                `tipo` enum('cambio_vuelo','embarque','cancelacion','equipaje','general') NOT NULL DEFAULT 'general',
                                `leida` tinyint(1) NOT NULL DEFAULT 0,
                                `fecha_envio` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva`
--

CREATE TABLE `reserva` (
                           `id_reserva` int(11) NOT NULL,
                           `id_usuario` int(11) NOT NULL,
                           `id_vuelo` int(11) NOT NULL,
                           `codigo_reserva` varchar(20) NOT NULL,
                           `fecha_reserva` datetime NOT NULL DEFAULT current_timestamp(),
                           `estado` enum('confirmada','pendiente','cancelada') NOT NULL DEFAULT 'pendiente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
                       `id_rol` int(11) NOT NULL,
                       `descripcion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`id_rol`, `descripcion`) VALUES
                                                (1, 'admin'),
                                                (2, 'pasajero');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
                           `id_usuario` int(11) NOT NULL,
                           `id_rol` int(11) NOT NULL DEFAULT 2,
                           `nombre` varchar(100) NOT NULL,
                           `apellido` varchar(100) NOT NULL,
                           `correo` varchar(150) NOT NULL,
                           `contrasena` varchar(255) NOT NULL,
                           `telefono` varchar(20) DEFAULT NULL,
                           `fecha_registro` datetime NOT NULL DEFAULT current_timestamp(),
                           `estado` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vuelo`
--

CREATE TABLE `vuelo` (
                         `id_vuelo` int(11) NOT NULL,
                         `id_aerolinea` int(11) NOT NULL,
                         `id_origen` int(11) NOT NULL,
                         `id_destino` int(11) NOT NULL,
                         `numero_vuelo` varchar(10) NOT NULL,
                         `fecha_salida` datetime NOT NULL,
                         `fecha_llegada` datetime NOT NULL,
                         `puerta_embarque` varchar(10) DEFAULT NULL,
                         `estado` enum('programado','embarcando','en_vuelo','aterrizado','cancelado') NOT NULL DEFAULT 'programado'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `aerolinea`
--
ALTER TABLE `aerolinea`
    ADD PRIMARY KEY (`id_aerolinea`),
  ADD UNIQUE KEY `codigo_iata_UNIQUE` (`codigo_iata`);

--
-- Indices de la tabla `aeropuerto`
--
ALTER TABLE `aeropuerto`
    ADD PRIMARY KEY (`id_aeropuerto`),
  ADD UNIQUE KEY `codigo_iata_aero_UNIQUE` (`codigo_iata`);

--
-- Indices de la tabla `equipaje`
--
ALTER TABLE `equipaje`
    ADD PRIMARY KEY (`id_equipaje`),
  ADD KEY `fk_equipaje_reserva` (`id_reserva`);

--
-- Indices de la tabla `notificacion`
--
ALTER TABLE `notificacion`
    ADD PRIMARY KEY (`id_notificacion`),
  ADD KEY `fk_notificacion_usuario` (`id_usuario`),
  ADD KEY `fk_notificacion_vuelo` (`id_vuelo`);

--
-- Indices de la tabla `reserva`
--
ALTER TABLE `reserva`
    ADD PRIMARY KEY (`id_reserva`),
  ADD UNIQUE KEY `codigo_reserva_UNIQUE` (`codigo_reserva`),
  ADD KEY `fk_reserva_usuario` (`id_usuario`),
  ADD KEY `fk_reserva_vuelo` (`id_vuelo`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
    ADD PRIMARY KEY (`id_rol`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
    ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `correo_UNIQUE` (`correo`),
  ADD KEY `fk_usuario_rol` (`id_rol`);

--
-- Indices de la tabla `vuelo`
--
ALTER TABLE `vuelo`
    ADD PRIMARY KEY (`id_vuelo`),
  ADD KEY `fk_vuelo_aerolinea` (`id_aerolinea`),
  ADD KEY `fk_vuelo_origen` (`id_origen`),
  ADD KEY `fk_vuelo_destino` (`id_destino`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `aerolinea`
--
ALTER TABLE `aerolinea`
    MODIFY `id_aerolinea` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `aeropuerto`
--
ALTER TABLE `aeropuerto`
    MODIFY `id_aeropuerto` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `equipaje`
--
ALTER TABLE `equipaje`
    MODIFY `id_equipaje` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `notificacion`
--
ALTER TABLE `notificacion`
    MODIFY `id_notificacion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `reserva`
--
ALTER TABLE `reserva`
    MODIFY `id_reserva` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
    MODIFY `id_rol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
    MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `vuelo`
--
ALTER TABLE `vuelo`
    MODIFY `id_vuelo` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `equipaje`
--
ALTER TABLE `equipaje`
    ADD CONSTRAINT `fk_equipaje_reserva` FOREIGN KEY (`id_reserva`) REFERENCES `reserva` (`id_reserva`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `notificacion`
--
ALTER TABLE `notificacion`
    ADD CONSTRAINT `fk_notificacion_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_notificacion_vuelo` FOREIGN KEY (`id_vuelo`) REFERENCES `vuelo` (`id_vuelo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `reserva`
--
ALTER TABLE `reserva`
    ADD CONSTRAINT `fk_reserva_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_reserva_vuelo` FOREIGN KEY (`id_vuelo`) REFERENCES `vuelo` (`id_vuelo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
    ADD CONSTRAINT `fk_usuario_rol` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id_rol`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `vuelo`
--
ALTER TABLE `vuelo`
    ADD CONSTRAINT `fk_vuelo_aerolinea` FOREIGN KEY (`id_aerolinea`) REFERENCES `aerolinea` (`id_aerolinea`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_vuelo_destino` FOREIGN KEY (`id_destino`) REFERENCES `aeropuerto` (`id_aeropuerto`) ON UPDATE CASCADE,
                                                                                                                                                                                                                                              ADD CONSTRAINT `fk_vuelo_origen` FOREIGN KEY (`id_origen`) REFERENCES `aeropuerto` (`id_aeropuerto`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
