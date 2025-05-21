SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS vm_tag;
DROP TABLE IF EXISTS vm_seq;
DROP TABLE IF EXISTS vm;
DROP TABLE IF EXISTS tag_seq;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS outbound_ip_seq;
DROP TABLE IF EXISTS outbound_ip;
DROP TABLE IF EXISTS open_port_seq;
DROP TABLE IF EXISTS open_port;
DROP TABLE IF EXISTS member_seq;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS inbound_ip_seq;
DROP TABLE IF EXISTS inbound_ip;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE inbound_ip (
                            inbound_ip_id INTEGER NOT NULL AUTO_INCREMENT,
                            vm_id INTEGER,
                            create_at DATETIME(6) NOT NULL,
                            update_at DATETIME(6),
                            address VARCHAR(255),
                            PRIMARY KEY (inbound_ip_id)
) ENGINE=InnoDB;

CREATE TABLE member (
                        member_id INTEGER NOT NULL AUTO_INCREMENT,
                        create_at DATETIME(6) NOT NULL,
                        update_at DATETIME(6),
                        email VARCHAR(255),
                        password VARCHAR(255),
                        PRIMARY KEY (member_id)
) ENGINE=InnoDB;

CREATE TABLE open_port (
                           inbound_ip_id INTEGER,
                           open_port_id INTEGER NOT NULL AUTO_INCREMENT,
                           outbound_ip_id INTEGER,
                           port INTEGER NOT NULL,
                           create_at DATETIME(6) NOT NULL,
                           update_at DATETIME(6),
                           PRIMARY KEY (open_port_id)
) ENGINE=InnoDB;

CREATE TABLE outbound_ip (
                             outbound_ip_id INTEGER NOT NULL AUTO_INCREMENT,
                             vm_id INTEGER,
                             create_at DATETIME(6) NOT NULL,
                             update_at DATETIME(6),
                             address VARCHAR(255),
                             PRIMARY KEY (outbound_ip_id)
) ENGINE=InnoDB;

CREATE TABLE tag (
                     tag_id INTEGER NOT NULL AUTO_INCREMENT,
                     create_at DATETIME(6) NOT NULL,
                     update_at DATETIME(6),
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
                    create_at DATETIME(6) NOT NULL,
                    update_at DATETIME(6),
                    description VARCHAR(255),
                    private_ip VARCHAR(255),
                    title VARCHAR(255),
                    vm_status ENUM ('PENDING','REBOOTING','RUNNING','STARTING','TERMINATED','TERMINATING') NOT NULL,
                    PRIMARY KEY (vm_id)
) ENGINE=InnoDB;

CREATE TABLE vm_tag (
                        tag_id INTEGER NOT NULL,
                        vm_id INTEGER NOT NULL,
                        vm_tag_id INTEGER NOT NULL AUTO_INCREMENT,
                        create_at DATETIME(6) NOT NULL,
                        update_at DATETIME(6),
                        PRIMARY KEY (vm_tag_id)
) ENGINE=InnoDB;

ALTER TABLE inbound_ip
    ADD CONSTRAINT fk_inbound_ip_vm
        FOREIGN KEY (vm_id) REFERENCES vm (vm_id);

ALTER TABLE open_port
    ADD CONSTRAINT fk_open_port_inbound_ip_id
        FOREIGN KEY (inbound_ip_id) REFERENCES inbound_ip (inbound_ip_id);

ALTER TABLE open_port
    ADD CONSTRAINT fk_open_port_outbound_ip_id
        FOREIGN KEY (outbound_ip_id) REFERENCES outbound_ip (outbound_ip_id);

ALTER TABLE outbound_ip
    ADD CONSTRAINT fk_outbound_ip_vm_id
        FOREIGN KEY (vm_id) REFERENCES vm (vm_id);

ALTER TABLE vm
    ADD CONSTRAINT fk_vm_member_id
        FOREIGN KEY (member_id) REFERENCES member (member_id);

ALTER TABLE vm_tag
    ADD CONSTRAINT fk_vm_tag_tag_id
        FOREIGN KEY (tag_id) REFERENCES tag (tag_id);

ALTER TABLE vm_tag
    ADD CONSTRAINT fk_vm_tag_vm_id
        FOREIGN KEY (vm_id) REFERENCES vm (vm_id);