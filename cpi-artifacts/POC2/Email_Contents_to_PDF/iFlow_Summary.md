# Email_Contents_to_PDF iFlow Documentation

## 1. High-level architecture
The Email_Contents_to_PDF iFlow integrates email retrieval and PDF generation functionalities. It retrieves emails from a Gmail inbox, processes the email content, converts it into PDF format, and sends the generated PDFs as attachments to a specified recipient.

## 2. Purpose of this iFlow
The primary purpose of this iFlow is to automate the process of converting email content into PDF format and sending it as an attachment. This is particularly useful for archiving or sharing email content in a standardized format.

## 3. Sender/Receiver systems
- **Sender System**: Gmail (IMAP) - The iFlow retrieves unread emails from a specified Gmail inbox.
- **Receiver System**: Gmail (SMTP) - The iFlow sends the generated PDF attachments to a specified recipient's email address.

## 4. Adapter types used
- **IMAP Adapter**: Used for retrieving emails from the Gmail inbox.
- **SMTP Adapter**: Used for sending emails with the generated PDF attachments.

## 5. Step-by-step flow explanation
1. **Start Event**: The iFlow is triggered based on a scheduled event to check for new emails.
2. **Content Modifier**: The email body is extracted and prepared for processing.
3. **Groovy Script**: The email content is passed to a Groovy script that converts the text into PDF format and creates attachments.
4. **End Event**: The processed message, now containing the PDF attachments, is sent to the recipient.

## 6. Mapping logic summary
The iFlow does not utilize XSLT for transformation. The mapping is primarily handled within the Groovy script, which converts the email body into a PDF format and prepares it for sending as an attachment.

## 7. Groovy script explanations
The Groovy script (`script1.groovy`) performs the following functions:
- **Read Email Body**: It retrieves the body of the incoming email.
- **PDF Generation**: It defines a function to create a PDF from the email content using the iText library.
- **Create Attachments**: It generates two PDF attachments with the same content and sets them in the message.
- **Set Message Body**: It updates the message body to inform the recipient about the attached PDFs.

## 8. Error handling
The iFlow does not explicitly define error handling mechanisms in the provided artifacts. However, it is advisable to implement error handling strategies such as logging errors and sending notifications in case of failures during email retrieval or PDF generation.

## 9. Security/authentication
- **IMAP and SMTP Authentication**: The iFlow uses basic authentication with a username and password for accessing the Gmail account. The username is set to `my_gmail`, and the password should be securely managed (not hardcoded).
- **SSL/TLS**: The iFlow uses SSL for secure communication with Gmail servers, ensuring that email content is transmitted securely.

## 10. High-Level Process Flow Diagram
```mermaid
graph TD
    A[Sender System: Gmail (IMAP)] -->|Retrieve Emails| B[CPI: Email_Contents_to_PDF iFlow]
    B -->|Generate PDF| C[Receiver System: Gmail (SMTP)]
    C -->|Send Email with PDF Attachments| D[Recipient]
    
```
