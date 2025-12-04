# Consolidated Technical Report for CPI Package

## 1. High-level architecture
The architecture of the CPI package is designed to facilitate the mass upload of Supplier Purchase Orders (PO) from an S/4HANA system. It integrates various components including Groovy scripts for data processing, and utilizes email notifications for successful uploads. The architecture ensures seamless communication between the sender and receiver systems while maintaining data integrity and security.

## 2. Purpose of each iFlow
The primary iFlow in this package is responsible for handling the mass upload of Supplier POs. It orchestrates the flow of data from the S/4HANA system, processes the incoming data, generates necessary keys for identification, and sends email notifications with attached PDF documents.

## 3. Sender/Receiver systems (Consolidated)
- **Sender System**: SAP S/4HANA
- **Receiver System**: Email service (for sending notifications)

## 4. Adapter types used (Consolidated)
- **HTTP Adapter**: Used for receiving data from the S/4HANA system.
- **Mail Adapter**: Used for sending email notifications with attachments.

## 5. Step-by-step flow explanation (For each iFlow)
1. **Data Reception**: The iFlow receives data from the S/4HANA system via the HTTP adapter.
2. **Data Processing**: 
   - The first Groovy script (`script1.groovy`) processes the incoming message to create a linked key using the Supplier Invoice and Fiscal Year.
   - The second Groovy script (`script2.groovy`) constructs another linked key using the Purchase Order and Purchase Order Item.
3. **PDF Generation**: The iFlow generates a PDF document based on the processed data.
4. **Email Notification**: The third Groovy script (`script3.groovy`) prepares an email with the generated PDF attached and sends it to the specified recipient.

## 6. Mapping logic summary (Summarize XSLT, Mappings)
No XSLT mappings are provided in the current package. The mapping logic is primarily handled through Groovy scripts that concatenate various identifiers to create unique keys necessary for processing and tracking the Supplier POs.

## 7. Groovy script explanations (Summarize all scripts and their usage/purpose)
- **`script1.groovy`**: This script retrieves the Supplier Invoice and Fiscal Year from the message properties and concatenates them to form a linked key, which is stored as a property for further processing.
  
- **`script2.groovy`**: This script constructs a linked key using the Purchase Order and Purchase Order Item. It stores this key as a property and also sets it as a header for debugging purposes.

- **`script3.groovy`**: This script processes the PDF bytes from the message body, checks for their existence, and prepares an email payload with the PDF attached. It sets the email subject based on the billing ID and configures the message for sending via the Mail adapter.

## 8. Error handling
Error handling is implemented through conditional checks within the Groovy scripts. For instance, in `script3.groovy`, if no PDF bytes are found, an appropriate header is set, and a message indicating the absence of PDF data is returned. Additional error handling mechanisms can be implemented at the iFlow level to manage exceptions and retries.

## 9. Security/authentication
The package utilizes standard security practices for data transmission. The HTTP adapter should be configured to use HTTPS for secure communication. Authentication mechanisms for the email service should also be implemented to ensure that only authorized users can send emails.

## 10. Deployment notes
Before deploying the iFlow, ensure that all necessary configurations for the sender and receiver systems are in place. Validate the email service settings and test the Groovy scripts in a development environment to confirm their functionality. Monitor the iFlow after deployment for any issues and adjust configurations as necessary.
