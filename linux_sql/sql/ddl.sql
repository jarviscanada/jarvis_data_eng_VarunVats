CREATE TABLE host_info
  (
     id               SERIAL NOT NULL PRIMARY KEY,
     hostname         VARCHAR NOT NULL,
     cpu_number       INT     NOT NULL,
     cpu_architecture VARCHAR     NOT NULL,
     cpu_model        VARCHAR NOT NULL,
     cpu_mhz          INT     NOT NULL,
    L2_cache          BYTEA     NOT NULL,
    total_mem         INT     NOT NULL,
    timestamp         TIMESTAMP NOT NULL

  );


CREATE TABLE host_usage
  (
     timestamp      TIMESTAMP NOT NULL,
     host_id           SERIAL   NOT NULL PRIMARY KEY,
     memory_free      INT       NOT NULL,
     cpu_idle         INT       NOT NULL,
     cpu_kernel       INT      NOT NULL,
    disk_io           INT      NOT NULL,
    disk_availaible    BYTEA     NOT NULL,



     CONSTRAINT fk_info
     FOREIGN KEY (host_id)
     REFERENCES host_info (id)
  );

