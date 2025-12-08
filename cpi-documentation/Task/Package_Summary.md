# Consolidated Technical Report for CPI Package

## 1. High-level architecture
The architecture of the CPI package revolves around the integration of Supplier Purchase Orders (PO) from an S/4HANA system. The package utilizes various components such as Groovy scripts for data processing, and it is designed to handle PDF attachments and email notifications. The overall flow involves receiving data, processing it, and sending notifications with attachments.

## 2. Purpose of each iFlow
The iFlow in this package is designed to facilitate the mass upload of Supplier Purchase Orders from S/4HANA. It processes incoming data, generates linked keys for SAP objects, and sends email notifications with PDF attachments related to the Supplier POs.

## 3. Sender/Receiver systems (Consolidated)
- **Sender System**: S/4HANA (Source of Supplier Purchase Orders)
- **Receiver System**: Email service (for sending notifications with PDF attachments)

## 4. Adapter types used (Consolidated)
- **HTTP Adapter**: Used for receiving incoming requests from the S/4HANA system.
- **Mail Adapter**: Used for sending email notifications with PDF attachments.

## 5. Step-by-step flow explanation (For each iFlow)
1. **Receive Data**: The iFlow starts by receiving Supplier Purchase Order data from the S/4HANA system via the HTTP adapter.
2. **Process Data**: 
   - The first Groovy script (`script1.groovy`) concatenates the Supplier Invoice and Fiscal Year to create a `LinkedSAPObjectKey`.
   - The second Groovy script (`script2.groovy`) constructs another `LinkedSAPObjectKey` using the Purchase Order and Purchase Order Item.
3. **Generate PDF Attachment**: The third Groovy script (`script3.groovy`) processes the PDF bytes, prepares the email content, and attaches the PDF to the email.
4. **Send Email**: The iFlow sends an email notification with the generated PDF attachment to the specified recipient.

## 6. Mapping logic summary (Summarize XSLT, Mappings)
No XSLT mappings are provided in the artifacts. The mapping logic is primarily handled through Groovy scripts that manipulate the message properties to create the necessary keys for further processing.

## 7. Groovy script explanations (Summarize all scripts and their usage/purpose)
- **`script1.groovy`**: This script retrieves the Supplier Invoice and Fiscal Year from the message properties, concatenates them to form a `LinkedSAPObjectKey`, and sets it as a property for further use in the iFlow.
  
- **`script2.groovy`**: This script constructs a `LinkedSAPObjectKey` using the Purchase Order and Purchase Order Item. It stores this key as a property and also sets it as a header for debugging purposes.

- **`script3.groovy`**: This script processes the PDF bytes from the message body, prepares an email payload with the PDF as an attachment, and sets the email content and recipient details. It encodes the PDF in Base64 format and sets the message body to JSON format for email transmission.

## 8. Error handling
Error handling mechanisms are not explicitly detailed in the provided artifacts. However, it is advisable to implement error handling strategies such as logging errors, sending alerts, and managing retries in the iFlow configuration to ensure robust processing.

## 9. Security/authentication
The artifacts do not specify any security or authentication mechanisms. It is recommended to use secure connections (HTTPS) for the HTTP adapter and to configure authentication for the email adapter to ensure secure transmission of sensitive data.

## 10. Deployment notes
- Ensure that all Groovy scripts are correctly deployed and accessible within the iFlow.
- Validate the configuration of the HTTP and Mail adapters to ensure they are correctly set up for the sender and receiver systems.
- Test the iFlow thoroughly in a development environment before moving to production to ensure all components work as expected.
