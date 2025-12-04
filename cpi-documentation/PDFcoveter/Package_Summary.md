# Consolidated Technical Report for SAP CPI Package: PDFcoveter

## 1. High-level architecture
The architecture of the PDFcoveter package involves a series of integration flows (iFlows) that process incoming emails, extract their content, and generate PDF documents based on that content. The generated PDFs are then attached to outgoing emails. The package utilizes Groovy scripts for processing and PDF generation, leveraging the iText library for PDF creation.

## 2. Purpose of each iFlow
The iFlows in this package are designed to:
- Extract email content from incoming messages.
- Generate one or more PDF documents based on the extracted content.
- Attach the generated PDFs to outgoing emails.
- Handle various email formats and ensure proper MIME formatting for attachments.

## 3. Sender/Receiver systems (Consolidated)
- **Sender Systems**: Email systems that send messages to the CPI integration flow.
- **Receiver Systems**: Email systems that receive the processed emails with attached PDFs.

## 4. Adapter types used (Consolidated)
- **Mail Adapter**: Used for both sending and receiving emails.
- **HTTP Adapter**: (if applicable) for any HTTP-based interactions.

## 5. Step-by-step flow explanation (For each iFlow)
### iFlow 1: Email to PDF Generation
1. **Receive Email**: The iFlow receives an email via the Mail Adapter.
2. **Extract Content**: The email body is extracted using Groovy scripts (e.g., `script1.groovy`, `script2.groovy`).
3. **Generate PDFs**: The content is used to create one or more PDFs using Groovy scripts (e.g., `script19.groovy`, `script20.groovy`).
4. **Attach PDFs**: The generated PDFs are attached to the outgoing email.
5. **Send Email**: The email with attachments is sent to the specified recipient.

### iFlow 2: PDF Merging
1. **Receive Email**: Similar to the first iFlow, it receives an email.
2. **Extract Content**: The email body is extracted.
3. **Generate PDFs**: Two PDFs are generated based on the email content.
4. **Merge PDFs**: The two PDFs are merged into a single PDF using Groovy scripts (e.g., `script23.groovy`, `script26.groovy`).
5. **Send Merged PDF**: The merged PDF is sent as an attachment in the outgoing email.

## 6. Mapping logic summary (Summarize XSLT, Mappings)
- **No XSLT mappings** are utilized in this package. The mapping logic is primarily handled through Groovy scripts that directly manipulate the email content and generate PDFs.

## 7. Groovy script explanations (Summarize all scripts and their usage/purpose)
- **script1.groovy**: Extracts email content from incoming messages using JavaMail API.
- **script2.groovy**: Similar to script1, extracts email content and sets it as the message body.
- **script3.groovy**: Converts the CPI body into an InputStream and parses it to extract the email body.
- **script4.groovy**: Generates a PDF from the email body and sets it as the message body.
- **script5.groovy**: Handles PDF generation with error handling.
- **script6.groovy**: Similar to script5 but focuses on setting the PDF as the body.
- **script7.groovy**: Sets the Content-Disposition header for the first PDF attachment.
- **script8.groovy**: Sets the Content-Disposition header for the second PDF attachment.
- **script9.groovy**: Sets the Content-Disposition header for the second PDF attachment.
- **script10.groovy**: Sets the Content-Disposition header for the first PDF attachment.
- **script11.groovy**: Sets the Content-Disposition header for the second PDF attachment.
- **script12.groovy**: Generates a PDF and sets it as an attachment.
- **script13.groovy**: Similar to script12, generates a PDF and sets it as an attachment.
- **script14.groovy**: Generates a PDF and attaches it to the message.
- **script15.groovy**: Generates a PDF and attaches it to the message.
- **script16.groovy**: Generates a PDF and attaches it to the message.
- **script17.groovy**: Generates a PDF from the email body.
- **script18.groovy**: Extracts email content and generates a PDF.
- **script19.groovy**: Generates a PDF from the email body.
- **script20.groovy**: Generates two PDFs and creates a multipart MIME message.
- **script21.groovy**: Generates two PDFs and attaches them to the message.
- **script22.groovy**: Generates a PDF and sets it as the message body.
- **script23.groovy**: Merges two PDFs into one.
- **script24.groovy**: Generates two PDFs and creates a multipart MIME message.
- **script25.groovy**: Generates two PDFs and attaches them to the message.
- **script26.groovy**: Merges two PDFs into one and sets it as the message body.
- **script27.groovy**: Generates two PDFs and attaches them to the message.
- **script28.groovy**: Logs the attachments for debugging purposes.
- **script29.groovy**: Generates two PDFs and attaches them to the message.
- **script30.groovy**: Cleans up the email body and generates two PDFs.

## 8. Error handling
Error handling is implemented in several scripts (e.g., `script5.groovy`) to catch exceptions during PDF generation. If an error occurs, a plain text message indicating the error is set as the message body.

## 9. Security/authentication
The package relies on the security features of the SAP CPI platform, including secure connections for email transmission. Authentication details for email accounts are managed within the CPI configuration.

## 10. Deployment notes
- Ensure that all necessary libraries (e.g., iText) are included in the CPI environment.
- Validate email configurations for sender and receiver systems.
- Test the iFlows thoroughly in a development environment before deploying to production.
