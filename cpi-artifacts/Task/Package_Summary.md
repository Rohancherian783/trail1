# Consolidated Technical Report for CPI Package

## 1. High-level architecture
The architecture of the CPI package is designed to facilitate the mass upload of Supplier Purchase Orders (PO) from an S/4HANA system. It utilizes various integration patterns to process incoming data, transform it, and send notifications via email. The architecture includes multiple iFlows, Groovy scripts for data manipulation, and error handling mechanisms.

## 2. Purpose of each iFlow
The primary iFlow in this package is responsible for orchestrating the mass upload of Supplier POs. It integrates with the S/4HANA system to retrieve PO data, processes it through various transformation steps, and sends email notifications with attached PDF documents.

## 3. Sender/Receiver systems (Consolidated)
- **Sender System**: S/4HANA (Source of Supplier PO data)
- **Receiver System**: Email service (for sending notifications with PDF attachments)

## 4. Adapter types used (Consolidated)
- **HTTP Adapter**: Used for receiving incoming requests from the S/4HANA system.
- **OData Adapter**: Utilized for sending data to the backend systems.
- **Mail Adapter**: Employed for sending email notifications with attachments.

## 5. Step-by-step flow explanation (For each iFlow)
1. **Receive PO Data**: The iFlow starts by receiving Supplier PO data from the S/4HANA system via the HTTP adapter.
2. **Process Data**: The incoming data is processed through a series of Groovy scripts:
   - **Script 1**: Combines the Supplier Invoice and Fiscal Year to create a linked key.
   - **Script 2**: Constructs a linked key using the Purchase Order and Purchase Order Item.
3. **Generate PDF**: The iFlow generates a PDF document based on the processed data.
4. **Email Notification**: The final step involves sending an email with the generated PDF attached using the Mail adapter.

## 6. Mapping logic summary (Summarize XSLT, Mappings)
No XSLT mappings are provided in the current package. The mapping logic is primarily handled through Groovy scripts that concatenate various properties to create linked keys for further processing.

## 7. Groovy script explanations (Summarize all scripts and their usage/purpose)
- **Script 1 (`script1.groovy`)**: 
  - Purpose: Combines the `SupplierInvoice` and `FiscalYear` properties to create a `LinkedSAPObjectKey`.
  - Usage: This key is used for identifying the Supplier PO uniquely in subsequent processing steps.

- **Script 2 (`script2.groovy`)**: 
  - Purpose: Constructs a `LinkedSAPObjectKey` by concatenating the `PurchaseOrder` with '000' and `PurchaseOrderItem`.
  - Usage: This key is stored as an exchange property for use in the OData adapter.

- **Script 3 (`script3.groovy`)**: 
  - Purpose: Prepares an email payload with the PDF attachment, including subject and body content.
  - Usage: This script encodes the PDF bytes to Base64 and sets the email headers and body for sending notifications.

## 8. Error handling
Error handling is implemented through the use of message headers to indicate the status of processing. For instance, if no PDF bytes are found, the script sets the header `X-EMAIL-STATUS` to "NO_PDF" and provides a relevant message in the body.

## 9. Security/authentication
The package does not explicitly detail security measures or authentication methods. However, it is assumed that standard SAP CPI security practices are followed, including secure connections and proper handling of sensitive data.

## 10. Deployment notes
When deploying this CPI package, ensure that all necessary configurations for the sender and receiver systems are in place. Verify that the email service is correctly set up to handle outgoing messages and that the required properties for the Groovy scripts are available in the message context.
