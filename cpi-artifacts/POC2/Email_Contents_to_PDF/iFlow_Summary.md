# Email_Contents_to_PDF iFlow Documentation

## 1. High-level architecture
The Email_Contents_to_PDF iFlow integrates email processing with PDF generation. It retrieves emails from a Gmail account, converts the email body into a PDF format, and sends the generated PDF as an attachment to a specified recipient.

## 2. Purpose of this iFlow
The primary purpose of this iFlow is to automate the conversion of email content into PDF format and send it as an attachment. This is useful for archiving or sharing email content in a standardized format.

## 3. Sender/Receiver systems
- **Sender System**: Gmail (IMAP) - The iFlow retrieves unread emails from a specified Gmail inbox.
- **Receiver System**: Gmail (SMTP) - The iFlow sends the generated PDF as an email attachment to a specified recipient.

## 4. Adapter types used
- **IMAP Adapter**: Used to fetch emails from the Gmail inbox.
- **SMTP Adapter**: Used to send emails with the generated PDF attachments.

## 5. Step-by-step flow explanation
1. **Start Event**: The iFlow is triggered based on a schedule to check for new emails.
2. **Content Modifier**: The email body is extracted and prepared for processing.
3. **Groovy Script**: The email body is passed to a Groovy script that converts the text into a PDF format.
4. **End Event**: The generated PDF is attached to a new email, which is then sent to the specified recipient.

## 6. Mapping logic summary
The iFlow does not utilize XSLT for mapping. The mapping is handled within the Groovy script, which processes the email body and generates a PDF.

## 7. Groovy script explanations
The Groovy script (`script1.groovy`) performs the following functions:
- **Read Email Body**: It retrieves the body of the incoming email.
- **PDF Generation**: It defines a function to create a PDF from the email body using the iText library.
- **Create Attachments**: It creates two PDF attachments with the same content.
- **Set Message Body**: It updates the message body to inform the recipient about the attached PDFs.
- **Return Message**: The modified message with attachments is returned for further processing.

## 8. Error handling
The iFlow does not explicitly define error handling mechanisms. However, it is advisable to implement error handling strategies such as logging errors and sending notifications in case of failures during email retrieval or PDF generation.

## 9. Security/authentication
- **IMAP and SMTP Authentication**: The iFlow uses basic authentication with a username and password for accessing the Gmail account. The username is set to `my_gmail`, and the password should be securely managed (not hardcoded).
- **SSL/TLS**: The iFlow uses secure connections (IMAPS for incoming emails and SMTP with STARTTLS for outgoing emails) to ensure data security during transmission.

## 10. High-Level Process Flow Diagram
```mermaid
graph TD
    A[Sender System: Gmail (IMAP)] -->|Fetch Unread Emails| B[CPI: Email_Contents_to_PDF iFlow]
    B -->|Convert Email Body to PDF| C[Receiver System: Gmail (SMTP)]
    C -->|Send Email with PDF Attachment| D[Recipient]
```
