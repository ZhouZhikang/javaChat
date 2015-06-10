CREATE DATABASE `javaChat` /*!40100 DEFAULT CHARACTER SET utf8 */;
CREATE TABLE `Friend` (
  `Fid` int(11) NOT NULL AUTO_INCREMENT,
  `FriendId` varchar(100) DEFAULT NULL,
  `Whos` varchar(100) DEFAULT NULL,
  `NewMessage` int(11) DEFAULT NULL,
  `IsOnline` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`Fid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
CREATE TABLE `Message` (
  `MessageId` int(11) NOT NULL AUTO_INCREMENT,
  `FromUser` varchar(100) DEFAULT NULL,
  `ToUser` varchar(100) DEFAULT NULL,
  `Content` varchar(1000) DEFAULT NULL,
  `SendTime` datetime DEFAULT NULL,
  PRIMARY KEY (`MessageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `User` (
  `UserId` varchar(100) NOT NULL,
  `UserPWD` varchar(100) DEFAULT NULL,
  `IsOnline` tinyint(1) DEFAULT NULL,
  `RegisterDate` datetime DEFAULT NULL,
  `RecentLoginIp` varchar(100) DEFAULT NULL,
  `RecentLoginPort` int(11) DEFAULT NULL,
  `RecentLogoutDate` datetime DEFAULT NULL,
  `HasNewMessage` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
