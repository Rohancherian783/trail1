# Documentation Report for Email_Contents_to_PDF iFlow

## 1. High-level architecture
The Email_Contents_to_PDF iFlow is designed to automate the process of reading emails, converting their content into PDF format, and sending them as attachments in a new email. The architecture consists of a sender system that retrieves emails via IMAP, processes the email content, generates PDFs, and then sends the PDFs as attachments via SMTP to a specified recipient.

## 2. Purpose of this iFlow
The primary purpose of this iFlow is to facilitate the conversion of email content into PDF format and send it as an attachment in a new email. This is particularly useful for archiving or sharing email content in a standardized format.

## 3. Sender/Receiver systems
- **Sender System**: The sender system is an email server accessed via IMAP (specifically `imap.gmail.com`) to read incoming emails from the inbox.
- **Receiver System**: The receiver system is also an email server accessed via SMTP (specifically `smtp.gmail.com`) to send the generated PDF attachments to the specified recipient.

## 4. Adapter types used
- **IMAP Adapter**: Used for receiving emails from the sender's inbox.
- **SMTP Adapter**: Used for sending emails with the generated PDF attachments.

## 5. Step-by-step flow explanation
1. **Start Event**: The iFlow begins with a start event that triggers the process.
2. **Content Modifier**: The email content is read and modified to extract the body text.
3. **Groovy Script**: A Groovy script is executed to convert the email body into PDF format.
4. **End Event**: The process concludes with an end event after the email has been sent.

## 6. Mapping logic summary
The mapping logic involves reading the email body from the incoming message, converting it into a PDF format using a Groovy script, and then attaching the generated PDFs to a new email message. The original email body is replaced with a message indicating that the content has been converted to PDF.

## 7. Groovy script explanations
The Groovy script (`script1.groovy`) performs the following functions:
- Reads the email body from the incoming message.
- Defines a function to create a PDF from the text content.
- Generates the PDF and creates two attachments with the same content but different names.
- Sets the attachments to the message and modifies the body text to indicate that the original content is attached in PDF format.

## 8. Error handling
Error handling is not explicitly defined in the provided iFlow configuration. However, it is advisable to implement error handling mechanisms to manage potential issues such as email retrieval failures or PDF generation errors.

## 9. Security/authentication
The iFlow uses basic authentication for both the IMAP and SMTP connections. The user credentials are specified in the configuration, ensuring that the iFlow can securely access the email accounts for reading and sending emails.

## 10. High-Level Architecture Diagram
![architecture.png](architecture.png) 

This documentation provides a comprehensive overview of the Email_Contents_to_PDF iFlow, detailing its architecture, purpose, systems involved, and the processing steps taken to achieve the desired outcome.
