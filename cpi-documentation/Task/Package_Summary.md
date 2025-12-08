# Consolidated Technical Report for SAP CPI Package

## 1. High-level architecture
The architecture of the SAP CPI package is designed to facilitate the mass upload of Supplier Purchase Orders (PO) from an S/4HANA system. The integration flow leverages various components including Groovy scripts for data transformation and processing, and utilizes email notifications for sending PDF attachments. The overall flow is orchestrated through an iFlow that connects the sender and receiver systems, ensuring seamless data exchange.

## 2. Purpose of each iFlow
The primary iFlow in this package is responsible for:
- **Mass Upload of Supplier Purchase Orders**: This iFlow processes incoming Supplier PO data, generates linked keys for SAP objects, and sends email notifications with attached PDF documents.

## 3. Sender/Receiver systems (Consolidated)
- **Sender System**: SAP S/4HANA, which sends Supplier PO data to the CPI.
- **Receiver System**: An email service (e.g., Microsoft Graph API) that receives the generated email notifications with PDF attachments.

## 4. Adapter types used (Consolidated)
- **HTTP Adapter**: Used for receiving incoming requests from the S/4HANA system.
- **Mail Adapter**: Utilized for sending email notifications with PDF attachments.

## 5. Step-by-step flow explanation (For each iFlow)
1. **Receive Supplier PO Data**: The iFlow starts by receiving Supplier PO data from the S/4HANA system via the HTTP adapter.
2. **Process Data with Groovy Scripts**:
   - **Script 1**: Combines the Supplier Invoice and Fiscal Year to create a linked key.
   - **Script 2**: Constructs a linked key using the Purchase Order and Purchase Order Item.
3. **Generate PDF Attachment**: The iFlow processes the PDF bytes and prepares the email content.
4. **Send Email Notification**: The iFlow sends an email with the PDF attachment to the specified recipient using the Mail adapter.

## 6. Mapping logic summary (Summarize XSLT, Mappings)
No XSLT mappings are provided in this package. The mapping logic is primarily handled through Groovy scripts that concatenate various identifiers to create linked keys necessary for further processing.

## 7. Groovy script explanations (Summarize all scripts and their usage/purpose)
- **Script 1 (`script1.groovy`)**: 
  - Purpose: Combines the `SupplierInvoice` and `FiscalYear` properties to create a `LinkedSAPObjectKey`.
  - Usage: This key is used for identifying the SAP object uniquely in subsequent processing steps.
  
- **Script 2 (`script2.groovy`)**: 
  - Purpose: Constructs a `LinkedSAPObjectKey` by concatenating the `PurchaseOrder`, a fixed string '000', and the `PurchaseOrderItem`.
  - Usage: This key is stored as an exchange property for use in OData adapter calls and is also set as a header for debugging.

- **Script 3 (`script3.groovy`)**: 
  - Purpose: Prepares the email payload with the PDF attachment, including subject and body content.
  - Usage: This script encodes the PDF bytes in Base64 and constructs a JSON payload for sending via email.

## 8. Error handling
Error handling mechanisms are not explicitly detailed in the provided scripts. However, the iFlow should ideally include error handling steps to manage exceptions during data processing and email sending, such as logging errors and sending alerts.

## 9. Security/authentication
The package does not specify security measures or authentication methods. However, it is recommended to implement OAuth or similar authentication mechanisms for the Mail adapter to ensure secure email transmission.

## 10. Deployment notes
- Ensure that all necessary configurations for the HTTP and Mail adapters are set up in the CPI tenant.
- Validate the Groovy scripts for any dependencies or required properties before deployment.
- Test the iFlow thoroughly in a development environment before moving to production to ensure all components work as expected.
