SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS vm_tag;
DROP TABLE IF EXISTS vm;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS network;
DROP TABLE IF EXISTS member;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE member (
                        member_id INTEGER NOT NULL AUTO_INCREMENT,
                        create_at TIMESTAMP(6) NOT NULL,
                        update_at TIMESTAMP(6),
                        email VARCHAR(255),
                        password VARCHAR(255),
                        PRIMARY KEY (member_id)
) ENGINE=InnoDB;

CREATE TABLE tag (
                     tag_id INTEGER NOT NULL AUTO_INCREMENT,
                     create_at TIMESTAMP(6) NOT NULL,
                     update_at TIMESTAMP(6),
                     title VARCHAR(255),
                     PRIMARY KEY (tag_id),
                     UNIQUE KEY (title)
) ENGINE=InnoDB;

CREATE TABLE vm (
                    member_id INTEGER,
                    memory INTEGER NOT NULL,
                    storage INTEGER NOT NULL,
                    vcpu INTEGER NOT NULL,
                    vm_id INTEGER NOT NULL AUTO_INCREMENT,
                    create_at TIMESTAMP(6) NOT NULL,
                    update_at TIMESTAMP(6),
                    description VARCHAR(255),
                    private_ip VARCHAR(255),
                    network_id INTEGER NOT NULL ,
                    title VARCHAR(255),
                    vm_status ENUM ('PENDING','REBOOTING','RUNNING','STARTING','TERMINATED','TERMINATING') NOT NULL,
                    PRIMARY KEY (vm_id)
) ENGINE=InnoDB;

CREATE TABLE vm_tag (
                        tag_id INTEGER NOT NULL,
                        vm_id INTEGER NOT NULL,
                        vm_tag_id INTEGER NOT NULL AUTO_INCREMENT,
                        create_at TIMESTAMP(6) NOT NULL,
                        update_at TIMESTAMP(6),
                        PRIMARY KEY (vm_tag_id)
) ENGINE=InnoDB;

CREATE TABLE network (
                        network_id INTEGER NOT NULL AUTO_INCREMENT,
                        title VARCHAR(255),
                        open_ip VARCHAR(255),
                        open_port int(255),
                        create_at TIMESTAMP(6) NOT NULL,
                        update_at TIMESTAMP(6),
                        PRIMARY KEY (network_id)
) ENGINE=InnoDB;

ALTER TABLE vm
    ADD CONSTRAINT fk_vm_member_id
        FOREIGN KEY (member_id) REFERENCES member (member_id);

ALTER TABLE vm
    ADD CONSTRAINT fk_vm_network_id
        FOREIGN KEY (network_id) references network (network_id);

ALTER TABLE vm_tag
    ADD CONSTRAINT fk_vm_tag_tag_id
        FOREIGN KEY (tag_id) REFERENCES tag (tag_id);

ALTER TABLE vm_tag
    ADD CONSTRAINT fk_vm_tag_vm_id
        FOREIGN KEY (vm_id) REFERENCES vm (vm_id);