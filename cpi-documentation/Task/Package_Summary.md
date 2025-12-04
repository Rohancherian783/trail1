# Consolidated Technical Report for CPI Package

## 1. High-level architecture
The architecture of the CPI package is designed to facilitate the mass upload of Supplier Purchase Orders (PO) from an S/4HANA system. It integrates various components including Groovy scripts for data processing, and utilizes email notifications for successful uploads. The architecture leverages SAP Cloud Platform Integration (CPI) capabilities to handle data transformation and communication between systems.

## 2. Purpose of each iFlow
The primary iFlow in this package is responsible for the mass upload of Supplier POs from S/4HANA. It orchestrates the data flow from the source system, processes the data using Groovy scripts, and sends email notifications with attached PDF documents.

## 3. Sender/Receiver systems (Consolidated)
- **Sender System**: S/4HANA (Source of Supplier POs)
- **Receiver System**: Email service (for sending notifications with PDF attachments)

## 4. Adapter types used (Consolidated)
- **HTTP Adapter**: Used for receiving incoming requests from the S/4HANA system.
- **Mail Adapter**: Used for sending email notifications with PDF attachments.

## 5. Step-by-step flow explanation (For each iFlow)
1. **Receive Purchase Order Data**: The iFlow starts by receiving data from the S/4HANA system via the HTTP adapter.
2. **Process Data**: The data is processed through a series of Groovy scripts:
   - **Script 1**: Generates a linked key using the Supplier Invoice and Fiscal Year.
   - **Script 2**: Constructs a linked key using the Purchase Order and Purchase Order Item.
3. **Generate PDF Attachment**: The iFlow encodes the PDF document into Base64 format and prepares it for email.
4. **Send Email Notification**: The iFlow sends an email with the PDF attached to the specified recipient.

## 6. Mapping logic summary (Summarize XSLT, Mappings)
No XSLT mappings are provided in the current package. The mapping logic is handled entirely through Groovy scripts, which concatenate various identifiers to create linked keys necessary for processing.

## 7. Groovy script explanations (Summarize all scripts and their usage/purpose)
- **Script 1 (`script1.groovy`)**: 
  - Purpose: Combines the Supplier Invoice and Fiscal Year to create a unique linked key.
  - Usage: This linked key is stored as a property for further processing.

- **Script 2 (`script2.groovy`)**: 
  - Purpose: Constructs a linked key from the Purchase Order and Purchase Order Item by concatenating them with '000' in between.
  - Usage: The linked key is stored as a property and also set as a header for debugging.

- **Script 3 (`script3.groovy`)**: 
  - Purpose: Encodes the PDF bytes into Base64 format and prepares an email payload with the PDF attached.
  - Usage: This script is crucial for sending email notifications with the generated PDF.

## 8. Error handling
Error handling is not explicitly defined in the provided scripts. However, the iFlow should ideally include mechanisms to handle scenarios such as:
- Missing PDF data (handled in Script 3).
- Invalid Purchase Order or Item data (should be validated before processing).

## 9. Security/authentication
The package does not specify any security or authentication mechanisms. It is assumed that the CPI environment is secured and that appropriate authentication methods are in place for the HTTP and Mail adapters.

## 10. Deployment notes
- Ensure that all necessary configurations for the HTTP and Mail adapters are set up in the CPI environment.
- Validate that the email recipient addresses are correct and that the email service is operational.
- Test the iFlow thoroughly in a development environment before deploying to production to ensure all scripts function as expected and handle edge cases appropriately.
