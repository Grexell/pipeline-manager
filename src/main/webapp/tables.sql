CREATE TABLE `execution` (
  `id_execution` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` datetime NOT NULL,
  PRIMARY KEY (`id_execution`)
);

CREATE TABLE `execution_task` (
  `id_execution` int(11) NOT NULL,
  `id_task` int(11) NOT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `status` varchar(15) NOT NULL,
  PRIMARY KEY (`id_execution`,`id_task`),
  KEY `id_task` (`id_task`),
  CONSTRAINT `execution_task_ibfk_1` FOREIGN KEY (`id_execution`) REFERENCES `execution` (`id_execution`),
  CONSTRAINT `execution_task_ibfk_2` FOREIGN KEY (`id_task`) REFERENCES `task` (`id_task`)
);

CREATE TABLE `pipeline` (
  `id_pipeline` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id_pipeline`),
  UNIQUE KEY `name` (`name`)
);

CREATE TABLE `task` (
  `id_task` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(80) DEFAULT NULL,
  `id_pipeline` int(11) NOT NULL,
  `action` varchar(15) NOT NULL,
  PRIMARY KEY (`id_task`),
  KEY `id_pipeline` (`id_pipeline`),
  CONSTRAINT `task_ibfk_1` FOREIGN KEY (`id_pipeline`) REFERENCES `pipeline` (`id_pipeline`) ON DELETE CASCADE
);

CREATE TABLE `transition` (
  `prev_task` int(11) NOT NULL,
  `next_task` int(11) NOT NULL,
  PRIMARY KEY (`prev_task`,`next_task`),
  KEY `transition_ibfk_2` (`next_task`),
  CONSTRAINT `transition_ibfk_1` FOREIGN KEY (`prev_task`) REFERENCES `task` (`id_task`) ON DELETE CASCADE,
  CONSTRAINT `transition_ibfk_2` FOREIGN KEY (`next_task`) REFERENCES `task` (`id_task`) ON DELETE CASCADE
);