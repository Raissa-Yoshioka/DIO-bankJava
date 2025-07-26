package dio.bankJava.models;

import java.time.OffsetDateTime;
import java.util.UUID;

public record MoneyAudit (
    UUID transactionID,
    AccountType targetTAccountType,
    String description,
    OffsetDateTime createdAt
) {

}
