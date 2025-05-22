INSERT INTO member (member_id, create_at, update_at, email, password)
VALUES
    (1, CURRENT_TIMESTAMP, NULL, 'user1@example.com', 'password1');

INSERT INTO vm (vm_id, member_id, memory, storage, vcpu, create_at, update_at, description, private_ip, name, vm_status, deleted)
VALUES
    (1, 1, 16, 4, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 1', '192.168.10.10', 'VM1', 'STARTING', 0),
    (2, 1, 8, 8, 2, CURRENT_TIMESTAMP, NULL, 'Test VM 2', '192.168.10.11', 'VM2', 'RUNNING', 0),
    (3, 1, 16, 16, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 3', '192.168.10.12', 'VM3', 'PENDING', 0),
    (4, 1, 16, 32, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 4', '192.168.10.13', 'VM4', 'REBOOTING', 0),
    (5, 1, 32, 64, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 5', '192.168.10.14', 'VM5', 'TERMINATING', 0),
    (6, 1, 16, 128, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 6', '192.168.10.15', 'VM6', 'TERMINATED', 0);

INSERT INTO network (network_id, vm_id, name, open_ip, open_port, create_at, update_at)
VALUES
    (1, 1, "NETWORK1", "1.1.1.1", 10000, CURRENT_TIMESTAMP, NULL),
    (2, 1, "NETWORK2", "1.1.1.2", 10001, CURRENT_TIMESTAMP, NULL),
    (3, 2, "NETWORK3", "1.1.1.3", 10002, CURRENT_TIMESTAMP, NULL),
    (4, 3, "NETWORK4", "1.1.1.4", 10003, CURRENT_TIMESTAMP, NULL),
    (5, 3, "NETWORK5", "1.1.1.5", 10004, CURRENT_TIMESTAMP, NULL),
    (6, 3, "NETWORK6", "1.1.1.6", 10005, CURRENT_TIMESTAMP, NULL),
    (7, 1, "NETWORK7", "1.1.1.7", 10006, CURRENT_TIMESTAMP, NULL);

INSERT INTO tag (tag_id, create_at, update_at, name)
VALUES
    (1, CURRENT_TIMESTAMP, NULL, 'DEV'),
    (2, CURRENT_TIMESTAMP, NULL, 'PROD'),
    (3, CURRENT_TIMESTAMP, NULL, 'TEST'),
    (4, CURRENT_TIMESTAMP, NULL, 'QA');

INSERT INTO vm_tag (vm_tag_id, vm_id, tag_id, create_at, update_at)
VALUES
    (1, 1, 1, CURRENT_TIMESTAMP, NULL),
    (2, 1, 2, CURRENT_TIMESTAMP, NULL),
    (3, 2, 1, CURRENT_TIMESTAMP, NULL);

