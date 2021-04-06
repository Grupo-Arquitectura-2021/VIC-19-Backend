USE db_cabalito;

-- TRIGGERS

DELIMITER |
CREATE TRIGGER tg_insert_h_person
AFTER INSERT ON person
FOR EACH ROW
BEGIN
	INSERT INTO `h_person`(`id_person`,`first_name`,`last_name`,`phone_number`,`status`,`tx_date`,`tx_id_user`,`tx_host`,`tx_update`) VALUES ( NEW.id_person,NEW.first_name,NEW.last_name,NEW.phone_number,NEW.status, NEW.tx_date,NEW.tx_id_user,NEW.tx_host,NEW.tx_update);
END;
|
DELIMITER ;

DELIMITER |
CREATE TRIGGER tg_update_h_person
AFTER UPDATE ON person
FOR EACH ROW
BEGIN
	INSERT INTO `h_person`(`id_person`,`first_name`,`last_name`,`phone_number`,`status`,`tx_date`,`tx_id_user`,`tx_host`,`tx_update`) VALUES (NEW.id_person,NEW.first_name,NEW.last_name,NEW.phone_number,NEW.status, NEW.tx_date,NEW.tx_id_user,NEW.tx_host,NEW.tx_update);
    END;
|
DELIMITER ;

DELIMITER |
CREATE TRIGGER tg_insert_h_user
AFTER INSERT ON user
FOR EACH ROW
BEGIN
	INSERT INTO `h_user`(`id_user`,`password`,`email`,`status`,`tx_date`,`tx_id_user`,`tx_host`,`tx_update`) VALUES ( NEW.id_user,NEW.password,NEW.email,NEW.status,NEW.tx_date,NEW.tx_id_user,NEW.tx_host,NEW.tx_update);
END;
|
DELIMITER ;

DELIMITER |
CREATE TRIGGER tg_update_h_user
AFTER UPDATE ON user
FOR EACH ROW
BEGIN
	INSERT INTO `h_user`(`id_user`,`password`,`email`,`status`,`tx_date`,`tx_id_user`,`tx_host`,`tx_update`) VALUES (NEW.id_user,NEW.password,NEW.email,NEW.status,NEW.tx_date,NEW.tx_id_user,NEW.tx_host,NEW.tx_update);
END;
|
DELIMITER ;

DELIMITER |
CREATE TRIGGER tg_insert_h_seller
AFTER INSERT ON seller
FOR EACH ROW
BEGIN
	INSERT INTO `h_seller`(`id_seller`,`id_person`,`id_user`,`status`,`image_path`,`tx_date`,`tx_id_user`,`tx_host`,`tx_update`) VALUES (NEW.id_seller,NEW.id_person,NEW.id_user,NEW.status,NEW.image_path,NEW.tx_date,NEW.tx_id_user,NEW.tx_host,NEW.tx_update);
END;
|
DELIMITER ;

DELIMITER |
CREATE TRIGGER tg_update_h_seller
AFTER UPDATE ON seller
FOR EACH ROW
BEGIN
	INSERT INTO `h_seller`(`id_seller`,`id_person`,`id_user`,`status`,`image_path`,`tx_date`,`tx_id_user`,`tx_host`,`tx_update`) VALUES (NEW.id_seller,NEW.id_person,NEW.id_user,NEW.status,NEW.image_path,NEW.tx_date,NEW.tx_id_user,NEW.tx_host,NEW.tx_update);
END;
|
DELIMITER ;

delimiter |
CREATE TRIGGER tg_insert_h_publication AFTER INSERT ON publication
    FOR EACH ROW
    BEGIN
        INSERT INTO h_publication VALUES (null,NEW.id_publication,NEW.id_seller,NEW.id_brand,NEW.id_color,NEW.id_city, NEW.title
        ,NEW.model,NEW.door_number,NEW.license_plate,NEW.description,NEW.motor,NEW.price,NEW.date,NEW.status,NEW.tx_date,
        NEW.tx_id_user,NEW.tx_host,NEW.tx_update);
    END;
|
delimiter ;

delimiter |
CREATE TRIGGER tg_update_h_publication AFTER UPDATE ON publication
    FOR EACH ROW
    BEGIN
        INSERT INTO h_publication VALUES (null, NEW.id_publication,NEW.id_seller,NEW.id_brand,NEW.id_color,NEW.id_city,NEW.title,
        NEW.model,NEW.door_number,NEW.license_plate,NEW.description,NEW.motor,NEW.price,NEW.date,NEW.status,NEW.tx_date,
        NEW.tx_id_user,NEW.tx_host,NEW.tx_update);
    END;
|
delimiter ;

DELIMITER |
