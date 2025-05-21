-- INSERT INTO member 테이블
INSERT INTO member (member_id, create_at, update_at, email, password)
VALUES
    (1, CURRENT_TIMESTAMP, NULL, 'user1@example.com', 'password1');

-- INSERT INTO vm 테이블
INSERT INTO vm (vm_id, member_id, memory, storage, vcpu, create_at, update_at, description, private_ip, title, vm_status)
VALUES
    (1, 1, 16, 4, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 1', '192.168.10.10', 'VM1', 'STARTING'),
    (2, 1, 8, 8, 2, CURRENT_TIMESTAMP, NULL, 'Test VM 2', '192.168.10.11', 'VM2', 'RUNNING'),
    (3, 1, 16, 16, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 3', '192.168.10.12', 'VM3', 'PENDING'),
    (4, 1, 16, 32, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 4', '192.168.10.13', 'VM4', 'REBOOTING'),
    (5, 1, 32, 64, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 5', '192.168.10.14', 'VM5', 'TERMINATING'),
    (6, 1, 16, 128, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 6', '192.168.10.15', 'VM6', 'TERMINATED');

-- INSERT INTO tag 테이블
INSERT INTO tag (tag_id, create_at, update_at, title)
VALUES
    (1, CURRENT_TIMESTAMP, NULL, 'DEV'),
    (2, CURRENT_TIMESTAMP, NULL, 'PROD'),
    (3, CURRENT_TIMESTAMP, NULL, 'TEST'),
    (4, CURRENT_TIMESTAMP, NULL, 'QA');

-- INSERT INTO vm_tag 테이블
INSERT INTO vm_tag (vm_tag_id, vm_id, tag_id, create_at, update_at)
VALUES
    (1, 1, 1, CURRENT_TIMESTAMP, NULL),
    (2, 1, 2, CURRENT_TIMESTAMP, NULL),
    (3, 2, 1, CURRENT_TIMESTAMP, NULL);

-- INSERT INTO inbound_ip 테이블
INSERT INTO inbound_ip (inbound_ip_id, vm_id, create_at, update_at, address)
VALUES
    (1, 1, CURRENT_TIMESTAMP, NULL, '10.0.0.1'),
    (2, 2, CURRENT_TIMESTAMP, NULL, '10.0.0.2');

-- INSERT INTO outbound_ip 테이블
INSERT INTO outbound_ip (outbound_ip_id, vm_id, create_at, update_at, address)
VALUES
    (1, 1, CURRENT_TIMESTAMP, NULL, '8.8.8.8'),
    (2, 2, CURRENT_TIMESTAMP, NULL, '8.8.4.4');

-- INSERT INTO open_port 테이블
INSERT INTO open_port (open_port_id, inbound_ip_id, outbound_ip_id, port, create_at, update_at)
VALUES
    (1, 1, 1, 80, CURRENT_TIMESTAMP, NULL),
    (2, 2, 2, 443, CURRENT_TIMESTAMP, NULL);