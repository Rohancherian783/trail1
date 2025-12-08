# Consolidated Technical Report for CPI Package

## 1. High-level architecture
The architecture of the CPI package is designed to facilitate the mass upload of Supplier Purchase Orders (PO) from an S/4HANA system. The integration flow leverages various components such as Groovy scripts for data transformation, email notifications for status updates, and OData services for data handling. The architecture ensures seamless communication between the sender and receiver systems while maintaining data integrity and security.

## 2. Purpose of each iFlow
The primary iFlow in this package is responsible for processing Supplier Purchase Orders. It handles the extraction of relevant data from incoming messages, constructs necessary keys for SAP objects, and sends email notifications with attached PDF documents. The iFlow ensures that all necessary transformations and mappings are performed before the data is sent to the target system.

## 3. Sender/Receiver systems (Consolidated)
- **Sender System**: S/4HANA (Supplier Purchase Orders)
- **Receiver System**: OData service endpoint for processing Supplier Purchase Orders and email service for notifications.

## 4. Adapter types used (Consolidated)
- **HTTP Adapter**: Used for receiving incoming messages from the S/4HANA system.
- **OData Adapter**: Utilized for sending processed data to the target OData service.
- **Mail Adapter**: Employed for sending email notifications with PDF attachments.

## 5. Step-by-step flow explanation (For each iFlow)
1. **Receive Message**: The iFlow starts by receiving a message from the S/4HANA system via the HTTP adapter.
2. **Content Modifier**: The incoming message is processed to extract relevant properties such as Purchase Order and Purchase Order Item.
3. **Groovy Script 1**: The first Groovy script (`script1.groovy`) concatenates the Supplier Invoice and Fiscal Year to create a unique LinkedSAPObjectKey.
4. **Groovy Script 2**: The second Groovy script (`script2.groovy`) constructs another LinkedSAPObjectKey using the Purchase Order and Purchase Order Item, storing it as an exchange property.
5. **PDF Processing**: The iFlow processes the PDF bytes received in the message. If no PDF is found, it sets an appropriate status header.
6. **Groovy Script 3**: The third Groovy script (`script3.groovy`) prepares the email payload, including the PDF attachment and recipient details, and sets the message body to JSON format.
7. **Send Email**: The email adapter sends the constructed email with the attached PDF to the specified recipient.
8. **OData Call**: Finally, the processed data is sent to the OData service for further handling.

## 6. Mapping logic summary (Summarize XSLT, Mappings)
No XSLT mappings are provided in the current package. The mapping logic is primarily handled through Groovy scripts that manipulate message properties and construct keys necessary for the integration process.

## 7. Groovy script explanations (Summarize all scripts and their usage/purpose)
- **script1.groovy**: This script retrieves the Supplier Invoice and Fiscal Year from the message properties and concatenates them to create a LinkedSAPObjectKey, which is stored for later use.
- **script2.groovy**: This script constructs a LinkedSAPObjectKey using the Purchase Order and Purchase Order Item, storing it as an exchange property and adding it to the message headers for debugging purposes.
- **script3.groovy**: This script processes the PDF bytes from the message, constructs an email payload with the PDF as an attachment, and sets the message body to JSON format for sending via the mail adapter.

## 8. Error handling
Error handling is implemented through the use of conditional checks within the Groovy scripts. For instance, if no PDF bytes are found, the script sets a specific header ("X-EMAIL-STATUS") and provides a message body indicating the absence of PDF data. Additional error handling mechanisms can be implemented at the iFlow level to manage exceptions and retries.

## 9. Security/authentication
The integration flow utilizes secure communication protocols to ensure data integrity and confidentiality. Authentication mechanisms for the OData service and email service should be configured to prevent unauthorized access. This may include OAuth tokens or basic authentication, depending on the service requirements.

## 10. Deployment notes
Before deploying the iFlow, ensure that all necessary configurations for sender and receiver systems are in place. Validate the email recipient addresses and ensure that the mail adapter is correctly configured to send emails. Test the integration flow in a development environment before moving to production to ensure all components function as expected.
