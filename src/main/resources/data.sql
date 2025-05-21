INSERT INTO member (member_id, create_at, update_at, email, password)
VALUES
    (1, CURRENT_TIMESTAMP, NULL, 'user1@example.com', 'password1');

INSERT INTO network (network_id, title, open_ip, open_port, create_at, update_at)
VALUES
    (1, "NETWORK1", "1.1.1.1", 10000, CURRENT_TIMESTAMP, NULL),
    (2, "NETWORK2", "1.1.1.2", 10001, CURRENT_TIMESTAMP, NULL),
    (3, "NETWORK3", "1.1.1.3", 10002, CURRENT_TIMESTAMP, NULL),
    (4, "NETWORK4", "1.1.1.4", 10003, CURRENT_TIMESTAMP, NULL);

INSERT INTO vm (vm_id, member_id, memory, storage, vcpu, create_at, update_at, description, private_ip, network_id, title, vm_status)
VALUES
    (1, 1, 16, 4, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 1', '192.168.10.10', 1, 'VM1', 'STARTING'),
    (2, 1, 8, 8, 2, CURRENT_TIMESTAMP, NULL, 'Test VM 2', '192.168.10.11', 2, 'VM2', 'RUNNING'),
    (3, 1, 16, 16, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 3', '192.168.10.12', 3, 'VM3', 'PENDING'),
    (4, 1, 16, 32, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 4', '192.168.10.13', 4, 'VM4', 'REBOOTING'),
    (5, 1, 32, 64, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 5', '192.168.10.14', 1, 'VM5', 'TERMINATING'),
    (6, 1, 16, 128, 4, CURRENT_TIMESTAMP, NULL, 'Test VM 6', '192.168.10.15', 2, 'VM6', 'TERMINATED');

INSERT INTO tag (tag_id, create_at, update_at, title)
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

