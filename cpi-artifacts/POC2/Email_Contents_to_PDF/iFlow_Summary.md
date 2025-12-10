# Documentation for Email Contents to PDF iFlow

## 1. High-level architecture
The Email Contents to PDF iFlow is designed to automate the process of converting email content into PDF format and sending it as an attachment. The architecture consists of a sender system that retrieves emails from an IMAP server, processes the email content, converts it to PDF, and then sends the PDF as an email attachment via an SMTP server.

## 2. Purpose of this iFlow
The purpose of this iFlow is to facilitate the conversion of email content into PDF format. This is particularly useful for archiving or sharing email content in a standardized format. The iFlow retrieves unread emails, processes their content, generates PDF files, and sends them to specified recipients.

## 3. Sender/Receiver systems
- **Sender System**: 
  - IMAP Server (e.g., Gmail) for retrieving emails.
- **Receiver System**: 
  - SMTP Server (e.g., Gmail) for sending emails with PDF attachments.

## 4. Adapter types used
- **IMAP Adapter**: Used for connecting to the email server to fetch unread emails.
- **SMTP Adapter**: Used for sending emails with the generated PDF attachments.

## 5. Step-by-step flow explanation
1. **Start Event**: The iFlow is triggered based on a schedule (e.g., every minute).
2. **Content Modifier**: The email body is extracted and prepared for processing.
3. **Groovy Script**: The email body is passed to a Groovy script that converts the content into PDF format.
4. **End Event**: The process concludes after the PDF is generated and sent.
5. **Message Flow**: The generated PDF is sent as an email attachment to the specified recipient.

## 6. Mapping logic summary
The mapping logic primarily involves extracting the email body and converting it into a PDF format. The Groovy script handles the conversion and creates two PDF attachments with the same content. The body of the outgoing email is set to inform the recipient about the attached PDFs.

## 7. Groovy script explanations
The Groovy script performs the following tasks:
- Reads the email body from the incoming message.
- Defines a function to create a PDF from the text content.
- Generates the PDF and creates two attachments with the same content.
- Sets the email body to indicate that the PDFs are attached.
- Returns the modified message with attachments.

## 8. Error handling
Error handling is implemented through the iFlow's built-in error handling mechanisms. If any step fails (e.g., email retrieval, PDF generation, or email sending), the iFlow can be configured to log the error and send notifications to the relevant stakeholders.

## 9. Security/authentication
- **IMAP Authentication**: The iFlow uses basic authentication to connect to the IMAP server. Credentials are securely stored and managed.
- **SMTP Authentication**: Similar to IMAP, SMTP also uses basic authentication for sending emails. The credentials are configured in the iFlow settings.

## 10. High-Level Architecture Diagram
![High-Level Architecture Diagram](architecture.png)

This documentation provides a comprehensive overview of the Email Contents to PDF iFlow, detailing its architecture, purpose, and operational flow. The iFlow effectively automates the conversion of email content into PDF format, enhancing productivity and standardization in email management.
