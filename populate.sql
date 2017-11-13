INSERT INTO customer(id,
                    cif_id,
                    type,
                    firstname,
                    middlename,
                    lastname,
                    phone,
                    email,
                    address_line,
                    bvn)
     VALUES (1,
             'R00000023',
             'RETAIL',
             'ADA',
             'TAYO',
             'LATIFAT',
             '08039809890',
             'dkfj@yahoo.com',
             'No 7 oju street',
             '22222222222');



INSERT INTO account(id,
                    name,
                    number,
                    scheme_type,
                    scheme_code,
                    open_date,
                    sol,
                    last_process_type,
                    last_process_date,
                    customer_id)
     VALUES (1,
             'ADA',
             '23443234545',
             'SBA',
             'SB101',
             '2013-09-06',
             '999',
             'DOC',
             '2014-09-06',
             1);

INSERT INTO account(id,
                    name,
                    number,
                    scheme_type,
                    scheme_code,
                    open_date,
                    sol,
                    last_process_type,
                    last_process_date,
                    customer_id)
     VALUES (2,
             'ADA',
             '23443234885',
             'SBA',
             'SB102',
             '2013-10-06',
             '999',
             'DOC',
             '2014-10-06',
             1);

INSERT INTO document(id,
                    type,
                    ref_name,
                    status,
                    last_process_response,
                    account_id)
     VALUES (1,
             'TIN',
             'TIN-0001',
             'A',
             '',
             1);

INSERT INTO document(id,
                    type,
                    ref_name,
                    status,
                    last_process_response,
                    account_id)
     VALUES (2,
             'PASSPORT',
             'PP-0001',
             'A',
             '',
             1);

INSERT INTO document(id,
                    type,
                    ref_name,
                    status,
                    last_process_response,
                    account_id)
     VALUES (3,
             'TIN',
             'TIN-0002',
             'A',
             '',
             2);



COMMIT;