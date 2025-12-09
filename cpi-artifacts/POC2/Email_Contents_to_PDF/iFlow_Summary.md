# Email_Contents_to_PDF iFlow Documentation

## 1. High-level architecture
The Email_Contents_to_PDF iFlow integrates email content retrieval and PDF generation. It utilizes an IMAP adapter to fetch emails and an SMTP adapter to send the generated PDF as an email attachment. The architecture consists of a sender system (email server), the SAP Cloud Platform Integration (CPI), and a receiver system (email server).

## 2. Purpose of this iFlow
The purpose of this iFlow is to automate the process of converting email content into PDF format and sending it as an email attachment. This is particularly useful for archiving or sharing email content in a standardized format.

## 3. Sender/Receiver systems
- **Sender System**: Gmail (IMAP server) - retrieves unread emails from the inbox.
- **Receiver System**: Gmail (SMTP server) - sends the generated PDF as an email attachment.

## 4. Adapter types used
- **IMAP Adapter**: Used to connect to the Gmail server to fetch unread emails.
- **SMTP Adapter**: Used to send the generated PDF as an email attachment to the specified recipient.

## 5. Step-by-step flow explanation
1. **Start Event**: The iFlow is triggered by a scheduled event that polls the sender email inbox.
2. **Content Modifier**: The email body is extracted and prepared for processing.
3. **Groovy Script**: A Groovy script is executed to convert the email body into a PDF format.
4. **End Event**: The process concludes after the PDF is generated and sent as an email attachment.

## 6. Mapping logic summary
The iFlow does not utilize XSLT for mapping. The mapping is handled within the Groovy script, which converts the email body text into a PDF format.

## 7. Groovy script explanations
The Groovy script (`script1.groovy`) performs the following functions:
- Reads the email body from the incoming message.
- Defines a function to create a PDF from the email content using the iText library.
- Generates the PDF and creates two attachments with the same content but different names.
- Sets the email body to indicate that the original content is attached in PDF format.

## 8. Error handling
The iFlow is configured to not return exceptions to the sender, which means that errors during processing will not be communicated back. However, logging is enabled for all events, allowing for monitoring and troubleshooting.

## 9. Security/authentication
- **IMAP and SMTP Authentication**: The iFlow uses basic authentication with a username (`my_gmail`) and password (not explicitly mentioned for security reasons) to connect to the Gmail servers.
- **SSL/TLS**: The connections to both IMAP and SMTP servers are secured using SSL/TLS protocols to ensure data privacy and integrity.

## 10. High-Level Process Flow Diagram
```mermaid
graph TD
    A[Sender System (Gmail IMAP)] -->|Fetches unread emails| B[CPI]
    B -->|Processes email content| C[Groovy Script]
    C -->|Generates PDF| D[Receiver System (Gmail SMTP)]
    D -->|Sends email with PDF attachment| A
```
