INSERT INTO member (member_id, create_at, update_at, email, password)
VALUES
    (1, CURRENT_TIMESTAMP, NULL, 'user1@example.com', 'password1'),
    (2, CURRENT_TIMESTAMP, NULL, 'user2@example.com', 'password2');

INSERT INTO vm (vm_id, member_id, memory, storage, vcpu, create_at, update_at, description, private_ip, name, vm_status, deleted)
VALUES
    (1, 1, 16, 4, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 1', '192.168.10.10', 'VM1', 'STARTING', 0),
    (2, 1, 8, 8, 2, CURRENT_TIMESTAMP, NULL, 'Test VM 2', '192.168.10.11', 'VM2', 'RUNNING', 0),
    (3, 1, 16, 16, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 3', '192.168.10.12', 'VM3', 'PENDING', 0),
    (4, 1, 16, 32, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 4', '192.168.10.13', 'VM4', 'REBOOTING', 0),
    (5, 1, 32, 64, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 5', '192.168.10.14', 'VM5', 'TERMINATING', 0),
    (6, 1, 16, 128, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 6', '192.168.10.15', 'VM6', 'TERMINATED', 0),
    (7, 1, 16, 128, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 7', '192.168.10.16', 'VM7', 'STARTING', 0),
    (8, 1, 4, 64, 8, CURRENT_TIMESTAMP, NULL, 'Test VM 8', '192.168.10.17', 'VM8', 'REBOOTING', 0),
    (9, 1, 8, 64, 16, CURRENT_TIMESTAMP, NULL, 'Test VM 9', '192.168.10.18', 'VM9', 'PENDING', 0);

INSERT INTO network (network_id, name, open_ip, open_port, create_at, update_at, vm_id)
VALUES
    (1, "NETWORK1", "1.1.1.1", 10000, CURRENT_TIMESTAMP, NULL, 1),
    (2, "NETWORK2", "1.1.1.2", 10001, CURRENT_TIMESTAMP, NULL, 1),
    (3,  "NETWORK3", "1.1.1.3", 10002, CURRENT_TIMESTAMP, NULL, 2),
    (4, "NETWORK4", "1.1.1.4", 10003, CURRENT_TIMESTAMP, NULL, 3),
    (5, "NETWORK5", "1.1.1.5", 10004, CURRENT_TIMESTAMP, NULL, 4),
    (6, "NETWORK6", "1.1.1.6", 10005, CURRENT_TIMESTAMP, NULL, 5),
    (7, "NETWORK7", "1.1.1.7", 10006, CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO tag (tag_id, create_at, update_at, name)
VALUES
    (1, CURRENT_TIMESTAMP, NULL, 'DEV'),
    (2, CURRENT_TIMESTAMP, NULL, 'PROD'),
    (3, CURRENT_TIMESTAMP, NULL, 'TEST'),
    (4, CURRENT_TIMESTAMP, NULL, 'QA');

INSERT INTO vm_tag (vm_tag_id, vm_id, tag_id, create_at, update_at)
VALUES
    (1, 1, 1, CURRENT_TIMESTAMP, NULL),
    (2, 2, 2, CURRENT_TIMESTAMP, NULL),
    (3, 3, 3, CURRENT_TIMESTAMP, NULL),
    (4, 4, 4, CURRENT_TIMESTAMP, NULL),
    (5, 5, 1, CURRENT_TIMESTAMP, NULL),
    (6, 6, 2, CURRENT_TIMESTAMP, NULL),
    (7, 7, 3, CURRENT_TIMESTAMP, NULL),
    (8, 8, 4, CURRENT_TIMESTAMP, NULL),
    (9, 9, 1, CURRENT_TIMESTAMP, NULL),
    (10, 1, 2, CURRENT_TIMESTAMP, NULL),
    (11, 2, 3, CURRENT_TIMESTAMP, NULL),
    (12, 3, 4, CURRENT_TIMESTAMP, NULL),
    (13, 4, 1, CURRENT_TIMESTAMP, NULL);


