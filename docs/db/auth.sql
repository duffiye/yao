create table if not exists t_oauth_client_details
(
    client_id               varchar(40)   not null primary key,
    resource_ids            varchar(256)  null,
    client_secret           varchar(256)  null,
    scope                   varchar(256)  null,
    authorized_grant_types  varchar(256)  null,
    web_server_redirect_uri varchar(256)  null,
    authorities             varchar(256)  null,
    access_token_validity   int           null,
    refresh_token_validity  int           null,
    additional_information  varchar(4096) null,
    autoapprove             varchar(256)  null
) charset = utf8mb4;

INSERT INTO t_oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types,
                                    web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity,
                                    additional_information, autoapprove)
VALUES ('cloud', null, '$2a$10$X1HOPGX6ADkQn4rvtk.C4uaz8vF1TdpY2aP/iC.3UKlonvco/k9e.', 'server',
        'password,refresh_token,authorization_code', null, null, null, null, null, 'false');

