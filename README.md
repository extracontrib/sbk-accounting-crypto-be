## Crypto Ledger Reference Implementation

This project provides Reference Implementation to let you implement Ledger for your cryptocurrency-based project. 
The purpose is to accelerate the cryptocurrency-based projects development.

In a nutshell, project lets your customer identify taxable events (Capital Gains & Income Tax) for list of transaction undertaken.  

### Build & Run

1. Clone project
2. Build with maven
```
mvn clean install
```
3. Run
```
cd sbk-accounting-crypto-be-api
mvn spring-boot:run
```

You can verify that Swagger UI is available at http://localhost:8080/swagger-ui.html

### QA

Perform next call either from command line or from Swagger UI. We basically send here set of user transactions to let the service identify taxable events

### Request
```
curl -X POST "http://localhost:8080/api/reports/calculate-gain-loss-events" -H "accept: application/json" -H "Content-Type: application/json" \
     -d '{ "userId": "jdoe", "accountingMethod": "FIFO", "transactions": [ { "amount": 10, "effectiveAt": 1695601856, "rate": 0.5, "transactionType": "DEPOSIT", "id": "d844ce82-b304-4b7e-b8d7-aa1d9d6fb49c" }, { "amount": 10, "effectiveAt": 1695601899, "rate": 0.2, "transactionType": "REVENUE", "id": "dd7fc4cc-b0ad-491d-9af8-a377e76f1ef3" }, { "amount": 15, "effectiveAt": 1695601953, "rate": 0.7, "transactionType": "EXPENSE", "id": "88138283-0c5c-443d-8103-fa38724a87dc" } ]}'
```

Inspect the response

### Response
```
{
  "userId": "jdoe",
  "accountingMethod": "FIFO",
  "events": [
    {
      "sourceTxId": "dd7fc4cc-b0ad-491d-9af8-a377e76f1ef3",
      "amount": 10,
      "rate": 0.2,
      "taxableAmountUsd": 2.0,
      "incomeType": "ORDINARY_INCOME"
    },
    {
      "sourceTxId": "88138283-0c5c-443d-8103-fa38724a87dc",
      "targetTxId": "d844ce82-b304-4b7e-b8d7-aa1d9d6fb49c",
      "amount": 10,
      "baseRate": 0.5,
      "rate": 0.7,
      "taxableAmountUsd": 2.0,
      "incomeType": "CAPITAL_GAIN",
      "capitalGainType": "SHORT_TERM"
    },
    {
      "sourceTxId": "88138283-0c5c-443d-8103-fa38724a87dc",
      "targetTxId": "dd7fc4cc-b0ad-491d-9af8-a377e76f1ef3",
      "amount": 5,
      "baseRate": 0.2,
      "rate": 0.7,
      "taxableAmountUsd": 2.5,
      "incomeType": "CAPITAL_GAIN",
      "capitalGainType": "SHORT_TERM"
    }
  ]
}
```