USE porta_db

-- Crear Administrator
EXEC CreateAdministrator 'admin', 'admin', 'federicobettic@gmail.com', '$2a$10$VE1fyuP3SL9yK1i1NkRHweB76XI1o6qjzJoLUnUzdKBhibqt2H8MK'


-- Crear Subscriber
EXEC CreateSubscriber 'federico', 'bettic', 'fbettic@gmail.com', '1111111111111', '1998-07-11', '$2y$10$Y5/QxW.Z4DfN0YQlPwaCk.9/Le1ZhpDRLWffX9rV7gtcMl2gYpRO2', 1;


-- Crear Fees
EXEC CreateFee 'BANNER_PRIORITY_LOW', 2.5
EXEC CreateFee 'BANNER_PRIORITY_MEDIUM', 6.5
EXEC CreateFee 'BANNER_PRIORITY_HIGH', 9.8
EXEC CreateFee 'BANNER_SIZE_SMALL', 4.5
EXEC CreateFee 'BANNER_SIZE_MEDIUM', 8.5
EXEC CreateFee 'BANNER_SIZE_LARGE', 10.6
EXEC CreateFee 'ALLPAGES', 15.5
EXEC CreateFee 'LOGIN_FEE', 12.3
EXEC CreateFee 'SIGNUP_FEE', 13.5

-- Crear Priorities
EXEC CreateBannerPriority "LOW", 5, 1
EXEC CreateBannerPriority "MEDIUM", 10, 2
EXEC CreateBannerPriority "HIGH", 15, 3

-- Crear Sizes
EXEC CreateSizeType 4, 'SMALL', 5, 150, 100
EXEC CreateSizeType 5, 'MEDIUM', 5, 300, 100
EXEC CreateSizeType 6, 'LARGE', 5, 150, 600

select * from FeeType