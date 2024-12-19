-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: db:3306
-- Tiempo de generación: 07-12-2024 a las 19:00:09
-- Versión del servidor: 9.1.0
-- Versión de PHP: 8.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `maze_game`
--

CREATE DATABASE IF NOT EXISTS `maze_game` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

USE `maze_game`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `coins`
--

CREATE TABLE `coins` (
  `id` int NOT NULL,
  `cx` int DEFAULT NULL,
  `cy` int DEFAULT NULL,
  `roomId` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `coins`
--

INSERT INTO `coins` (`id`, `cx`, `cy`, `roomId`) VALUES
(18, 500, 500, 1),
(19, 350, 500, 3),
(20, 100, 100, 2),
(21, 100, 100, 5),
(22, 400, 50, 4),
(23, 500, 300, 14),
(25, 50, 500, 13),
(26, 300, 50, 9),
(27, 500, 400, 8),
(28, 400, 400, 16),
(29, 200, 50, 17),
(30, 450, 300, 19),
(31, 150, 500, 20),
(32, 250, 500, 22);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `doors`
--

CREATE TABLE `doors` (
  `id` int NOT NULL,
  `isOpen` tinyint(1) DEFAULT NULL,
  `keyId` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `doors`
--

INSERT INTO `doors` (`id`, `isOpen`, `keyId`) VALUES
(1, 1, 1),
(2, 0, 1),
(3, 1, NULL),
(4, 1, NULL),
(5, 0, 2),
(6, 1, NULL),
(7, 1, NULL),
(8, 1, NULL),
(9, 1, NULL),
(10, 0, 3),
(11, 0, 4),
(12, 1, NULL),
(13, 1, NULL),
(14, 1, NULL),
(15, 1, NULL),
(16, 0, 6),
(17, 1, NULL),
(18, 1, NULL),
(19, 1, NULL),
(20, 0, 7),
(21, 0, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `games`
--

CREATE TABLE `games` (
  `id` int NOT NULL,
  `userId` int DEFAULT NULL,
  `coins` int DEFAULT '0',
  `currentRoomId` int DEFAULT NULL,
  `startDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `endDate` datetime DEFAULT NULL,
  `mapId` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `games`
--

INSERT INTO `games` (`id`, `userId`, `coins`, `currentRoomId`, `startDate`, `endDate`, `mapId`) VALUES
(63, 16, 1, 3, '2024-12-06 22:09:40', '2024-12-06 22:15:25', 1),
(64, 16, 1, 3, '2024-12-06 22:15:27', '2024-12-06 22:16:29', 1),
(65, 16, 0, 10, '2024-12-06 22:17:18', '2024-12-07 18:37:19', 2),
(68, 16, 0, 3, '2024-12-07 18:47:12', '2024-12-07 18:47:38', 1),
(69, 16, 1, 3, '2024-12-07 18:47:47', NULL, 1),
(70, 16, 0, 14, '2024-12-07 18:52:49', NULL, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `games_coins`
--

CREATE TABLE `games_coins` (
  `id` int NOT NULL,
  `gamesId` int DEFAULT NULL,
  `coinId` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `games_coins`
--

INSERT INTO `games_coins` (`id`, `gamesId`, `coinId`) VALUES
(138, 63, 18),
(139, 63, 19),
(140, 63, 20),
(141, 63, 21),
(142, 63, 22),
(143, 64, 19),
(144, 64, 18),
(145, 64, 20),
(146, 64, 21),
(147, 64, 22),
(148, 65, 25),
(149, 65, 23),
(150, 65, 26),
(151, 65, 27),
(159, 68, 19),
(160, 68, 18),
(161, 68, 20),
(162, 68, 21),
(163, 69, 19);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `games_doors`
--

CREATE TABLE `games_doors` (
  `id` int NOT NULL,
  `gamesId` int DEFAULT NULL,
  `doorId` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `games_doors`
--

INSERT INTO `games_doors` (`id`, `gamesId`, `doorId`) VALUES
(62, 63, 2),
(63, 63, 5),
(64, 64, 2),
(65, 64, 5),
(66, 65, 10),
(67, 65, 11),
(69, 68, 2),
(70, 68, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `games_keys`
--

CREATE TABLE `games_keys` (
  `id` int NOT NULL,
  `gamesId` int DEFAULT NULL,
  `keyId` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `games_keys`
--

INSERT INTO `games_keys` (`id`, `gamesId`, `keyId`) VALUES
(84, 63, 1),
(85, 63, 2),
(86, 64, 1),
(87, 64, 2),
(88, 65, 3),
(89, 65, 4),
(93, 68, 1),
(94, 68, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `keys`
--

CREATE TABLE `keys` (
  `id` int NOT NULL,
  `name` text,
  `roomId` int DEFAULT NULL,
  `cx` int DEFAULT NULL,
  `cy` int DEFAULT NULL,
  `value` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `keys`
--

INSERT INTO `keys` (`id`, `name`, `roomId`, `cx`, `cy`, `value`) VALUES
(1, 'Key 1', 1, 50, 50, 2),
(2, 'Key 2', 2, 200, 100, 2),
(3, 'Key 3', 11, 500, 50, 2),
(4, 'Key 4', 8, 300, 500, 2),
(6, 'Key 5', 15, 50, 300, 2),
(7, 'Key 6', 21, 50, 450, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `maps`
--

CREATE TABLE `maps` (
  `id` int NOT NULL,
  `name` text,
  `initialRoomId` int DEFAULT NULL,
  `finalRoomId` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `maps`
--

INSERT INTO `maps` (`id`, `name`, `initialRoomId`, `finalRoomId`) VALUES
(1, 'map1', 1, 6),
(2, 'map2', 14, 7),
(3, 'map3', 17, 24);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rooms`
--

CREATE TABLE `rooms` (
  `id` int NOT NULL,
  `name` text,
  `hasCoin` tinyint(1) DEFAULT NULL,
  `hasKey` tinyint(1) DEFAULT NULL,
  `mapId` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `rooms`
--

INSERT INTO `rooms` (`id`, `name`, `hasCoin`, `hasKey`, `mapId`) VALUES
(1, 'Room 1', 1, 1, 1),
(2, 'Room 2', 1, 1, 1),
(3, 'Room 3', 1, 0, 1),
(4, 'Room 4', 1, 0, 1),
(5, 'Room 5', 1, 0, 1),
(6, 'Room 6', 0, 0, 1),
(7, 'Room 7', 0, 0, 2),
(8, 'Room 8', 1, 1, 2),
(9, 'Room 9', 1, 0, 2),
(10, 'Room 10', 0, 0, 2),
(11, 'Room 11', 0, 1, 2),
(12, 'Room 12', 0, 0, 2),
(13, 'Room 13', 1, 0, 2),
(14, 'Room 14', 1, 0, 2),
(15, 'Room 15', 0, 1, 3),
(16, 'Room 16', 1, 0, 3),
(17, 'Room 17', 1, 0, 3),
(18, 'Room 18', 0, 0, 3),
(19, 'Room 19', 1, 0, 3),
(20, 'Room 20', 1, 0, 3),
(21, 'Room 21', 0, 1, 3),
(22, 'Room 22', 1, 0, 3),
(23, 'Room 23', 0, 0, 3),
(24, 'Room 24', 0, 0, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `name` text,
  `username` text,
  `password` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `name`, `username`, `password`) VALUES
(16, 'Marc', 'mlorente', '$2a$10$A4mzoqNYk6T6hveuDoaBI.pDiVrLuynqEctLbyQ4ptmGN6edb86Pm'),
(17, 'Marc', 'marc', '$2a$10$bb5a4LbG2ngaBa/Lm0SpC.Iay21JPihD2k.a8L0VDMHRZn9RinjDO'),
(18, 'marc', 'mLorente_13', '$2a$10$C8hDU29GhZAKeCWOpJ6asuZJVUEnVJBX5NURCDYNQclJYna6g1t8q'),
(19, 'marc', 'mlt', '$2a$10$N9ua5WLqnsdhq9E.Pe/w1OFRIj/qGsFOyAeJSVYeqgtV4aMh2ycYS');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `walls`
--

CREATE TABLE `walls` (
  `id` int NOT NULL,
  `dir` text,
  `roomId` int DEFAULT NULL,
  `doorId` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `walls`
--

INSERT INTO `walls` (`id`, `dir`, `roomId`, `doorId`) VALUES
(2, 'N', 1, 1),
(3, 'E', 1, 2),
(4, 'W', 2, 2),
(5, 'S', 1, NULL),
(6, 'W', 1, NULL),
(7, 'N', 2, NULL),
(8, 'S', 2, 3),
(9, 'E', 2, 4),
(10, 'S', 3, 1),
(11, 'N', 3, 5),
(12, 'E', 3, NULL),
(13, 'W', 3, NULL),
(14, 'W', 4, 4),
(15, 'N', 4, NULL),
(16, 'S', 4, NULL),
(17, 'E', 4, NULL),
(18, 'N', 5, 3),
(19, 'S', 5, NULL),
(20, 'W', 5, NULL),
(21, 'E', 5, NULL),
(22, 'N', 6, NULL),
(23, 'S', 6, 5),
(24, 'E', 6, NULL),
(25, 'W', 6, NULL),
(26, 'N', 14, NULL),
(27, 'E', 14, NULL),
(28, 'W', 14, NULL),
(29, 'S', 14, 6),
(30, 'N', 13, 6),
(31, 'E', 13, 7),
(32, 'S', 13, NULL),
(33, 'W', 13, 8),
(34, 'N', 12, NULL),
(35, 'E', 12, 8),
(36, 'S', 12, 9),
(37, 'W', 12, NULL),
(38, 'N', 11, 9),
(39, 'E', 11, NULL),
(40, 'S', 11, NULL),
(41, 'W', 11, NULL),
(42, 'N', 10, NULL),
(43, 'W', 10, 7),
(44, 'S', 10, 11),
(45, 'E', 10, 10),
(46, 'N', 9, NULL),
(47, 'W', 9, 10),
(48, 'E', 9, NULL),
(49, 'S', 9, 12),
(50, 'N', 8, 12),
(51, 'S', 8, NULL),
(52, 'E', 8, NULL),
(53, 'W', 8, NULL),
(54, 'N', 7, 11),
(55, 'S', 7, NULL),
(56, 'E', 7, NULL),
(57, 'W', 7, NULL),
(58, 'N', 15, NULL),
(59, 'E', 15, NULL),
(60, 'S', 15, 13),
(61, 'W', 15, NULL),
(62, 'N', 16, 13),
(63, 'S', 16, NULL),
(64, 'E', 16, 14),
(65, 'W', 16, NULL),
(66, 'N', 17, NULL),
(67, 'W', 17, 14),
(68, 'S', 17, NULL),
(69, 'E', 17, 15),
(70, 'N', 18, 17),
(71, 'W', 18, 15),
(72, 'E', 18, NULL),
(73, 'S', 18, 16),
(74, 'N', 19, NULL),
(75, 'W', 19, NULL),
(76, 'E', 19, NULL),
(77, 'S', 19, 17),
(82, 'N', 20, 16),
(83, 'E', 20, NULL),
(84, 'W', 20, NULL),
(85, 'S', 20, 18),
(86, 'N', 21, 18),
(87, 'E', 21, 19),
(88, 'W', 21, 20),
(89, 'S', 21, NULL),
(90, 'N', 22, NULL),
(91, 'E', 22, NULL),
(92, 'W', 22, 19),
(93, 'S', 22, NULL),
(94, 'N', 23, NULL),
(95, 'S', 23, NULL),
(96, 'E', 23, 20),
(97, 'W', 23, 21),
(98, 'N', 24, NULL),
(99, 'S', 24, NULL),
(100, 'W', 24, NULL),
(101, 'E', 24, 21);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `coins`
--
ALTER TABLE `coins`
  ADD PRIMARY KEY (`id`),
  ADD KEY `roomId` (`roomId`);

--
-- Indices de la tabla `doors`
--
ALTER TABLE `doors`
  ADD PRIMARY KEY (`id`),
  ADD KEY `door_ibfk_1` (`keyId`);

--
-- Indices de la tabla `games`
--
ALTER TABLE `games`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ongoingGames_ibfk_1` (`currentRoomId`),
  ADD KEY `ongoingGames_ibfk_2` (`userId`),
  ADD KEY `ongoingGames_ibfk_3` (`mapId`);

--
-- Indices de la tabla `games_coins`
--
ALTER TABLE `games_coins`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ongoingGames_coins_ibfk_1` (`gamesId`),
  ADD KEY `ongoingGames_coins_ibfk_2` (`coinId`);

--
-- Indices de la tabla `games_doors`
--
ALTER TABLE `games_doors`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ongoingGames_doors_ibfk_1` (`gamesId`),
  ADD KEY `ongoingGames_doors_ibfk_2` (`doorId`);

--
-- Indices de la tabla `games_keys`
--
ALTER TABLE `games_keys`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ongoingGames_keys_ibfk_1` (`gamesId`),
  ADD KEY `ongoingGames_keys_ibfk_2` (`keyId`);

--
-- Indices de la tabla `keys`
--
ALTER TABLE `keys`
  ADD PRIMARY KEY (`id`),
  ADD KEY `roomId` (`roomId`);

--
-- Indices de la tabla `maps`
--
ALTER TABLE `maps`
  ADD PRIMARY KEY (`id`),
  ADD KEY `maps_ibfk_1` (`initialRoomId`),
  ADD KEY `maps_ibfk_2` (`finalRoomId`);

--
-- Indices de la tabla `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mapId` (`mapId`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `walls`
--
ALTER TABLE `walls`
  ADD PRIMARY KEY (`id`),
  ADD KEY `roomId` (`roomId`),
  ADD KEY `walls_ibfk_2` (`doorId`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `coins`
--
ALTER TABLE `coins`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT de la tabla `doors`
--
ALTER TABLE `doors`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `games`
--
ALTER TABLE `games`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71;

--
-- AUTO_INCREMENT de la tabla `games_coins`
--
ALTER TABLE `games_coins`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=164;

--
-- AUTO_INCREMENT de la tabla `games_doors`
--
ALTER TABLE `games_doors`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71;

--
-- AUTO_INCREMENT de la tabla `games_keys`
--
ALTER TABLE `games_keys`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=95;

--
-- AUTO_INCREMENT de la tabla `keys`
--
ALTER TABLE `keys`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `maps`
--
ALTER TABLE `maps`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `rooms`
--
ALTER TABLE `rooms`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `walls`
--
ALTER TABLE `walls`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `coins`
--
ALTER TABLE `coins`
  ADD CONSTRAINT `coins_ibfk_1` FOREIGN KEY (`roomId`) REFERENCES `rooms` (`id`);

--
-- Filtros para la tabla `doors`
--
ALTER TABLE `doors`
  ADD CONSTRAINT `door_ibfk_1` FOREIGN KEY (`keyId`) REFERENCES `keys` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Filtros para la tabla `games`
--
ALTER TABLE `games`
  ADD CONSTRAINT `games_ibfk_1` FOREIGN KEY (`currentRoomId`) REFERENCES `rooms` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `games_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `games_ibfk_3` FOREIGN KEY (`mapId`) REFERENCES `maps` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Filtros para la tabla `games_coins`
--
ALTER TABLE `games_coins`
  ADD CONSTRAINT `games_coins_ibfk_1` FOREIGN KEY (`gamesId`) REFERENCES `games` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `games_coins_ibfk_2` FOREIGN KEY (`coinId`) REFERENCES `coins` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `games_doors`
--
ALTER TABLE `games_doors`
  ADD CONSTRAINT `games_doors_ibfk_1` FOREIGN KEY (`gamesId`) REFERENCES `games` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `games_doors_ibfk_2` FOREIGN KEY (`doorId`) REFERENCES `doors` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `games_keys`
--
ALTER TABLE `games_keys`
  ADD CONSTRAINT `games_keys_ibfk_1` FOREIGN KEY (`gamesId`) REFERENCES `games` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `games_keys_ibfk_2` FOREIGN KEY (`keyId`) REFERENCES `keys` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `keys`
--
ALTER TABLE `keys`
  ADD CONSTRAINT `keys_ibfk_1` FOREIGN KEY (`roomId`) REFERENCES `rooms` (`id`);

--
-- Filtros para la tabla `maps`
--
ALTER TABLE `maps`
  ADD CONSTRAINT `maps_ibfk_1` FOREIGN KEY (`initialRoomId`) REFERENCES `rooms` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `maps_ibfk_2` FOREIGN KEY (`finalRoomId`) REFERENCES `rooms` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `rooms`
--
ALTER TABLE `rooms`
  ADD CONSTRAINT `rooms_ibfk_1` FOREIGN KEY (`mapId`) REFERENCES `maps` (`id`);

--
-- Filtros para la tabla `walls`
--
ALTER TABLE `walls`
  ADD CONSTRAINT `walls_ibfk_1` FOREIGN KEY (`roomId`) REFERENCES `rooms` (`id`),
  ADD CONSTRAINT `walls_ibfk_2` FOREIGN KEY (`doorId`) REFERENCES `doors` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
