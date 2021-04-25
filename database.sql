CREATE TABLE test.`service` (
	`id` bigint(20) NOT NULL,
    `ip` varchar(16) NOT NULL,
    `project` varchar(64) DEFAULT NULL,
    `status` varchar(6) NOT NULL,
    `username` varchar(16) DEFAULT NULL,
    `password` varchar(32) DEFAULT NULL,
    `role` varchar(128) DEFAULT NULL,
    `system_version` varchar(64) NOT NULL,
    `cpu` varchar(64) NOT NULL,
    `memory` varchar(64) NOT NULL,
    `disk` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
    `remark` varchar(128) DEFAULT NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;