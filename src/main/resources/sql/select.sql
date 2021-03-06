SELECT ACCOUNT.*, TTA.TOTAL_TRANSFERRED_AMOUNT
FROM (SELECT SOURCE_ID, SUM(AMOUNT) AS TOTAL_TRANSFERRED_AMOUNT FROM TRANSFER WHERE TRANSFER_TIME >= '2020-01-01 00:00:00.000' GROUP BY SOURCE_ID) AS TTA, ACCOUNT
WHERE TTA.TOTAL_TRANSFERRED_AMOUNT >1000 AND TTA.SOURCE_ID = ACCOUNT.ID;