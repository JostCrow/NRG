ALTER TABLE `device` ADD `location` VARCHAR( 150 ) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL AFTER `name` ;
ALTER TABLE `device` ADD `device_url` VARCHAR( 150 ) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL AFTER `logo_url`;
ALTER TABLE `device` CHANGE `photo_url` `background_url` VARCHAR( 150 ) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL;