# Email_Contents_to_PDF iFlow Documentation

## 1. High-level architecture
The architecture of the Email_Contents_to_PDF iFlow consists of a sender system that retrieves emails, processes their content, and sends the processed content as PDF attachments to a receiver system. The integration is facilitated through SAP Cloud Platform Integration (CPI), which orchestrates the flow of data between the sender and receiver.

## 2. Purpose of this iFlow
The purpose of the Email_Contents_to_PDF iFlow is to automate the process of converting email content into PDF format and sending it as an attachment. This is particularly useful for archiving or sharing email content in a standardized format.

## 3. Sender/Receiver systems
- **Sender System**: Gmail (IMAP) - The iFlow retrieves unread emails from a specified Gmail inbox.
- **Receiver System**: Gmail (SMTP) - The iFlow sends the generated PDF attachments to a specified recipient email address.

## 4. Adapter types used
- **IMAP Adapter**: Used for retrieving emails from the sender system (Gmail).
- **SMTP Adapter**: Used for sending emails with PDF attachments to the receiver system (Gmail).

## 5. Step-by-step flow explanation
1. **Start Event**: The iFlow is triggered based on a scheduled event to check for new emails.
2. **Content Modifier**: The email body is extracted and prepared for processing.
3. **Groovy Script**: The email body is passed to a Groovy script that converts the content into a PDF format.
4. **End Event**: The processed message, now containing the PDF attachments, is sent to the receiver system.

## 6. Mapping logic summary
The iFlow does not utilize XSLT for transformation; instead, it relies on the Groovy script for processing the email content. The Groovy script reads the email body and generates a PDF document from it.

## 7. Groovy script explanations
The Groovy script (`script1.groovy`) performs the following functions:
- **Read Email Body**: It retrieves the body of the incoming email message.
- **PDF Creation**: It defines a function to create a PDF document from the email body using the iText library.
- **Attachments Handling**: It creates two PDF attachments with the same content and sets them in the message.
- **Set Message Body**: It updates the message body to inform the recipient about the attached PDFs.

## 8. Error handling
The iFlow is configured to log all events, which helps in monitoring and troubleshooting. However, specific error handling mechanisms (like retries or alerts) are not explicitly defined in the provided artifacts.

## 9. Security/authentication
- **IMAP and SMTP Authentication**: The iFlow uses basic authentication with a username and password for accessing the Gmail account. The username is set to `my_gmail`, and the password is managed securely within the CPI environment.
- **SSL/TLS**: The iFlow uses SSL/TLS for secure communication with Gmail servers, ensuring that the data in transit is encrypted.

## 10. High-Level Process Flow Diagram
```mermaid
graph TD
    A[Sender System: Gmail (IMAP)] -->|Retrieve Emails| B[CPI: Email_Contents_to_PDF iFlow]
    B -->|Process Email Content| C[Groovy Script: Convert to PDF]
    C -->|Generate PDF Attachments| D[Receiver System: Gmail (SMTP)]
    D -->|Send Email with Attachments| E[End Process]
```
